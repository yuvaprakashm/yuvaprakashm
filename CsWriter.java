package net.texala.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CsWriter {
    private static final String FILE_NAME = "record.csv";
    private static final String HEADER = "EMPID,First Name,Last Name,Department\n";
    private static final Set<String> registeredEmpId = new HashSet<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(FILE_NAME);
                writer.write(HEADER);
                writer.close();
            }

            FileWriter writer = new FileWriter(FILE_NAME, true);

            while (true) {
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
                        continue;
                }

                System.out.println("Choose an operation:");
                System.out.println("1. Register Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Display Employee");
                System.out.println("5. Return to Select Department");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerEmployee(writer, department);
                        break;
                    case 2:
                        updateEmployee(writer);
                        break;
                    case 3:
                        deleteEmployee(writer);
                        break;
                    case 4:
                        displayEmployee();
                        break;
                    case 5:
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

    public static void registerEmployee(FileWriter writer, String department) throws IOException {
        System.out.println("Enter EMPID :");
        String empId = scanner.nextLine();

        if (isEmpIdExists(empId)) {
            System.out.println("EMPID " + empId + " is already registered. Please enter a unique EMPID.");
            return;
        }

        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        String record = empId + ',' + firstName + ',' + lastName + ',' + department + "\n";
        writer.write(record);
        writer.flush();
        System.out.println("Employee registered successfully.");

        registeredEmpId.add(empId);
    }

    public static void updateEmployee(FileWriter writer) throws IOException {
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
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            fileWriter.write(updatedData.toString());
            fileWriter.close();
            System.out.println("Employee details updated successfully.");
        }
    }

    public static void deleteEmployee(FileWriter writer) throws IOException {
        System.out.println("Enter EMPID of the employee to delete:");
        String empIdToDelete = scanner.nextLine();

        File originalFile = new File(FILE_NAME);
        Scanner fileScanner = new Scanner(originalFile);

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

        fileScanner.close();

        if (!found) {
            System.out.println("Employee not found.");
        } else {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            fileWriter.write(updatedData.toString());
            fileWriter.close();
        }
    }

    public static void displayEmployee() throws IOException {
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

    private static void displayEmployeeById(Scanner scanner) throws IOException {
        System.out.println("Enter EMPID to display employee details:");
        String empId = scanner.nextLine();

        Scanner fileScanner = new Scanner(new File(FILE_NAME));
        boolean found = false;
        System.out.println("EMPID,First Name,Last Name,Department");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length > 0 && parts[0].equals(empId)) {
                System.out.println(line);
                found = true;
                break;
            }
        }

        fileScanner.close();

        if (!found) {
            System.out.println("Employee with EMPID " + empId + " not found.");
        }
    }

    private static void displayAllEmployees() throws IOException {
        Scanner fileScanner = new Scanner(new File(FILE_NAME));
        while (fileScanner.hasNextLine()) {
            System.out.println(fileScanner.nextLine());
        }

        fileScanner.close();
    }

    private static void displayEmployeeByDepartment(Scanner scanner) throws IOException {
        System.out.println("Choose Department:");
        System.out.println("1. PS");
        System.out.println("2. DEV");
        System.out.println("3. QA");
        System.out.println("4. ADMIN");
        System.out.print("Select option : ");

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

        Scanner fileScanner = new Scanner(new File(FILE_NAME));
        boolean found = false;

        System.out.println("Employees in department " + department + ":");
        System.out.println("EMPID,First Name,Last Name,Department");
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length > 3 && parts[3].equals(department)) {
                System.out.println(line);
                found = true;
            }
        }

        fileScanner.close();

        if (!found) {
            System.out.println("No employees found in department: " + department);
        }
    }

    private static boolean isEmpIdExists(String empId) throws IOException {
        Scanner fileScanner = new Scanner(new File(FILE_NAME));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length > 0 && parts[0].equals(empId)) {
                fileScanner.close();
                return true;
            }
        }
        fileScanner.close();
        return false;
    }
}
