package com.example.techcorp.engine;

import com.example.techcorp.domain.Company;
import com.example.techcorp.domain.Project;
import com.example.techcorp.domain.ProjectStatus;
import com.example.techcorp.ui.ConsoleUI;

public class GameEngine {

    private final Company company;
    private final ConsoleUI ui;
    private boolean running;
    private int turn;

    public GameEngine(Company company, ConsoleUI ui) {
        this.company = company;
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

            if (running) {
                advanceTurn();
            }
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
        for (Project project : company.getProjects()) {
            project.workOneTurn();
        }
        ui.showMessage("Wykonano pracę nad projektami w tej turze.");
    }

    private void advanceTurn() {
        if (allProjectsFinished()) {
            ui.showMessage("Wszystkie projekty zostały zakończone. Gra skończona!");
            running = false;
        } else {
            turn++;
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