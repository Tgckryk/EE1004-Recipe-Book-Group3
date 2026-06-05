public class MainCourseRecipe extends Recipe {
    public MainCourseRecipe(String name, int prepMinutes, int cookMinutes, int servings) {
        super(name, prepMinutes, cookMinutes, servings);}
    @Override
    public String getCategory() {
        return "Main";}
}