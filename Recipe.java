public abstract class Recipe {
    protected String name;
    protected int prepMinutes;
    protected int cookMinutes;
    protected int servings;

    public Recipe(String name, int prepMinutes, int cookMinutes, int servings) {
        this.name = name; this.prepMinutes = prepMinutes; this.cookMinutes = cookMinutes; this.servings = servings;
    }
    public int getTotalTime() {return prepMinutes + cookMinutes;}
    public abstract String getCategory();
    @Override
    public String toString() {
        return String.format("[%s] %s\nPrep: %d min, Cook: %d min, Total: %d min, Serves: %d",
                getCategory(), name, prepMinutes, cookMinutes, getTotalTime(), servings);
    }
}
