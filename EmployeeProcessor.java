package net.texala.employee_processor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import net.texala.csv_file.employee.Employee;
import net.texala.csv_file.employee_comparator.EmployeeComparator;

public class EmployeeProcessor {
	private Vector<Employee> employees;
	private BufferedWriter errorWriter;

	public EmployeeProcessor() {
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
					try {
						int empID = Integer.parseInt(parts[0]);
						String firstName = parts[1];
						String lastName = parts[2];
						String department = parts[3];

						if (isDuplicateEmpID(empID)) {
							errorWriter.write("Error! Record with Employee ID " + empID + " is repeated  \n");
						}

						if (!isValidFirstName(firstName)) {
							errorWriter.write("Error! Incorrect first name = " + firstName + "\n");
						}

						if (!isValidLastName(lastName)) {
							errorWriter.write("Error! Incorrect last name = " + lastName + "\n");
						}
						employees.add(new Employee(empID, firstName, lastName, department));
					} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
						errorWriter.write("Error! Invalid record format: " + line + "\n");
					}
				} else {
					errorWriter.write("Error ! Invalid record: " + line + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean isValidLastName(String firstName) {

		return firstName.matches("[a-zA-Z]+");
	}

	private boolean isDuplicateEmpID(int empID) {
		for (Employee employee : employees) {
			if (employee.getEmpID() == empID) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidFirstName(String firstName) {

		return firstName.matches("[a-zA-Z]+");
	}

	public boolean isValidRecord(String line) {

		return line.split(",").length == 4;
	}

	public void sortAndDisplay(String sortBy) {
		employees.sort(new EmployeeComparator(sortBy));
		System.out.println("The Sorted Records by ====> " + sortBy);

		for (Employee employee : employees) {

			System.out.println(employee.getEmpID() + ", " + employee.getFirstName() + ", " + employee.getLastName()
					+ ", " + employee.getDepartment());
		}
	}

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

	public void closeErrorWriter() {
		try {
			errorWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EmployeeProcessor processor = new EmployeeProcessor();
		processor.readCSV("employee.csv");
		processor.sortAndDisplay("firstName");
		processor.saveSortedFile("sorted_employees.csv");
		processor.closeErrorWriter();
	}
}
