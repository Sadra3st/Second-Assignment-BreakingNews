package AP;

public class Main {
    public static void main(String[] args) {
        String apiKey = "63843d2d2c194eb2bafc9efeaa5dd15d";
        Infrastructure infrastructure = new Infrastructure(apiKey);

        System.out.println("Welcome to the News App!");
        infrastructure.displayNewsList();

        System.out.println("Do you want to save this article to favorites? (yes/no)");
        String saveChoice = new java.util.Scanner(System.in).nextLine();
        if (saveChoice.equalsIgnoreCase("yes")) {
            int choice = new java.util.Scanner(System.in).nextInt();
            if (choice > 0 && choice <= infrastructure.getNewsList().size()) {
                infrastructure.saveFavorite(infrastructure.getNewsList().get(choice - 1));
            }
        }

        System.out.println("Do you want to view your favorite articles? (yes/no)");
        String viewChoice = new java.util.Scanner(System.in).nextLine();
        if (viewChoice.equalsIgnoreCase("yes")) {
            infrastructure.displayFavorites();
        }
    }
}