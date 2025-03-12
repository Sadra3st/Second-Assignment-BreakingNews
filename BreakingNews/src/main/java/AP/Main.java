package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String API_KEY = "63843d2d2c194eb2bafc9efeaa5dd15d"; // Replace with your API Key
        Infrastructure infra = new Infrastructure(API_KEY);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n----- NEWS APPLICATION -----");
            System.out.println("1. Show news list");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    infra.displayNewsList();
                    break;
                case 2:
                    System.out.println("Exiting application...");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
