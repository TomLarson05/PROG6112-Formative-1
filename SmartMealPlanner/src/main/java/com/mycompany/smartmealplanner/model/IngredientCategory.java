package com.mycompany.smartmealplanner.model;

/**
 * Ingredient categories for organizing grocery lists by store layout
 * Each category includes an emoji and display name for enhanced user experience
 */
public enum IngredientCategory {
    PRODUCE("🥬 Produce"),
    MEAT("🥩 Meat & Seafood"), 
    DAIRY("🥛 Dairy & Eggs"),
    PANTRY("🏪 Pantry & Dry Goods"), 
    FROZEN("❄️ Frozen"), 
    BAKERY("🍞 Bakery"),
    CONDIMENTS("🧂 Condiments & Sauces");
    
    private final String displayName;
    
    IngredientCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get categories in typical grocery store layout order
     * @return Array of categories in shopping order
     */
    public static IngredientCategory[] getShoppingOrder() {
        return new IngredientCategory[] {
            PRODUCE,
            BAKERY,
            MEAT,
            DAIRY,
            FROZEN,
            PANTRY,
            CONDIMENTS
        };
    }
}