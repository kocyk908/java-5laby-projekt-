public class Project {

    private List<Employee> team = new ArrayList<>();
    private int progress;
    private int requiredWork;

    public void workOneTurn() {
        for (Employee e : team) {
            progress += e.work();
        }
    }
}
