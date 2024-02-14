package net.texala.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class CSVFileManager {

	private static final String FILE_NAME = "AllEmployee.csv";
	private static final String HEADER = "EMPID,First Name,Last Name,Department\n";
	private BufferedWriter errorWriter;

	public CSVFileManager() {
		try {
			errorWriter = new BufferedWriter(new FileWriter("error.log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeData(Vector<Employee> employeeRecords) {
		try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
			fileWriter.write(HEADER);
			for (Employee employee : employeeRecords) {
				fileWriter.write(employee.getEmpId() + "," + employee.getFirstName() + "," + employee.getLastName()
						+ "," + employee.getDepartment() + "\n");
			}
			System.out.println("Changes committed to CSV.");
		} catch (IOException e) {
			System.out.println("Error committing changes: " + e.getMessage());
		}
	}

	public static Vector<Employee> readData() {
		Vector<Employee> records = new Vector<>();
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				int empId = Integer.parseInt(data[0].trim());
				String firstName = data[1].trim();
				String lastName = data[2].trim();
				String department = data[3].trim();
				records.add(new Employee(empId, firstName, lastName, department));
			}
		} catch (IOException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
		return records;
	}

	public void logError(String error) {
		try {
			errorWriter.write(error + "\n");
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

	public Vector<Employee> sortAndDisplay(String sortBy, Vector<Employee> employees) {
		Comparator<Employee> comparator = new EmployeeComparator(sortBy.toLowerCase());

		switch (sortBy.toLowerCase()) {
		case "firstname":
		case "lastname":
		case "empid":
		case "department":
			employees.sort(comparator);
			System.out.println("Sorted Records:");
			System.out.println("EmpId, FirstName, LastName, Department");
			for (Employee employee : employees) {
				System.out.println(employee.getEmpId() + ", " + employee.getFirstName() + ", " + employee.getLastName()
						+ ", " + employee.getDepartment());
			}
			break;
		default:
			System.out.println("Invalid sorting criteria.");
			break;
		}
		return employees;
	}

	public void saveSortedFile(String fileName, Vector<Employee> employees) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(HEADER);
			for (Employee employee : employees) {
				writer.write(employee.getEmpId() + "," + employee.getFirstName() + "," + employee.getLastName() + ","
						+ employee.getDepartment() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void findDuplicatesAndLog(Vector<Employee> employees) {
		Map<Integer, Integer> idCount = new HashMap<>();

		for (Employee employee : employees) {
			int empID = employee.getEmpId();
			idCount.put(empID, idCount.getOrDefault(empID, 0) + 1);
		}

		for (Map.Entry<Integer, Integer> entry : idCount.entrySet()) {
			int empID = entry.getKey();
			int count = entry.getValue();
			if (count > 1) {
				logError("Duplicate employee ID found: " + empID + ". Occurrences: " + count);
			}
		}
	}

	public void sortRecords(String sortBy1, Vector<Employee> employeeRecords) {

		Comparator<Employee> comparator = new Comparator<Employee>() {
			@Override
			public int compare(Employee emp1, Employee emp2) {
				switch (sortBy1.toLowerCase()) {
				case "empid":
					return Integer.compare(emp1.getEmpId(), emp2.getEmpId());
				case "firstname":
					return emp1.getFirstName().compareToIgnoreCase(emp2.getFirstName());
				case "lastname":
					return emp1.getLastName().compareToIgnoreCase(emp2.getLastName());
				case "department":
					return emp1.getDepartment().compareToIgnoreCase(emp2.getDepartment());
				default:

					return Integer.compare(emp1.getEmpId(), emp2.getEmpId());
				}
			}
		};

		Collections.sort(employeeRecords, comparator);
	}
}
