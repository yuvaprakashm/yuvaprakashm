package net.texala.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

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

	// Check validity of a record
	private boolean isValidRecord(String line) {
		String[] parts = line.split(",");
		// Check if the line has correct number of fields
		if (parts.length != 4) {
			return false;
		}
		// Check validity of each field (you can add more checks as needed)
		try {
			int empID = Integer.parseInt(parts[0]);
			String firstName = parts[1];
			String lastName = parts[2];
			String department = parts[3];
			if (empID <= 0 || !firstName.matches("[a-zA-Z]+") || !lastName.matches("[a-zA-Z]+")) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	// Read CSV file, extract valid records, and store in employees vector
	// Read CSV file, extract valid records, and store in employees vector
	public void readCSV(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			br.readLine(); // Skip header

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
						errorWriter.write("Error! Incorrect first name in record: " + line + "\n");
						continue;
					}

					if (!isValidName(lastName)) {
						errorWriter.write("Error! Incorrect last name in record: " + line + "\n");
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

	public boolean isValidName(String name) {
		boolean isValid = name.matches("[a-zA-Z]+");
		if (!isValid) {
			System.out.println("Invalid name: " + name);
		}
		return isValid;
	}

	private boolean isValidEmpID(int empID) {
		return empID > 0;
	}

	// Sort and display records
	public void sortAndDisplay(String sortBy) {
	    Comparator<Employee> comparator = new EmployeeComparator(sortBy.toLowerCase());

	    switch (sortBy.toLowerCase()) {
	        case "firstname":
	        case "lastname":
	        case "empid":
	        case "department":
	            employees.sort(comparator);
	            System.out.println("Sorted Records:");
	            for (Employee employee : employees) {
	                System.out.println(employee.getEmpID() + ", " + employee.getFirstName() + " " + employee.getLastName()
	                        + ", " + employee.getDepartment());
	            }
	            break;
	        default:
	            System.out.println("Invalid sorting criteria.");
	            break;
	    }
	}

	// Save sorted file
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

	// Close error writer
	public void closeErrorWriter() {
		try {
			errorWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
