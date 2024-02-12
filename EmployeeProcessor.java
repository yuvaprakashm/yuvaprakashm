package net.texala.employee_service_sortanddisplay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

import net.texala.employee_processor.Employee;

public class Processor {
	private Vector<Employee> employees;
	private BufferedWriter errorWriter;

	public Processor() {
		employees = new Vector<>();
		try {
			errorWriter = new BufferedWriter(new FileWriter("error.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readCSV(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			br.readLine();

			while ((line = br.readLine()) != null) {
				if (isValidRecord(line)) {
					String[] parts = line.split(",");
					int empID;
					String firstName;
					String lastName;
					String department;
					try {
						empID = Integer.parseInt(parts[0]);
						firstName = parts[1];
						lastName = parts[2];
						department = parts[3];
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						errorWriter.write("Error! Invalid record format: " + line + "\n");
						continue;
					}

					if (!isValidEmpID(empID)) {
						errorWriter.write("Error! Invalid Employee ID: " + empID + "\n");
						continue;
					}

					if (!isValidName(firstName)) {
						errorWriter.write("Error! Invalid first name: " + firstName + "\n");
						continue;
					}

					if (!isValidName(lastName)) {
						errorWriter.write("Error! Invalid last name: " + lastName + "\n");
						continue;
					}

					employees.add(new Employee(empID, firstName, lastName, department));
				} else {
					errorWriter.write("Error! Invalid record: " + line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isValidEmpID(int empID) {

		return empID > 0;
	}

	private boolean isValidName(String name) {

		return name.matches("[a-zA-Z]+");
	}

	private boolean isValidRecord(String line) {
		String[] parts = line.split(",");
		if (parts.length != 4) {
			return false;
		}

		return true;
	}

	public void sortAndDisplay(String sortBy) {
		Comparator<Employee> comparator = new EmployeeComparator(sortBy);

		switch (sortBy) {
		case "FirstName":
		case "LastName":
		case "EmpID":
			employees.sort(comparator);
			break;
		default:
			System.out.println("Invalid sorting criteria.");
			return;
		}

		System.out.println("Sorted Records:");
		for (Employee employee : employees) {
			System.out.println(employee);
		}
	}

	public void saveSortedFile(String fileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write("EMPID,First Name,Last Name,Department\n");
			for (Employee employee : employees) {
				writer.write(employee.getEmpID() + "," + employee.getFirstName() + "," + employee.getLastName() + ","
						+ employee.getDepartment() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeErrorWriter() {
		try {
			errorWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
