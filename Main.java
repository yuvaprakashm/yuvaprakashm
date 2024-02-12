package net.texala.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Scanner scanner = new Scanner(System.in);

        processor.readCSV("record.csv");

        boolean exit = false;

        while (!exit) {
            // Display menu
            System.out.println("Choose sorting criteria:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Employee ID");
            System.out.println("4. Department");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Read user choice
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
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    continue;
            }

            if (!exit) {
                processor.sortAndDisplay(sortBy);

                processor.saveSortedFile("sorted_output.csv");

                processor.closeErrorWriter();
            }
        }

        System.out.println("Exiting...");

        scanner.close();
    }
}
