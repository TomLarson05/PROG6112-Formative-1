package com.mycompany.smartmealplanner.model;

/**
 * Macro-Nutrient Model Class
 * 
 * Represents nutritional information including calories, protein, carbohydrates, and fat.
 * Provides immutable operations for combining and scaling nutritional data.
 * Used throughout the application for nutrition tracking and target setting.
 */
public class Macro {
    
    // ===== FIELDS =====
    private double calories;  // Total energy in kilocalories
    private double protein;   // Protein content in grams
    private double carbs;     // Carbohydrate content in grams
    private double fat;       // Fat content in grams
    
    /**
     * Default constructor
     */
    public Macro() {
        this(0, 0, 0, 0);
    }
    
    /**
     * Constructor with all macro-nutrient values
     * @param calories Total calories
     * @param protein Protein in grams
     * @param carbs Carbohydrates in grams
     * @param fat Fat in grams
     */
    public Macro(double calories, double protein, double carbs, double fat) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }
    
    /**
     * Copy constructor
     * @param other Macro to copy from
     */
    public Macro(Macro other) {
        this.calories = other.calories;
        this.protein = other.protein;
        this.carbs = other.carbs;
        this.fat = other.fat;
    }
    
    /**
     * Add another macro to this one and return a new Macro.
     * Creates immutable result - original objects remain unchanged.
     * 
     * @param other Macro to add
     * @return New Macro with combined values
     */
    public Macro add(Macro other) {
        return new Macro(
            this.calories + other.calories,
            this.protein + other.protein,
            this.carbs + other.carbs,
            this.fat + other.fat
        );
        /* Fowler, M. 2018
           "Refactoring" - Chapter 11: API Refactoring
           Addison-Wesley, 2nd Edition
           ISBN: 978-0134757599
           Accessed 22 August 2025
        */
    }
    
    /**
     * Subtract another macro from this one and return a new Macro
     * @param other Macro to subtract
     * @return New Macro with difference
     */
    public Macro minus(Macro other) {
        return new Macro(
            this.calories - other.calories,
            this.protein - other.protein,
            this.carbs - other.carbs,
            this.fat - other.fat
        );
    }
    
    /**
     * Scale all macro values by a factor
     * @param factor Scaling factor
     * @return New Macro with scaled values
     */
    public Macro scale(double factor) {
        return new Macro(
            this.calories * factor,
            this.protein * factor,
            this.carbs * factor,
            this.fat * factor
        );
    }
    
    // ===== GETTERS AND SETTERS =====
    public double getCalories() {
        return calories;
    }
    
    public void setCalories(double calories) {
        this.calories = calories;
    }
    
    public double getProtein() {
        return protein;
    }
    
    public void setProtein(double protein) {
        this.protein = protein;
    }
    
    public double getCarbs() {
        return carbs;
    }
    
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }
    
    public double getFat() {
        return fat;
    }
    
    public void setFat(double fat) {
        this.fat = fat;
    }
    
    /**
     * Format macro information for display.
     * Uses standard nutrition label format.
     * 
     * @return Formatted string with macro values
     */
    @Override
    public String toString() {
        return String.format("%.0f kcal | P %.0fg | C %.0fg | F %.0fg", 
            calories, protein, carbs, fat);
    }
}