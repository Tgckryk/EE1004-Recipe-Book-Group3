public class AppetizerRecipe extends Recipe {
    public AppetizerRecipe(String name, int prepMinutes, int cookMinutes, int servings) {
        super(name, prepMinutes, cookMinutes, servings);}
    @Override
    public String getCategory() {return "Appetizer";}
}