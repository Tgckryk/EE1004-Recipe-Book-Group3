import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RecipeBook book = new RecipeBook();
        book.loadFromFile("recipes.txt");

        System.out.println("Loaded " + book.getRecipeCount() + " recipes from recipes.txt\n");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Recipe Book Menu");
            System.out.println("1. List all recipes");
            System.out.println("2. Filter by category");
            System.out.println("3. Show total cooking time");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println();
                book.listAllRecipes();
                System.out.println();
            } else if (choice.equals("2")) {
                System.out.print("Enter category (appetizer/main/dessert): ");
                String cat = scanner.nextLine().trim();
                System.out.println();
                book.filterByCategory(cat);
                System.out.println();
            } else if (choice.equals("3")) {
                System.out.println("\nTotal cooking time across all recipes: " + book.getTotalCookingTime() + " minutes\n");
            } else if (choice.equals("4")) {
                System.out.println("Exiting. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.\n");
            }
        }
        scanner.close();
    }
}