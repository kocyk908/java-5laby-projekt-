package com.university.techcorp;

public class Main {

    public static void main(String[] args) {

        Company company = new Company("TechCorp", 50_000);

        Employee anna = new Developer("Anna", 9, 8_000);
        Employee piotr = new Tester("Piotr", 6, 6_500);
        Employee ewa = new Manager("Ewa", 7, 9_000);
        Employee tomek = new Intern("Tomek", 4, 3_000);

        company.hire(anna);
        company.hire(piotr);
        company.hire(ewa);
        company.hire(tomek);

        Project mobileApp = new Project("Mobile App", 40);
        mobileApp.addEmployee(anna);
        mobileApp.addEmployee(piotr);
        mobileApp.addEmployee(ewa);

        Project website = new Project("Website", 45);
        website.addEmployee(anna);
        website.addEmployee(tomek);

        company.startProject(mobileApp);
        company.startProject(website);

        System.out.println("INITIAL STATE:");
        company.showStatus();

        int turn = 1;
        while (true) {
            System.out.println("\n--- TURN " + turn + " ---");
            if (!mobileApp.isFinished()){
                mobileApp.workOneTurn();
            }
            if (!website.isFinished()) {
                 website.workOneTurn();
            }
            
            company.showStatus();
            if (mobileApp.isFinished() && website.isFinished()) {
                break;
            }
            turn++;
        }


        System.out.println("\nProject '" + mobileApp.getName() + "' finished.");
        System.out.println("Project '" + website.getName() + "' finished.");
    }
}