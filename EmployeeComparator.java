package net.texala.csv_file.employee_comparator;

import java.util.Comparator;

import net.texala.csv_file.employee.Employee;

public class EmployeeComparator implements Comparator<Employee> {
	private String sortBy;

	public EmployeeComparator(String sortBy) {
		this.sortBy = sortBy;
	}

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
			return 0;
		}
	}
}