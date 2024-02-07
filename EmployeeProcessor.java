package net.texala.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class EmployeeProcessor {
	private Vector<Employee> employees;
	private BufferedWriter errorWriter;

	// Constructor
	public EmployeeProcessor() {
		employees = new Vector<>();
		try {
			errorWriter = new BufferedWriter(new FileWriter("error.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to read CSV file line by line
	public void readCSV(String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			// Skip the first line (header)
			br.readLine();
			while ((line = br.readLine()) != null) {
				if (isValidRecord(line)) {
					String[] parts = line.split(",");
					int empID = Integer.parseInt(parts[0]);
					String firstName = parts[1];
					String lastName = parts[2];
					String department = parts[3];
					employees.add(new Employee(empID, firstName, lastName, department));
				} else {
					errorWriter.write("Invalid record: " + line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to check if record is valid
	public boolean isValidRecord(String line) {
		// Implement your validation logic here
		// For simplicity, let's assume any record with 4 fields is valid
		return line.split(",").length == 4;
	}

	// Method to sort and display employees
	public void sortAndDisplay(String sortBy) {
		employees.sort(new EmployeeComparator(sortBy));
		for (Employee employee : employees) {
			System.out.println(employee.getEmpID() + ", " + employee.getFirstName() + ", " + employee.getLastName()
					+ ", " + employee.getDepartment());
		}
	}

	// Method to save sorted file
	public void saveSortedFile(String fileName) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			for (Employee employee : employees) {
				writer.write(employee.getEmpID() + ", " + employee.getFirstName() + ", " + employee.getLastName() + ", "
						+ employee.getDepartment() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to close error writer
	public void closeErrorWriter() {
		try {
			errorWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EmployeeProcessor processor = new EmployeeProcessor();
		processor.readCSV("employees.csv");
		processor.sortAndDisplay("firstName");
		processor.saveSortedFile("sorted_employees.csv");
		processor.closeErrorWriter();
	}
}