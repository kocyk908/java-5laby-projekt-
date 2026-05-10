public class Company {

    private String name;
    private double cash;
    private List<Employee> employees = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public void hire(Employee e) {
        employees.add(e);
    }

    public void addProject(Project p) {
        projects.add(p);
    }
}