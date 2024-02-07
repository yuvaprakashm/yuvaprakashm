package net.texala.main;

import java.util.Comparator;

class EmployeeComparator implements Comparator<Employee> {
    private String sortBy;

    // Constructor
    public EmployeeComparator(String sortBy) {
        this.sortBy = sortBy;
    }

    // Compare method
    @Override
    public int compare(Employee emp1, Employee emp2) {
        switch (sortBy) {
            case "firstName":
                return emp1.getFirstName().compareTo(emp2.getFirstName());
            case "lastName":
                return emp1.getLastName().compareTo(emp2.getLastName());
            case "empID":
                return Integer.compare(emp1.getEmpID(), emp2.getEmpID());
            default:
                return 0; // No sorting
        }
    }
}