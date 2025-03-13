package AP;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Infrastructure infrastructure = new Infrastructure("63843d2d2c194eb2bafc9efeaa5dd15d");

        while (true) {

            System.out.println("\n\ud83d\udce2 News Menu:");
            System.out.println("1. View News Articles");
            System.out.println("2. View Favorite Articles");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                infrastructure.displayNewsList();
                System.out.print("Enter the number of the article you want to read (or 0 to go back): ");
                int newsChoice = scanner.nextInt();
                scanner.nextLine();

                if (newsChoice == 0) continue;

                if (newsChoice > 0 && newsChoice <= infrastructure.getNewsList().size()) {
                    News selectedNews = infrastructure.getNewsList().get(newsChoice - 1);

                    selectedNews.displayNews();

                    System.out.print("Do you want to save this article as a favorite? (yes/no): ");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("yes")) {
                        infrastructure.saveFavoriteArticle(selectedNews);
                    }

                    System.out.print("Do you want to go back to the menu? (yes/no): ");
                    String backChoice = scanner.nextLine();
                    if (!backChoice.equalsIgnoreCase("yes")) {
                        break;
                    }
                } else {
                    System.out.println("⚠ Invalid choice!");
                }
            } else if (choice == 2) {

                infrastructure.loadFavoriteArticles();
                System.out.print("Press Enter to return to the menu...");
                scanner.nextLine();
            } else if (choice == 3) {
                System.out.println("Exiting program...");
                break;
            } else {
                System.out.println("⚠ Invalid option.");
            }
        }
        scanner.close();
    }

}