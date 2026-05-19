package com.university.techcorp.engine;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Project;
import com.university.techcorp.domain.ProjectStatus;
import com.university.techcorp.ui.ConsoleUI;

import com.university.techcorp.events.RandomEventManager;

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

            // if (running) {
            //     advanceTurn();
            // }
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> ui.showCompanyStatus(company);
            case 2 -> startPlannedProjects();
            case 3 -> workOnProjects();
            case 4 -> running = false;
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
        for (Project project : company.getProjects()) {
            if (!project.isFinished()) {
                return false;
            }
        }
        return true;
    }
}