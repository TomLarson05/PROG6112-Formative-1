package com.mycompany.smartmealplanner.model;

/**
 * Plan Day Model Class
 * 
 * Represents a complete day's meal plan containing breakfast, lunch, and dinner.
 * Provides aggregation methods for calculating total daily macros and
 * ingredient lists for grocery shopping functionality.
 */
public class PlanDay {
    
    // ===== FIELDS =====
    private RecipeSelection breakfast;  // Morning meal selection
    private RecipeSelection lunch;      // Midday meal selection
    private RecipeSelection dinner;     // Evening meal selection
    private int dayNumber;              // Day number in meal plan (1-indexed)
    
    /**
     * Constructor for a day's meal plan
     * @param dayNumber Day number in the plan
     * @param breakfast Breakfast selection
     * @param lunch Lunch selection
     * @param dinner Dinner selection
     */
    public PlanDay(int dayNumber, RecipeSelection breakfast, RecipeSelection lunch, RecipeSelection dinner) {
        this.dayNumber = dayNumber;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }
    
    /**
     * Calculate total daily macro-nutrients.
     * Aggregates nutritional information from all three meals.
     * 
     * @return Combined Macro object with total daily nutrition
     */
    public Macro getDailyMacros() {
        Macro total = new Macro();  // Start with zero values
        
        // Add macros from each meal if present
        if (breakfast != null) {
            total = total.add(breakfast.getMacros());
        }
        if (lunch != null) {
            total = total.add(lunch.getMacros());
        }
        if (dinner != null) {
            total = total.add(dinner.getMacros());
        }
        
        return total;
    }
    
    /**
     * Get all ingredients needed for the day.
     * Combines ingredients from all meals for grocery list generation.
     * 
     * @return Array containing all ingredients with scaled quantities
     */
    public Ingredient[] getAllIngredients() {
        int totalIngredients = 0;
        
        if (breakfast != null) {
            totalIngredients += breakfast.getIngredients().length;
        }
        if (lunch != null) {
            totalIngredients += lunch.getIngredients().length;
        }
        if (dinner != null) {
            totalIngredients += dinner.getIngredients().length;
        }
        
        Ingredient[] allIngredients = new Ingredient[totalIngredients];
        int index = 0;
        
        if (breakfast != null) {
            for (Ingredient ing : breakfast.getIngredients()) {
                allIngredients[index++] = ing;
            }
        }
        if (lunch != null) {
            for (Ingredient ing : lunch.getIngredients()) {
                allIngredients[index++] = ing;
            }
        }
        if (dinner != null) {
            for (Ingredient ing : dinner.getIngredients()) {
                allIngredients[index++] = ing;
            }
        }
        
        return allIngredients;
    }
    
    // ===== GETTERS AND SETTERS =====
    public RecipeSelection getBreakfast() {
        return breakfast;
    }
    
    public void setBreakfast(RecipeSelection breakfast) {
        this.breakfast = breakfast;
    }
    
    public RecipeSelection getLunch() {
        return lunch;
    }
    
    public void setLunch(RecipeSelection lunch) {
        this.lunch = lunch;
    }
    
    public RecipeSelection getDinner() {
        return dinner;
    }
    
    public void setDinner(RecipeSelection dinner) {
        this.dinner = dinner;
    }
    
    public int getDayNumber() {
        return dayNumber;
    }
}