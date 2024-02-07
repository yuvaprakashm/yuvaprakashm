package net.texala.main;

class Employee {
    private int empID;
    private String firstName;
    private String lastName;
    private String department;

    // Constructor
    public Employee(int empID, String firstName, String lastName, String department) {
        this.empID = empID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    // Getters
    public int getEmpID() {
        return empID;
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
}