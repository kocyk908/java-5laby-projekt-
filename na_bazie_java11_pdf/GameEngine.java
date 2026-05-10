public class GameEngine {

    private Company company;
    private ConsoleUI ui;
    private int turn = 1;
    private boolean running = true;

    public void run() {
        while (running) {
            ui.showTurn(turn);
            ui.showCompanyStatus(company);
            ui.showMenu();

            int choice = ui.readChoice();

            switch (choice) {
                case 1 -> workOneTurn();
                case 0 -> running = false;
            }

            turn++;
        }
    }

    private void workOneTurn() {
        for (Project p : company.getProjects()) {
            p.workOneTurn();
        }
    }
}