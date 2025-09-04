package com.mycompany.smartmealplanner.model;

/**
 * Breakfast Recipe Model Class
 * 
 * Concrete implementation of Recipe for breakfast meals.
 * Inherits all functionality from Recipe base class and
 * specifies meal type for meal planning categorization.
 */
public class BreakfastRecipe extends Recipe {
    
    /**
     * Full constructor for breakfast recipe with complete details.
     * Creates a breakfast recipe with all nutritional and cooking information.
     * 
     * @param name Recipe name
     * @param ingredients Required ingredients array
     * @param calories Calories per serving
     * @param protein Protein in grams per serving
     * @param carbs Carbohydrates in grams per serving
     * @param fat Fat in grams per serving
     * @param instructions Cooking steps
     * @param prepTime Preparation time in minutes
     * @param cookTime Cooking time in minutes
     * @param difficulty Recipe complexity level
     */
    public BreakfastRecipe(String name, Ingredient[] ingredients, double calories, 
                          double protein, double carbs, double fat,
                          String[] instructions, int prepTime, int cookTime, String difficulty) {
        super(name, ingredients, calories, protein, carbs, fat, instructions, prepTime, cookTime, difficulty);
    }
    
    /**
     * Constructor for breakfast recipe (legacy support)
     */
    public BreakfastRecipe(String name, Ingredient[] ingredients, double calories, 
                          double protein, double carbs, double fat) {
        super(name, ingredients, calories, protein, carbs, fat);
    }
    
    /**
     * Constructor with custom base servings (legacy support)
     */
    public BreakfastRecipe(String name, Ingredient[] ingredients, double calories, 
                          double protein, double carbs, double fat, double baseServings) {
        super(name, ingredients, calories, protein, carbs, fat, baseServings);
    }
    
    /**
     * Returns the meal type for this recipe.
     * Used by the meal planning algorithm for categorization.
     * 
     * @return "Breakfast" meal type identifier
     */
    @Override
    public String getMealType() {
        return "Breakfast";
    }
}