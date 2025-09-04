package com.mycompany.smartmealplanner.model;

/**
 * Recipe Model Class - Abstract Base
 * 
 * Abstract base class for all recipe types in the meal planning system.
 * Implements the Template Method pattern for recipe inheritance hierarchy.
 * Contains nutritional information, ingredients, and cooking instructions.
 */

/* Oracle 2025
Abstract Classes and Methods (Java Inheritance Tutorial)
Oracle Documentation
https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
Accessed 2 September 2025
*/
public abstract class Recipe {
    
    // ===== FIELDS =====
    private String name;              // Recipe display name
    private Ingredient[] ingredients; // List of required ingredients
    private double calories;          // Calories per base serving
    private double protein;           // Protein in grams per base serving
    private double carbs;             // Carbohydrates in grams per base serving
    private double fat;               // Fat in grams per base serving
    private double baseServings;      // Number of servings this recipe makes
    private String[] instructions;    // Step-by-step cooking directions
    private int prepTime;             // Preparation time in minutes
    private int cookTime;             // Cooking time in minutes
    private String difficulty;        // Complexity level (Easy/Medium/Hard)
    
    /**
     * Full constructor with all recipe details.
     * Initializes a complete recipe with nutritional data and cooking information.
     * Provides defensive programming with null checks for optional fields.
     * 
     * @param name Recipe name for display
     * @param ingredients Array of required ingredients with quantities
     * @param calories Calories per base serving
     * @param protein Protein content per base serving in grams
     * @param carbs Carbohydrate content per base serving in grams
     * @param fat Fat content per base serving in grams
     * @param baseServings Number of servings this recipe produces
     * @param instructions Step-by-step cooking instructions array
     * @param prepTime Preparation time in minutes
     * @param cookTime Active cooking time in minutes
     * @param difficulty Recipe complexity level (Easy/Medium/Hard)
     */
    /* Gamma et al. 1995
       "Design Patterns" - Template Method Pattern
       Addison-Wesley
       ISBN: 978-0201633610
       Accessed 26 August 2025
    */
    public Recipe(String name, Ingredient[] ingredients, double calories, 
                  double protein, double carbs, double fat, double baseServings,
                  String[] instructions, int prepTime, int cookTime, String difficulty) {
        this.name = name;
        this.ingredients = ingredients;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.baseServings = baseServings;
        this.instructions = (instructions != null) ? instructions : new String[]{"No instructions available"};
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.difficulty = (difficulty != null) ? difficulty : "Medium";
    }
    
    /* Oracle 2025
    Defensive Programming with Null Checks
    Oracle Java Tutorials
    https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
    Accessed 27 August 2025
    */
    
    /**
     * Constructor with default base servings of 1.0
     */
    public Recipe(String name, Ingredient[] ingredients, double calories, 
                  double protein, double carbs, double fat,
                  String[] instructions, int prepTime, int cookTime, String difficulty) {
        this(name, ingredients, calories, protein, carbs, fat, 1.0, 
             instructions, prepTime, cookTime, difficulty);
    }
    
    /**
     * Legacy constructor for backward compatibility
     */
    public Recipe(String name, Ingredient[] ingredients, double calories, 
                  double protein, double carbs, double fat, double baseServings) {
        this(name, ingredients, calories, protein, carbs, fat, baseServings,
             new String[]{"Instructions not available"}, 15, 15, "Medium");
    }
    
    /**
     * Legacy constructor with default base servings
     */
    public Recipe(String name, Ingredient[] ingredients, double calories, 
                  double protein, double carbs, double fat) {
        this(name, ingredients, calories, protein, carbs, fat, 1.0,
             new String[]{"Instructions not available"}, 15, 15, "Medium");
    }
    
    /**
     * Get macros scaled for specified number of servings
     * @param servings Number of servings
     * @return Scaled macro nutrients
     */
    public Macro macrosFor(double servings) {
        double scaleFactor = servings / baseServings;
        return new Macro(
            calories * scaleFactor,
            protein * scaleFactor,
            carbs * scaleFactor,
            fat * scaleFactor
        );
    }
    
    /**
     * Get ingredients scaled for specified number of servings
     * @param servings Number of servings
     * @return Array of scaled ingredients
     */
    public Ingredient[] ingredientsFor(double servings) {
        double scaleFactor = servings / baseServings;
        Ingredient[] scaledIngredients = new Ingredient[ingredients.length];
        
        for (int i = 0; i < ingredients.length; i++) {
            scaledIngredients[i] = ingredients[i].scaleToServings(scaleFactor);
        }
        
        return scaledIngredients;
    }
    
    /* Stack Overflow 2013
    How to scale recipe quantities by serving size in Java
    Stack Overflow
    https://stackoverflow.com/questions/16504152/how-to-scale-recipe-quantities-by-serving-size-in-java
    Author: Stephen C
    https://stackoverflow.com/users/131872/stephen-c
    Accessed 28 August 2025
    */
    
    /**
     * Check if recipe contains any disliked ingredients
     * @param dislikes Array of disliked ingredient keywords
     * @return true if recipe contains any disliked ingredients
     */
    public boolean matchesDislikes(String[] dislikes) {
        if (dislikes == null || dislikes.length == 0) {
            return false;
        }
        
        // Check recipe name
        for (String dislike : dislikes) {
            if (dislike != null && !dislike.isEmpty()) {
                if (name.toLowerCase().contains(dislike.toLowerCase())) {
                    return true;
                }
                
                // Check each ingredient
                for (Ingredient ingredient : ingredients) {
                    if (ingredient.getName().toLowerCase().contains(dislike.toLowerCase())) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    /**
     * Get the meal type of this recipe
     * @return String representation of meal type
     */
    public abstract String getMealType();
    
    /**
     * Get formatted instructions as a single string
     * @return Formatted step-by-step instructions
     */
    public String getFormattedInstructions() {
        if (instructions == null || instructions.length == 0) {
            return "No instructions available";
        }
        
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < instructions.length; i++) {
            formatted.append(String.format("%d. %s%n", i + 1, instructions[i]));
        }
        return formatted.toString();
    }
    
    /**
     * Get total time (prep + cook)
     * @return Total time in minutes
     */
    public int getTotalTime() {
        return prepTime + cookTime;
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public Ingredient[] getIngredients() {
        return ingredients;
    }
    
    public double getCalories() {
        return calories;
    }
    
    public double getProtein() {
        return protein;
    }
    
    public double getCarbs() {
        return carbs;
    }
    
    public double getFat() {
        return fat;
    }
    
    public double getBaseServings() {
        return baseServings;
    }
    
    public String[] getInstructions() {
        return instructions;
    }
    
    public int getPrepTime() {
        return prepTime;
    }
    
    public int getCookTime() {
        return cookTime;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s): %.0f kcal, P%.0fg, C%.0fg, F%.0fg", 
            name, getMealType(), calories, protein, carbs, fat);
    }
}