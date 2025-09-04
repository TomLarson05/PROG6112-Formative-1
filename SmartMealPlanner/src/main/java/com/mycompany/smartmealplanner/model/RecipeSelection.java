package com.mycompany.smartmealplanner.model;

/**
 * Recipe Selection Model Class
 * 
 * Represents a recipe with a specific serving size multiplier.
 * Used by the meal planning algorithm to store optimized recipe
 * choices with their calculated serving amounts.
 */
public class RecipeSelection {
    
    // ===== FIELDS =====
    private Recipe recipe;      // The selected recipe
    private double servings;    // Serving size multiplier (e.g., 1.5x, 2.0x)
    
    /**
     * Constructor for recipe selection with serving multiplier.
     * Creates a recipe selection with specified serving size.
     * 
     * @param recipe The selected recipe object
     * @param servings Serving size multiplier (1.0 = base serving)
     */
    public RecipeSelection(Recipe recipe, double servings) {
        this.recipe = recipe;
        this.servings = servings;
    }
    
    /**
     * Get the macro-nutrients for this recipe selection.
     * Automatically scales nutritional values based on serving multiplier.
     * 
     * @return Macro object with scaled nutritional information
     */
    public Macro getMacros() {
        return recipe.macrosFor(servings);
    }
    
    /**
     * Get the ingredients for this selection
     * @return Scaled ingredients based on serving size
     */
    public Ingredient[] getIngredients() {
        return recipe.ingredientsFor(servings);
    }
    
    // ===== GETTERS =====
    public Recipe getRecipe() {
        return recipe;
    }
    
    public double getServings() {
        return servings;
    }
    
    @Override
    public String toString() {
        return String.format("%s (%.1fx)", recipe.getName(), servings);
    }
}