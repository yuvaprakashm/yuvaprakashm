package net.texala.main;

import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CsWriter {
    private static final String FILE_NAME = "record.csv";
    private static final String HEADER = "EMPID,First Name,Last Name,Department\n";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Vector<Employee> employees = new Vector<>();
    private static final int COMMIT_OPTION = 6;

    public static void main(String[] args) {
        try {
            // Load existing data into employees vector
            loadEmployees();

            while (true) {
                // Display department choices
                System.out.println("Choose a department:");
                System.out.println("1. PS");
                System.out.println("2. DEV");
                System.out.println("3. QA");
                System.out.println("4. ADMIN");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int departmentChoice = scanner.nextInt();
                scanner.nextLine();

                if (departmentChoice == 5) {
                    System.out.println("Exiting the program...");
                    break;
                }

                String department = getDepartment(departmentChoice);

                System.out.println("Choose an operation:");
                System.out.println("1. Register Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Display Employee");
                System.out.println("5. Return to Select Department");
                System.out.println("6. Commit Changes to CSV");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerEmployee(department);
                        break;
                    case 2:
                        updateEmployee();
                        break;
                    case 3:
                        deleteEmployee();
                        break;
                    case 4:
                        displayEmployee();
                        break;
                    case 5:
                        break;
                        
                    case COMMIT_OPTION:
                        commitChanges();
                        break;

                    default:
                        System.out.println("Invalid choice. Please select a number between 1 and 5.");
                        break;
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
    private static void commitChanges() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            fileWriter.write(HEADER);

            for (Employee employee : employees) {
                String record = employee.getEmpID() + "," + employee.getFirstName() + ","
                                + employee.getLastName() + "," + employee.getDepartment() + "\n";
                fileWriter.write(record);
            }

            fileWriter.close();
            System.out.println("Changes committed to CSV.");
        } catch (IOException e) {
            System.out.println("Error committing changes: " + e.getMessage());
        }
    }


    private static void loadEmployees() throws IOException {
        File file = new File(FILE_NAME);

        // Check if the file exists
        if (!file.exists()) {
            // If the file doesn't exist, create a new file
            file.createNewFile();
            return; // No need to load employees from an empty file
        }

        Scanner fileScanner = new Scanner(file);

        // Skip the header line
        if (fileScanner.hasNextLine()) {
            fileScanner.nextLine();
        }

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length == 4) { // Ensure all fields are present
                int empId = Integer.parseInt(parts[0].trim());
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                String department = parts[3].trim();

                Employee employee = new Employee(empId, firstName, lastName, department);
                employees.add(employee);
            } else {
                System.out.println("Invalid data format in CSV: " + line);
            }
        }

        fileScanner.close();
    }


    private static String getDepartment(int choice) {
        // Return department name based on user choice
        switch (choice) {
            case 1:
                return "PS";
            case 2:
                return "DEV";
            case 3:
                return "QA";
            case 4:
                return "ADMIN";
            default:
                return "";
        }
    }

    private static void registerEmployee(String department) {
        System.out.println("Enter EMPID:");
        int empId = scanner.nextInt();

        if (isEmpIdExists(empId)) {
            System.out.println("EMPID " + empId + " is already registered. Please enter a unique EMPID.");
            return;
        }
        scanner.nextLine();
        System.out.println("Enter 	First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        // Create employee object
        Employee employee = new Employee(empId, firstName, lastName, department);
        // Add employee to the vector
        employees.add(employee);

        System.out.println("Employee registered successfully.");
    }

    private static void updateEmployee() throws FileNotFoundException {
        System.out.println("Enter EMPID of the employee to update:");
        String empIdToUpdate = scanner.nextLine();

        File originalFile = new File(FILE_NAME);
        Scanner fileScanner = new Scanner(originalFile);

        StringBuilder updatedData = new StringBuilder();
        boolean found = false;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");

            if (parts[0].equals(empIdToUpdate)) {
                System.out.println("Choose what to update:");
                System.out.println("1. Update First Name");
                System.out.println("2. Update Last Name");
                System.out.println("3. Update Department");
                System.out.println("4. Update All Details");
                System.out.print("Enter your choice: ");
                int updateChoice = scanner.nextInt();
                scanner.nextLine();

                switch (updateChoice) {
                    case 1:
                        System.out.println("Enter new First Name:");
                        String newFirstName = scanner.nextLine();
                        parts[1] = newFirstName;
                        break;
                    case 2:
                        System.out.println("Enter new Last Name:");
                        String newLastName = scanner.nextLine();
                        parts[2] = newLastName;
                        break;
                    case 3:
                        System.out.println("Choose new Department:");
                        System.out.println("1. PS");
                        System.out.println("2. DEV");
                        System.out.println("3. QA");
                        System.out.println("4. ADMIN");
                        System.out.print("Choose department: ");
                        int newDepartmentChoice = scanner.nextInt();
                        scanner.nextLine();
                        String newDepartment;
                        switch (newDepartmentChoice) {
                            case 1:
                                newDepartment = "PS";
                                break;
                            case 2:
                                newDepartment = "DEV";
                                break;
                            case 3:
                                newDepartment = "QA";
                                break;
                            case 4:
                                newDepartment = "ADMIN";
                                break;
                            default:
                                System.out.println("Invalid department choice.");
                                return;
                        }
                        parts[3] = newDepartment;
                        break;
                    case 4:
                        System.out.println("Enter new First Name:");
                        newFirstName = scanner.nextLine();
                        System.out.println("Enter new Last Name:");
                        newLastName = scanner.nextLine();
                        System.out.println("Choose new Department:");
                        System.out.println("1. PS");
                        System.out.println("2. DEV");
                        System.out.println("3. QA");
                        System.out.println("4. ADMIN");
                        System.out.print("Choose department: ");
                        newDepartmentChoice = scanner.nextInt();
                        scanner.nextLine();
                        switch (newDepartmentChoice) {
                            case 1:
                                newDepartment = "PS";
                                break;
                            case 2:
                                newDepartment = "DEV";
                                break;
                            case 3:
                                newDepartment = "QA";
                                break;
                            case 4:
                                newDepartment = "ADMIN";
                                break;
                            default:
                                System.out.println("Invalid department choice.");
                                return;
                        }
                        parts[1] = newFirstName;
                        parts[2] = newLastName;
                        parts[3] = newDepartment;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                found = true;
            }

            updatedData.append(String.join(",", parts)).append("\n");
        }

        fileScanner.close();

        if (!found) {
            System.out.println("Employee not found in the selected department.");
        } else {
            try {
                FileWriter fileWriter = new FileWriter(FILE_NAME);
                fileWriter.write(updatedData.toString());
                fileWriter.close();
                System.out.println("Employee details updated successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private static void deleteEmployee() {
        System.out.println("Enter EMPID of the employee to delete:");
        String empIdToDelete = scanner.nextLine();

        File originalFile = new File(FILE_NAME);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(originalFile);

            StringBuilder updatedData = new StringBuilder();
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equals(empIdToDelete)) {
                    found = true;
                    System.out.println("Employee deleted successfully.");
                } else {
                    updatedData.append(line).append("\n");
                }
            }

            if (!found) {
                System.out.println("Employee not found.");
            } else {
                try {
                    FileWriter fileWriter = new FileWriter(FILE_NAME);
                    fileWriter.write(updatedData.toString());
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

    private static void displayEmployee() {
        System.out.println("Choose how to display employee details:");
        System.out.println("1. Display by EMPID");
        System.out.println("2. Display all employees");
        System.out.println("3. Display by Department");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                displayEmployeeById(scanner);
                break;
            case 2:
                displayAllEmployees();
                break;
            case 3:
                displayEmployeeByDepartment(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please select a number between 1 and 3.");
                break;
        }
    }

    private static void displayEmployeeById(Scanner scanner) {
        System.out.println("Enter EMPID to display employee details:");
        int empId = scanner.nextInt();
        scanner.nextLine();

        for (Employee employee : employees) {
            if (employee.getEmpID() == empId) {
                System.out.println(employee);
                return;
            }
        }
        System.out.println("Employee with EMPID " + empId + " not found.");
    }

    private static void displayAllEmployees() {
        System.out.println("All Employees:");
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    private static void displayEmployeeByDepartment(Scanner scanner) {
        System.out.println("Choose Department:");
        System.out.println("1. PS");
        System.out.println("2. DEV");
        System.out.println("3. QA");
        System.out.println("4. ADMIN");
        System.out.print("Select option: ");
        int departmentChoice = scanner.nextInt();
        scanner.nextLine();

        String department;
        switch (departmentChoice) {
            case 1:
                department = "PS";
                break;
            case 2:
                department = "DEV";
                break;
            case 3:
                department = "QA";
                break;
            case 4:
                department = "ADMIN";
                break;
            default:
                System.out.println("Invalid department choice.");
                return;
        }

        System.out.println("Employees in department " + department + ":");
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(department)) {
                System.out.println(employee);
            }
        }
    }

    private static boolean isEmpIdExists(int empId) {
        for (Employee employee : employees) {
            if (employee.getEmpID() == empId) {
                return true;
            }
        }
        return false;
    }
}
