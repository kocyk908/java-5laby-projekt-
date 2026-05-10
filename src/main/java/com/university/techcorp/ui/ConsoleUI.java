package com.university.techcorp.ui;

import com.university.techcorp.domain.Company;
import com.university.techcorp.domain.Project;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner scanner = new Scanner(System.in);

    public void showTurnHeader(int turn) {
        System.out.println("\n========================");
        System.out.println("       TURA " + turn);
        System.out.println("========================");
    }

    public void showMainMenu() {
        System.out.println("\nWybierz akcję:");
        System.out.println("1. Status firmy");
        System.out.println("2. Rozpocznij zaplanowane projekty");
        System.out.println("3. Pracuj nad projektami");
        System.out.println("4. Wyjdź z gry");
    }

    public int readMenuChoice() {
        System.out.print("\nTwój wybór: ");
        if (!scanner.hasNextInt()) {
            scanner.nextLine(); // Wyczyszczenie błędnego wpisu
            return -1;
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Konsumowanie znaku nowej linii
        return choice;
    }

    public void showCompanyStatus(Company company) {
        System.out.println("\n--- STATUS FIRMY ---");
        System.out.println("Nazwa: " + company.getName());
        System.out.println("Budżet: " + company.getCash() + " PLN");
        System.out.println("Projekty:");
        
        if (company.getProjects().isEmpty()) {
            System.out.println("  Brak projektów.");
        } else {
            for (Project project : company.getProjects()) {
                System.out.printf("- %-15s | Status: %-10s | Postęp: %d/%d%n",
                        project.getName(),
                        project.getStatus(),
                        project.getProgress(),
                        project.getRequiredWork());
            }
        }
        System.out.println("--------------------");
    }

    public void showMessage(String message) {
        System.out.println(">> " + message);
    }
}