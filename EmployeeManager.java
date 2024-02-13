package net.texala.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class EmployeeManager {

	private static final String FILE_NAME = "record.csv";
	private static final String HEADER = "EMPID,First Name,Last Name,Department\n";
	private Vector<Employee> employeeRecords;

	public EmployeeManager() {
		employeeRecords = new Vector<>();
	}

	public void addEmployee(int empId, String firstName, String lastName, String department) {
		Employee employee = new Employee(empId, firstName, lastName, department);
		employeeRecords.add(employee);
		System.out.println("Employee registered successfully.");
	}

	public void updateEmployee(int empId, String fieldToUpdate, String newValue) {
		for (Employee employee : employeeRecords) {
			if (employee.getEmpId() == empId) {
				switch (fieldToUpdate.toLowerCase()) {
				case "first name":
					employee.setFirstName(newValue);
					break;
				case "last name":
					employee.setLastName(newValue);
					break;
				case "department":
					employee.setDepartment(newValue);
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

	public void deleteEmployee(int empId) {
		for (int i = 0; i < employeeRecords.size(); i++) {
			if (employeeRecords.get(i).getEmpId() == empId) {
				employeeRecords.remove(i);
				System.out.println("Employee deleted successfully.");
				return;
			}
		}
		System.out.println("Employee not found.");
	}

	public void printEmployees() {
		System.out.println("All Employees:");
		for (Employee employee : employeeRecords) {
			System.out.println(employee);
		}
	}

	public void printEmployees(int startId, int endId) {
		System.out.println("Employees in the range " + startId + " to " + endId + ":");
		for (Employee employee : employeeRecords) {
			if (employee.getEmpId() >= startId && employee.getEmpId() <= endId) {
				System.out.println(employee);
			}
		}
	}

	public void printEmployeeByParameter(String parameter, String value) {
		System.out.println("Employees with " + parameter + " as " + value + ":");
		for (Employee employee : employeeRecords) {
			switch (parameter.toLowerCase()) {
			case "first name":
				if (employee.getFirstName().equalsIgnoreCase(value)) {
					System.out.println(employee);
				}
				break;
			case "last name":
				if (employee.getLastName().equalsIgnoreCase(value)) {
					System.out.println(employee);
				}
				break;
			case "department":
				if (employee.getDepartment().equalsIgnoreCase(value)) {
					System.out.println(employee);
				}
				break;
			default:
				System.out.println("Invalid parameter.");
			}
		}
	}

	public void commitChanges() {
		try {
			FileWriter fileWriter = new FileWriter(FILE_NAME);
			fileWriter.write(HEADER);
			for (Employee employee : employeeRecords) {
				fileWriter.write(employee.getEmpId() + "," + employee.getFirstName() + "," + employee.getLastName()
						+ "," + employee.getDepartment() + "\n");
			}
			fileWriter.close();
			System.out.println("Changes committed to CSV.");
		} catch (IOException e) {
			System.out.println("Error committing changes: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		EmployeeManager manager = new EmployeeManager();
		Scanner scanner = new Scanner(System.in);
		boolean exit = false;

		while (!exit) {
			System.out.println("\n1. Add Employee");
			System.out.println("2. Update Employee");
			System.out.println("3. Delete Employee");
			System.out.println("4. Print All Employees");
			System.out.println("5. Print Employees in Range");
			System.out.println("6. Print Employees by Parameter");
			System.out.println("7. Commit Changes");
			System.out.println("8. Exit");
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
				String department = scanner.nextLine();
				manager.addEmployee(empId, firstName, lastName, department);
				break;
			case 2:
				System.out.println("Enter EMPID of the employee to update:");
				int empIdToUpdate = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Choose field to update (First Name, Last Name, Department):");
				String fieldToUpdate = scanner.nextLine();
				System.out.println("Enter new value:");
				String newValue = scanner.nextLine();
				manager.updateEmployee(empIdToUpdate, fieldToUpdate, newValue);
				break;
			case 3:
				System.out.println("Enter EMPID of the employee to delete:");
				int empIdToDelete = scanner.nextInt();
				scanner.nextLine();
				manager.deleteEmployee(empIdToDelete);
				break;
			case 4:
				manager.printEmployees();
				break;
			case 5:
				System.out.println("Enter start EMPID:");
				int startId = scanner.nextInt();
				System.out.println("Enter end EMPID:");
				int endId = scanner.nextInt();
				manager.printEmployees(startId, endId);
				break;
			case 6:
				System.out.println("Choose parameter to search (First Name, Last Name, Department):");
				String parameter = scanner.nextLine();
				System.out.println("Enter value to search:");
				String value = scanner.nextLine();
				manager.printEmployeeByParameter(parameter, value);
				break;
			case 7:
				manager.commitChanges();
				break;
			case 8:
				exit = true;
				break;
			default:
				System.out.println("Invalid choice. Please enter a number between 1 and 8.");
			}
		}
		scanner.close();
	}
}
