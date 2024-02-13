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

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidEmpID(int empID) {
        return empID > 0;
    }

    private void logError(String error) {
        try {
            errorWriter.write(error + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 4) {
                    logError("Error! Invalid record format: " + line);
                    continue;
                }

                try {
                    int empID = Integer.parseInt(parts[0]);
                    String firstName = parts[1];
                    String lastName = parts[2];
                    String department = parts[3];

                    if (!isValidEmpID(empID)) {
                        logError("Error! Invalid Employee ID: " + empID);
                        continue;
                    }

                    boolean validFirstName = isValidName(firstName);
                    boolean validLastName = isValidName(lastName);

                    if (!validFirstName && !validLastName) {
                        logError("Error! Incorrect first name and last name in record: " + line);
                        continue;
                    }

                    if (!validFirstName) {
                        logError("Error! Incorrect first name in record: " + line);
                        continue;
                    }

                    if (!validLastName) {
                        logError("Error! Incorrect last name in record: " + line);
                        continue;
                    }

                    employees.add(new Employee(empID, firstName, lastName, department));
                } catch (NumberFormatException e) {
                    logError("Error! Invalid record format: " + line);
                }
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
 }
