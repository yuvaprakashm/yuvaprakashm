package net.texala.main;

import java.util.Comparator;

public class EmployeeComparator implements Comparator<Employee> {
    private String sortBy;

    public EmployeeComparator(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public int compare(Employee emp1, Employee emp2) {
        switch (sortBy.toLowerCase()) {
            case "firstname":
                return emp1.getFirstName().compareToIgnoreCase(emp2.getFirstName());
            case "lastname":
                return emp1.getLastName().compareToIgnoreCase(emp2.getLastName());
            case "empid":
                return Integer.compare(emp1.getEmpID(), emp2.getEmpID());
            case "department":
                return emp1.getDepartment().compareToIgnoreCase(emp2.getDepartment());
            default:
                throw new IllegalArgumentException("Invalid sorting criteria: " + sortBy);
        }
    }

}
