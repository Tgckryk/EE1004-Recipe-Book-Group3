import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeBook {
    private ArrayList<Recipe> recipes;

    public RecipeBook() {recipes = new ArrayList<>();}
    public void addRecipe(Recipe r) {recipes.add(r);}

    public void listAllRecipes() {
        if (recipes.isEmpty()) {System.out.println("Recipe book is empty.");return;}
        for (Recipe r : recipes) {System.out.println("- " + r.toString());}
    }

    public void filterByCategory(String category) {
        boolean found = false;
        for (Recipe r : recipes) {
            if (r.getCategory().equalsIgnoreCase(category)) {System.out.println("- " + r.toString()); found = true;}
        }
        if (!found) {System.out.println("No recipes found for category: " + category);}
    }

    public int getTotalCookingTime() {
        int total = 0;
        for (Recipe r : recipes) {total += r.getTotalTime();}
        return total;
    }

    public int getRecipeCount() {return recipes.size();}

    public void loadFromFile(String path) {
        try (Scanner fileScanner = new Scanner(new File(path))) {
            String type = null, name = null, prepStr = null, cookStr = null, servesStr = null;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("TYPE:")) {
                    processRecipeBlock(type, name, prepStr, cookStr, servesStr);
                    type = line.substring(5).trim();
                    name = null; prepStr = null; cookStr = null; servesStr = null;
                } else if (line.startsWith("NAME:")) {name = line.substring(5).trim();
                } else if (line.startsWith("PREP:")) {prepStr = line.substring(5).trim();
                } else if (line.startsWith("COOK:")) {cookStr = line.substring(5).trim();
                } else if (line.startsWith("SERVES:")) {servesStr = line.substring(7).trim();
                }
            }
            processRecipeBlock(type, name, prepStr, cookStr, servesStr);

        } catch (FileNotFoundException e) {System.out.println("Error: File not found at " + path);}
    }

    private void processRecipeBlock(String type, String name, String prepStr, String cookStr, String servesStr) {
        if (type == null && name == null && prepStr == null && cookStr == null && servesStr == null) return;
        if (type == null || type.isEmpty() || name == null || name.isEmpty() ||
                prepStr == null || prepStr.isEmpty() || cookStr == null || cookStr.isEmpty() ||
                servesStr == null || servesStr.isEmpty()) {
            System.out.println("Skipping invalid entry: Missing or empty required field.");
            return;
        }

        try {
            int prep = Integer.parseInt(prepStr);
            int cook = Integer.parseInt(cookStr);
            int serves = Integer.parseInt(servesStr);
            if (prep < 0 || cook < 0 || serves < 0) {
                System.out.println("Skipping invalid entry: PREP, COOK, or SERVES is not a non-negative integer.");
                return;
            }

            if (type.equalsIgnoreCase("APPETIZER")) {addRecipe(new AppetizerRecipe(name, prep, cook, serves));
            } else if (type.equalsIgnoreCase("MAIN")) {addRecipe(new MainCourseRecipe(name, prep, cook, serves));
            } else if (type.equalsIgnoreCase("DESSERT")) {addRecipe(new DessertRecipe(name, prep, cook, serves));
            } else {System.out.println("Unknown recipe type. Skipping...");
            }

        } catch (NumberFormatException e) {System.out.println("Skipping invalid entry: PREP, COOK, or SERVES is not a valid integer.");}
    }
}