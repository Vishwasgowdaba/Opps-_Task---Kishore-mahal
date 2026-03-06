import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Payable {
    void processSalary();
}

abstract class Employee implements Payable {
    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }

    public void displayDetails() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Base Salary/Earnings: " + salary);
    }

    public abstract double calculateBonus();
}

class FullTimeEmployee extends Employee {
    private double bonusPercentage;

    public FullTimeEmployee(int id, String name, double salary, double bonusPercentage) {
        super(id, name, salary);
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * (bonusPercentage / 100.0);
    }

    @Override
    public void processSalary() {
        double total = getSalary() + calculateBonus();
        System.out.printf("%s (Full-Time) Final Salary: %.2f%n", getName(), total);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: Full-Time");
        System.out.println("Bonus %: " + bonusPercentage);
    }
}

class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;
    private double bonusPercentage;

    public PartTimeEmployee(int id, String name, int hoursWorked, double hourlyRate, double bonusPercentage) {
        super(id, name, hoursWorked * hourlyRate);
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
        this.bonusPercentage = bonusPercentage;
    }

    @Override
    public double calculateBonus() {
        return (hoursWorked * hourlyRate) * (bonusPercentage / 100.0);
    }

    @Override
    public void processSalary() {
        double total = getSalary() + calculateBonus();
        System.out.printf("%s (Part-Time) Final Salary: %.2f%n", getName(), total);
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Type: Part-Time");
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.println("Hourly Rate: " + hourlyRate);
        System.out.println("Bonus %: " + bonusPercentage);
    }
}

public class Main {

    public static Employee findEmployee(List<Employee> list, int id) {
        for (Employee e : list) {
            if (e.getId() == id)
                return e;
        }
        return null;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();

        while (true) {

            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Process Payroll");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter Employee Type (F = Full-Time, P = Part-Time): ");
                    char type = sc.next().charAt(0);

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    if (type == 'F' || type == 'f') {
                        System.out.print("Enter Base Salary: ");
                        double salary = sc.nextDouble();

                        System.out.print("Enter Bonus Percentage: ");
                        double bonus = sc.nextDouble();

                        employees.add(new FullTimeEmployee(id, name, salary, bonus));
                    } 
                    else if (type == 'P' || type == 'p') {
                        System.out.print("Enter Hours Worked: ");
                        int hours = sc.nextInt();

                        System.out.print("Enter Hourly Rate: ");
                        double rate = sc.nextDouble();

                        System.out.print("Enter Bonus Percentage: ");
                        double bonus = sc.nextDouble();

                        employees.add(new PartTimeEmployee(id, name, hours, rate, bonus));
                    }

                    System.out.println("Employee Added Successfully!");
                    break;

                case 2:
                    if (employees.isEmpty()) {
                        System.out.println("No employees found.");
                    } else {
                        for (Employee emp : employees) {
                            System.out.println("-----------------------");
                            emp.displayDetails();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID to search: ");
                    int searchId = sc.nextInt();

                    Employee found = findEmployee(employees, searchId);

                    if (found != null) {
                        System.out.println("Employee Found:");
                        found.displayDetails();
                        System.out.println("Bonus: " + found.calculateBonus());
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 4:
                    if (employees.isEmpty()) {
                        System.out.println("No employees to process payroll.");
                    } else {
                        System.out.println("\n=== Payroll ===");
                        for (Employee emp : employees) {
                            emp.processSalary();
                        }
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}