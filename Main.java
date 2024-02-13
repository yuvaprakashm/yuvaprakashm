package net.texala.main;

import java.util.Scanner;

import net.texala.employee_processor.Processor;

public class Main {
    public static void main(String[] args) {
        Processor pro = new Processor();
        Scanner scanner = new Scanner(System.in);

        pro.readCSV("record.csv");

        while (true) {

            System.out.println("Choose sorting criteria:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Employee ID");
            System.out.println("4. Department");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            String sortBy = null;

            switch (choice) {
                case 1:
                    sortBy = "firstname";
                    break;
                case 2:
                    sortBy = "lastname";
                    break;
                case 3:
                    sortBy = "empid";
                    break;
                case 4:
                    sortBy = "department";
                    break;
                case 5:
                    sortBy = "exit";
                    System.out.println("Terminating the program");
                    scanner.close(); 
                    return;  
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    continue;
            }

            pro.sortAndDisplay(sortBy);
            pro.saveSortedFile("sorted_output.csv");
            pro.findDuplicatesAndLog();
            pro.closeErrorWriter();
        }
    }
}
