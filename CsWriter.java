package net.texala.employee_service.cs_writer;

import java.util.Scanner;
import java.util.Vector;
import net.texala.employee_details.Employee;
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

	public static void main(String[] args) {
		try {
			loadEmployees();

			while (true) {
				System.out.println("Choose a department:");
				System.out.println("1. PS");
				System.out.println("2. DEV");
				System.out.println("3. QA");
				System.out.println("4. ADMIN");

				System.out.print("Enter your choice: ");
				int departmentChoice = 0;
				try {
				    departmentChoice = scanner.nextInt();
				    scanner.nextLine();
				} catch (java.util.InputMismatchException e) {
				    scanner.nextLine();
				    System.out.println("Invalid input. Please enter a number between 1 and 4.");
				    continue;
				}

				String department = "";  

				if (departmentChoice < 1 || departmentChoice > 4) {
				    System.out.println("Invalid choice. Please select a number between 1 and 4.");
				    continue;
				}

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
				        System.out.println("Invalid choice. Please select a number between 1 and 4.");
				        continue;
				}

				while (true) {
					System.out.println("Choose an operation:");
					System.out.println("1. Register Employee");
					System.out.println("2. Update Employee");
					System.out.println("3. Delete Employee");
					System.out.println("4. Display Employee");
					System.out.println("5. Commit Changes to CSV");
					System.out.println("6. Return to Select Department");
					System.out.println("7. Exit");
					System.out.print("Enter your choice: ");

					int choice = 0;
					try {
						choice = scanner.nextInt();
						scanner.nextLine();
					} catch (java.util.InputMismatchException e) {
						scanner.nextLine();
						System.out.println("Invalid input. Please enter a number between 1 and 7.");
						continue;
					}

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
						commitChanges();
						break;
					case 6:
						break;
					case 7:
						System.out.println("Exiting program...");
						System.exit(0);
						break;
					default:
						System.out.println("Invalid choice. Please select a number between 1 and 7.");
						break;
					}
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
				String record = employee.getEmpID() + "," + employee.getFirstName() + "," + employee.getLastName() + ","
						+ employee.getDepartment() + "\n";
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

		if (!file.exists()) {
			file.createNewFile();
			return;
		}

		Scanner fileScanner = new Scanner(file);

		if (fileScanner.hasNextLine()) {
			fileScanner.nextLine();
		}

		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] parts = line.split(",");

			if (parts.length == 4) {
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

	private static void registerEmployee(String department) {
		Scanner scanner = new Scanner(System.in);

		int empId = 0;
		while (true) {
			try {
				System.out.println("Enter EMPID:");
				empId = Integer.parseInt(scanner.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid integer for EMPID.");
			}
		}

		System.out.println("Enter First Name:");
		String firstName = scanner.nextLine();

		System.out.println("Enter Last Name:");
		String lastName = scanner.nextLine();

		Employee employee = new Employee(empId, firstName, lastName, department);
		employees.add(employee);

		System.out.println("Employee registered successfully.");
		scanner.close();
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
}
