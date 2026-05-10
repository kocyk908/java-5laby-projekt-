package com.university.techcorp;

import com.university.techcorp.domain.*;
import com.university.techcorp.engine.GameEngine;
import com.university.techcorp.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        // 1. Inicjalizacja firmy
        Company company = new Company("TechCorp", 50_000);

        // 2. Zatrudnienie pracowników
        company.hire(new Developer("Anna", 9, 8_000));
        company.hire(new Tester("Piotr", 6, 6_500));
        company.hire(new Manager("Ewa", 7, 9_000));
        // company.hire(new Intern("Tomek", 4, 3_000)); // Możesz odblokować swojego stażystę!

        // 3. Utworzenie projektów
        Project mobileApp = new Project("Mobile App", 30);
        Project website = new Project("Website", 20);

        // 4. Przypisanie wszystkich zatrudnionych pracowników do pierwszego projektu
        for (var employee : company.getEmployees()) {
            mobileApp.addEmployee(employee);
        }

        // 5. Dodanie/wystartowanie projektów w firmie
        company.startProject(mobileApp);
        company.startProject(website);

        // 6. Inicjalizacja interfejsu użytkownika i silnika gry
        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, ui);

        // 7. Uruchomienie głównej pętli gry (Game Loop)
        engine.start();
    }
}