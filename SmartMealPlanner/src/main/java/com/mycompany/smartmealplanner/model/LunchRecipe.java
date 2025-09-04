package com.mycompany.smartmealplanner.model;

/**
 * Lunch Recipe Model Class
 * 
 * Concrete implementation of Recipe for lunch meals.
 * Part of the recipe inheritance hierarchy used by the
 * meal planning algorithm for proper categorization.
 */

/* Oracle 2025
   Inheritance: Abstract Classes and Subclasses
   Oracle Documentation
   https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html
   Accessed 3 September 2025
*/
public class LunchRecipe extends Recipe {
    
    /**
     * Full constructor for lunch recipe with instructions
     */
    public LunchRecipe(String name, Ingredient[] ingredients, double calories, 
                      double protein, double carbs, double fat,
                      String[] instructions, int prepTime, int cookTime, String difficulty) {
        super(name, ingredients, calories, protein, carbs, fat, instructions, prepTime, cookTime, difficulty);
    }
    
   /* Oracle 2025
   Using super() to call superclass constructors
   Oracle Documentation
   https://docs.oracle.com/javase/tutorial/java/IandI/super.html
   Accessed 3 September 2025
   */
    
    /**
     * Constructor for lunch recipe (legacy support)
     */
    public LunchRecipe(String name, Ingredient[] ingredients, double calories, 
                      double protein, double carbs, double fat) {
        super(name, ingredients, calories, protein, carbs, fat);
    }
    
    /**
     * Constructor with custom base servings (legacy support)
     */
    public LunchRecipe(String name, Ingredient[] ingredients, double calories, 
                      double protein, double carbs, double fat, double baseServings) {
        super(name, ingredients, calories, protein, carbs, fat, baseServings);
    }
    
    /**
     * Returns the meal type identifier for lunch recipes.
     * 
     * @return "Lunch" meal type string
     */
    @Override
    public String getMealType() {
        return "Lunch";
    }
    
    /* Oracle 2025
    Overriding Methods
    Oracle Documentation
    https://docs.oracle.com/javase/tutorial/java/IandI/override.html
    Accessed 3 September 2025
    */
}