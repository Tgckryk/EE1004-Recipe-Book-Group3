public class DessertRecipe extends Recipe {
    public DessertRecipe(String name, int prepMinutes, int cookMinutes, int servings) {
        super(name, prepMinutes, cookMinutes, servings);}
    @Override
    public String getCategory() {return "Dessert";}
}
