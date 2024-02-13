package net.texala.main;

public class Employee {
    private int empId;
    private String firstName;
    private String lastName;
    private String department;

    public Employee(int empId, String firstName, String lastName, String department) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public int getEmpId() {
        return empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee ID: " + empId + ", FirstName: " + firstName + ", LastName: " + lastName + ", Department: " + department;
    }

    public void setFirstName(String newValue) {
        this.firstName = newValue;
    }

    public void setLastName(String newValue) {
        this.lastName = newValue;
    }

    public void setDepartment(String newValue) {
        this.department = newValue;
    }
}
