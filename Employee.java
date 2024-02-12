package net.texala.main;

public class Employee {
	private int empID;
	private String firstName;
	private String lastName;
	private String department;

	public Employee(int empID, String firstName, String lastName, String department) {
		this.empID = empID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;

	}

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
