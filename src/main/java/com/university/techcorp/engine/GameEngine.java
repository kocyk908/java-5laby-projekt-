package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.ProjectStatus;
import com.university.techcorp.ui.ConsoleUI;

import com.university.techcorp.events.RandomEventManager;

import java.util.List;
import java.util.ArrayList;

public class GameEngine {

    private final Company company;
    private final ConsoleUI ui;
    private final RandomEventManager eventManager;
    private boolean running;
    private int turn;

    public GameEngine(Company company, ConsoleUI ui) {
        this.company = company;
        this.eventManager = new RandomEventManager();
        this.ui = ui;
        this.running = true;
        this.turn = 1;
    }

    public void start() {
        while (running) {
            ui.showTurnHeader(turn);
            ui.showCompanyStatus(company);
            ui.showMainMenu();
            
            int choice = ui.readMenuChoice();
            handleChoice(choice);
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> ui.showCompanyStatus(company);
            case 2 -> startPlannedProjects();
            case 3 -> workOnProjects();
            case 4 -> freezeProject();     
            case 5 -> running = false;
            default -> ui.showMessage("Nieprawidłowa opcja menu.");
        }
    }

    private void startPlannedProjects() {
        for (Project project : company.getProjects()) {
            if (project.getStatus() == ProjectStatus.PLANNED) {
                project.start();
            }
        }
        ui.showMessage("Wszystkie zaplanowane projekty zostały rozpoczęte.");
    }

    private void workOnProjects() {
        boolean workPerformed = false;
        for (Project project : company.getProjects()) {
            if (project.getStatus() == ProjectStatus.IN_PROGRESS) {

                project.workOneTurn();
                ui.showMessage("Wykonano pracę nad projektem: " + project.getName());
                workPerformed = true;
            } else if (project.getStatus() == ProjectStatus.PLANNED) {
                ui.showMessage("Projekt " + project.getName() + " nie jest w trakcie realizacji.");       
            }
        }
        // Jeśli jakikolwiek projekt był w trakcie realizacji, zespół pobiera pensję
        if (workPerformed) {
            try {
                company.paySalaries();
                ui.showMessage("Pobrano pensje za wykonaną pracę.");
            } catch (IllegalStateException e) {
                ui.showMessage("Nie można zapłacić pensji! Firma zbankrutowała. KONIEC GRY.");
                running = false;
            }
        } else {
            ui.showMessage("Brak aktywnych projektów. Zespół nie podjął pracy, budżet bez zmian.");
        }

        if (running && workPerformed) {
            advanceTurn();
        }
    }

    private void freezeProject() {
        List<Project> freezable = new ArrayList<>();
        
        // ZMIANA: Szukamy tylko rozpoczętych projektów
        for (Project p : company.getProjects()) {
            if (p.getStatus() == ProjectStatus.IN_PROGRESS || p.getStatus() == ProjectStatus.FROZEN) {
                freezable.add(p);
            }
        }

        if (freezable.isEmpty()) {
            ui.showMessage("Brak rozpoczętych projektów, które można by zamrozić bądź odmrozić.");
            return;
        }

        ui.showMessage("\nWybierz projekt do zamrożenia/odmrożenia:");
        for (int i = 0; i < freezable.size(); i++) {
            Project p = freezable.get(i);
            String actionText = (p.getStatus() == ProjectStatus.IN_PROGRESS) ? "[ZAMRÓŹ]" : "[ODMRÓŹ]";
            ui.showMessage((i + 1) + ". " + p.getName() + " | Status: " + p.getStatus() + " " + actionText);
        }
        ui.showMessage("0. Powrót");

        int choice = ui.readMenuChoice();

        if (choice > 0 && choice <= freezable.size()) {
            Project selectedProject = freezable.get(choice - 1);
            
            if (selectedProject.getStatus() == ProjectStatus.IN_PROGRESS) {
                selectedProject.freeze();
                ui.showMessage("Pomyślnie zamrożono projekt: " + selectedProject.getName());
            } else if (selectedProject.getStatus() == ProjectStatus.FROZEN) {
                selectedProject.resume();
                ui.showMessage("Pomyślnie odmrożono projekt: " + selectedProject.getName() + 
                               " (Status: PLANNED. Użyj opcji 2, aby go znowu uruchomić!).");
            }
        } else if (choice != 0) {
            ui.showMessage("Nieprawidłowy wybór.");
        }
    }

    private void advanceTurn() {
        eventManager.triggerRandomEvent(company);
        // 2. Sprawdzanie warunku wygranej
        if (allProjectsFinished()) {
            ui.showMessage("\n>>> GRATULACJE! WYGRANA! <<<");
            ui.showMessage("Wszystkie projekty zostały zakończone.");
            running = false;
        } else {
            turn++; // Przechodzimy do następnej tury
        }
    }

    private boolean allProjectsFinished() {
        if (company.getProjects().isEmpty()) {
            return false;
        }
        
        boolean anyFinished = false;
        
        for (Project project : company.getProjects()) {
            // Dopóki coś jest do zrobienia (IN_PROGRESS lub PLANNED), gra trwa dalej
            if (project.getStatus() == ProjectStatus.PLANNED || project.getStatus() == ProjectStatus.IN_PROGRESS) {
                return false; 
            }
            // Zapisujemy, że udało się pozytywnie zakończyć chociaż jeden projekt
            if (project.getStatus() == ProjectStatus.FINISHED) {
                anyFinished = true;
            }
        }
        // Wygrywasz tylko, jeśli nie ma już aktywnych projektów, i chociaż jeden udało się ukończyć (a nie wszystkie anulować)
        return anyFinished;
    }
}