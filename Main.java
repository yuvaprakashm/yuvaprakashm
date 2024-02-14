package net.texala.net;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        EmployeeManager manager = new EmployeeManager();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Display Employee");
            System.out.println("5. Commit Changes");
            System.out.println("6. Exit");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter EMPID:");
                    int empId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter First Name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter Last Name:");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter Department:");
                    System.out.println("1. Dev");
                    System.out.println("2. Ps");
                    System.out.println("3. Qa");
                    System.out.println("4. Admin");
                    System.out.print("Select Department: ");
                    int departmentChoice1 = scanner.nextInt();
                    scanner.nextLine();
                    String department;
                    switch (departmentChoice1) {
                        case 1:
                            department = "Dev";
                            break;
                        case 2:
                            department = "Ps";
                            break;
                        case 3:
                            department = "Qa";
                            break;
                        case 4:
                            department = "Admin";
                            break;
                        default:
                            System.out.println("Invalid department choice. Defaulting to 'Unknown'.");
                            department = "Unknown";
                            break;
                    }
                    manager.addEmployee(empId, firstName, lastName, department);
                    break;
                case 2:
                    System.out.println("Enter EMPID of the employee to update:");
                    int empIdToUpdate = scanner.nextInt();
                    scanner.nextLine();
                    Employee employeeToUpdate = manager.getEmployeeById(empIdToUpdate);
                    if (employeeToUpdate == null) {
                        System.out.println("Employee not found.");
                        break;
                    }
                    System.out.println("Choose field to update:");
                    System.out.println("1. First Name");
                    System.out.println("2. Last Name");
                    System.out.println("3. Department");
                    System.out.println("4. Update All");
                    System.out.print("Select Field: ");
                    int fieldChoice = scanner.nextInt();
                    scanner.nextLine();
                    String fieldToUpdate;
                    switch (fieldChoice) {
                        case 1:
                            fieldToUpdate = "First Name";
                            break;
                        case 2:
                            fieldToUpdate = "Last Name";
                            break;
                        case 3:
                            fieldToUpdate = "Department";
                            break;
                        case 4:
                            fieldToUpdate = "Update All";
                            break;
                        default:
                            System.out.println("Invalid field choice. Defaulting to 'Unknown'.");
                            fieldToUpdate = "Unknown";
                            break;
                    }
                    if (!fieldToUpdate.equals("Update All")) {
                        if (!fieldToUpdate.equals("Department")) {
                            System.out.println("Enter new value:");
                            String newValue = scanner.nextLine();
                            manager.updateEmployee(empIdToUpdate, fieldToUpdate, newValue);
                        } else {
                            System.out.println("Enter new department:");
                            System.out.println("1. Dev");
                            System.out.println("2. Ps");
                            System.out.println("3. Qa");
                            System.out.println("4. Admin");
                            System.out.print("Select Department: ");
                            int departmentChoice11 = scanner.nextInt();
                            scanner.nextLine();
                            String department1;
                            switch (departmentChoice11) {
                                case 1:
                                    department1 = "Dev";
                                    break;
                                case 2:
                                    department1 = "Ps";
                                    break;
                                case 3:
                                    department1 = "Qa";
                                    break;
                                case 4:
                                    department1 = "Admin";
                                    break;
                                default:
                                    System.out.println("Invalid department choice. Defaulting to 'Unknown'.");
                                    department1 = "Unknown";
                                    break;
                            }
                            manager.updateEmployee(empIdToUpdate, fieldToUpdate, department1);
                        }
                    } else {
                        System.out.println("Enter new values for all fields:");
                        System.out.println("Enter new EMPID:");
                        int newEmpId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.println("Enter new First Name:");
                        String newFirstName = scanner.nextLine();
                        System.out.println("Enter new Last Name:");
                        String newLastName = scanner.nextLine();
                        System.out.println("Enter new Department:");
                        System.out.println("1. Dev");
                        System.out.println("2. Ps");
                        System.out.println("3. Qa");
                        System.out.println("4. Admin");
                        System.out.print("Select Department: ");
                        int departmentChoice12 = scanner.nextInt();
                        scanner.nextLine();
                        String newDepartment;
                        switch (departmentChoice12) {
                            case 1:
                                newDepartment = "Dev";
                                break;
                            case 2:
                                newDepartment = "Ps";
                                break;
                            case 3:
                                newDepartment = "Qa";
                                break;
                            case 4:
                                newDepartment = "Admin";
                                break;
                            default:
                                System.out.println("Invalid department choice. Defaulting to 'Unknown'.");
                                newDepartment = "Unknown";
                                break;
                        }
                        manager.updateEmployee(empIdToUpdate, fieldToUpdate, newEmpId, newFirstName, newLastName, newDepartment);
                    }
                    break;
                case 3:
                    System.out.println("Enter EMPID of the employee to delete (or 0 to delete all employees):");
                    int empIdToDelete = scanner.nextInt();
                    scanner.nextLine();
                    manager.deleteEmployees(empIdToDelete);
                    break;
                case 4:
                    System.out.println("Display Employee");
                    Vector<Employee> employees = manager.getAllEmployees();
                    if (employees.isEmpty()) {
                        System.out.println("No employees found.");
                    } else {
                        System.out.println("EmpId, FirstName, LastName, Department");
                        for (Employee employee : employees) {
                            System.out.println(employee);
                        }
                    }
                    break;
                case 5:
                    manager.commitChanges();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Terminating the program...");
                    break;
                default:
                    System.out.println("Invalid option selected.");
                    break;
            }
        }
        scanner.close();
    }
}
