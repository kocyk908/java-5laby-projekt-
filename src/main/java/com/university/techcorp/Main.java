package com.university.techcorp;

import com.university.techcorp.domain.*;
import com.university.techcorp.engine.GameEngine;
import com.university.techcorp.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {
        // 1. Inicjalizacja firmy
        Company company = new Company("TechCorp", 50_000);

        // 2. Tworzenie pracowników (przypisujemy ich do zmiennych!)
        Employee anna = new Developer("Anna", 9, 8_000);
        Employee piotr = new Tester("Piotr", 6, 6_500);
        Employee ewa = new Manager("Ewa", 7, 9_000);
        Employee tomek = new Intern("Tomek", 4, 3_000);
        
        // 3. Zatrudnienie pracowników
        company.hire(anna);
        company.hire(piotr);
        company.hire(ewa);
        company.hire(tomek); // Możesz odblokować swojego stażystę!

        // 4. Utworzenie projektów
        Project mobileApp = new Project("Mobile App", 30);
        Project website = new Project("Website", 20);

        // 5. Dodanie/wystartowanie projektów w firmie
        company.addProject(mobileApp);
        company.addProject(website);
        
        // 6. Przypisanie wszystkich zatrudnionych pracowników do pierwszego projektu
        company.assignEmployee(mobileApp, anna);
        company.assignEmployee(mobileApp, piotr);
        company.assignEmployee(mobileApp, ewa);

        company.assignEmployee(website, anna);
        company.assignEmployee(website, tomek);


        // 7. Inicjalizacja interfejsu użytkownika i silnika gry
        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, ui);

        // 8. Uruchomienie głównej pętli gry (Game Loop)
        engine.start();
    }
}