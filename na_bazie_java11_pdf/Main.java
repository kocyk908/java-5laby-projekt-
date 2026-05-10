public class Main {

    public static void main(String[] args) {
        Company company = new Company("TechCorp", 50000);

        company.hire(new Developer("Anna", 8, 7000));
        company.hire(new Tester("Piotr", 6, 6000));

        Project project = new Project("Mobile App", 40);
        company.addProject(project);

        ConsoleUI ui = new ConsoleUI();
        GameEngine engine = new GameEngine(company, ui);

        engine.run(); // uruchamia grę
    }
}