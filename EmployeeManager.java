package net.texala.net;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

class EmployeeManager {
    private static final String FILE_NAME = "Allemployee.csv";
    private static final String HEADER = "EMPID,First Name,Last Name,Department\n";
    private Vector<Employee> employee;

    public EmployeeManager() {
        employee = new Vector<>();
        loadDataFromCSV();
    }

    private void loadDataFromCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int empId = Integer.parseInt(parts[0]);
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String department = parts[3];
                    employee.add(new Employee(empId, firstName, lastName, department));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data from CSV: " + e.getMessage());
        }
    }

    public void addEmployee(int empId, String firstName, String lastName, String department) {
        employee.add(new Employee(empId, firstName, lastName, department));
    }
    public void updateEmployee(int empId, String fieldToUpdate, String newValue) {
        for (Employee emp : employee) {
            if (emp.getEmpId() == empId) {
                switch (fieldToUpdate.toLowerCase()) {
                    case "first name":
                        emp.setFirstName(newValue);
                        break;
                    case "last name":
                        emp.setLastName(newValue);
                        break;
                    case "department":
                        emp.setDepartment(newValue);
                        break;
                    default:
                        System.out.println("Invalid field to update.");
                        return;
                }
                System.out.println("Employee details updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

   

    public void updateEmployeeDepartment(int empId, String newDepartment) {
        updateEmployee(empId, "department", newDepartment);
    }

    public void updateEmployee(int empIdToUpdate, String fieldToUpdate, int newEmpId, String newFirstName,
            String newLastName, String newDepartment) {
        for (Employee employee : employee) {
            if (employee.getEmpId() == empIdToUpdate) {
                employee.setEmpId(newEmpId);
                employee.setFirstName(newFirstName);
                employee.setLastName(newLastName);
                employee.setDepartment(newDepartment);
                System.out.println("Employee details updated successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public void deleteEmployees(int empIdToDelete) {
        if (empIdToDelete != 0) {
            boolean deleted = deleteEmployeeById(empIdToDelete);
            if (deleted) {
                System.out.println("Employee with EMPID " + empIdToDelete + " deleted successfully.");
            } else {
                System.out.println("Employee with EMPID " + empIdToDelete + " not found.");
            }
        } else {
            employee.clear();
            System.out.println("All employees deleted successfully.");
        }
    }

    boolean deleteEmployeeById(int empIdToDelete) {
        Iterator<Employee> iterator = employee.iterator();
        while (iterator.hasNext()) {
            Employee emp = iterator.next();
            if (emp.getEmpId() == empIdToDelete) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public void commitChanges() {
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write(HEADER);
            for (Employee employee : employee) {
                fileWriter.write(
                        employee.getEmpId() + "," + employee.getFirstName() + "," + employee.getLastName() + ","
                                + employee.getDepartment() + "\n");
            }
            System.out.println("Changes committed to CSV.");
        } catch (IOException e) {
            System.out.println("Error committing changes: " + e.getMessage());
        }
    }

    public Vector<Employee> getAllEmployees() {
        return employee;
    }

    public Employee getEmployeeById(int empIdToUpdate) {
        for (Employee emp : employee) {
            if (emp.getEmpId() == empIdToUpdate) {
                return emp;
            }
        }
        return null;
    }
}
