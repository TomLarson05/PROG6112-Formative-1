package com.mycompany.smartmealplanner.model;

/**
 * Dinner Recipe Model Class
 * 
 * Concrete implementation of Recipe for dinner meals.
 * Completes the recipe inheritance hierarchy (Breakfast, Lunch, Dinner)
 * used by the meal planning optimization algorithm.
 */
public class DinnerRecipe extends Recipe {
    
    /**
     * Full constructor for dinner recipe with instructions
     */
    public DinnerRecipe(String name, Ingredient[] ingredients, double calories, 
                       double protein, double carbs, double fat,
                       String[] instructions, int prepTime, int cookTime, String difficulty) {
        super(name, ingredients, calories, protein, carbs, fat, instructions, prepTime, cookTime, difficulty);
    }
    
    /**
     * Constructor for dinner recipe (legacy support)
     */
    public DinnerRecipe(String name, Ingredient[] ingredients, double calories, 
                       double protein, double carbs, double fat) {
        super(name, ingredients, calories, protein, carbs, fat);
    }
    
    /**
     * Constructor with custom base servings (legacy support)
     */
    public DinnerRecipe(String name, Ingredient[] ingredients, double calories, 
                       double protein, double carbs, double fat, double baseServings) {
        super(name, ingredients, calories, protein, carbs, fat, baseServings);
    }
    
    /**
     * Returns the meal type identifier for dinner recipes.
     * 
     * @return "Dinner" meal type string
     */
    @Override
    public String getMealType() {
        return "Dinner";
    }
}