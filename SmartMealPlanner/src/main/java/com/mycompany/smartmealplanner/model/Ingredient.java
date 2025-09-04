package com.mycompany.smartmealplanner.model;

/**
 * Ingredient Model Class
 * 
 * Represents a single ingredient in a recipe with quantity and unit information.
 * Supports scaling for different serving sizes to calculate shopping lists.
 */
public class Ingredient {
    
    // ===== FIELDS =====
    private String name;                // Ingredient name (e.g., "chicken breast")
    private String unit;                // Unit of measurement (g, ml, pc, etc.)
    private double quantityPerServing;  // Amount needed per single serving
    
    /**
     * Constructor for creating an ingredient
     * @param name Name of the ingredient
     * @param unit Unit of measurement (g, ml, pc, etc.)
     * @param quantityPerServing Amount per serving
     */
    public Ingredient(String name, String unit, double quantityPerServing) {
        this.name = name;
        this.unit = unit;
        this.quantityPerServing = quantityPerServing;
    }
    
    /**
     * Copy constructor for creating a duplicate ingredient.
     * Used when scaling recipes to preserve original data.
     * 
     * @param other Ingredient to copy from
     */
    public Ingredient(Ingredient other) {
        this.name = other.name;
        this.unit = other.unit;
        this.quantityPerServing = other.quantityPerServing;
        /* Bloch, J. 2018
           "Effective Java" - Item 13: Override clone judiciously
           Addison-Wesley, 3rd Edition
           ISBN: 978-0134685991
           Accessed 29 August 2025
        */
    }
    
    /**
     * Create a scaled version of this ingredient
     * @param servings Number of servings to scale to
     * @return New Ingredient with scaled quantity
     */
    public Ingredient scaleToServings(double servings) {
        return new Ingredient(name, unit, quantityPerServing * servings);
    }
    
    // ===== GETTERS AND SETTERS =====
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public double getQuantityPerServing() {
        return quantityPerServing;
    }
    
    public void setQuantityPerServing(double quantityPerServing) {
        this.quantityPerServing = quantityPerServing;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %.1f %s", name, quantityPerServing, unit);
    }
}