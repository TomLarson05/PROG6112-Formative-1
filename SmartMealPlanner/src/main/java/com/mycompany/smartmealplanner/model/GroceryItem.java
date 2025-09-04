package com.mycompany.smartmealplanner.model;

/**
 * Represents a single item in a grocery list with category and check-off functionality
 */
public class GroceryItem {
    private String name;
    private double quantity;
    private String unit;
    private IngredientCategory category;
    private boolean checkedOff;
    
    public GroceryItem(String name, double quantity, String unit, IngredientCategory category) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.category = category;
        this.checkedOff = false;
    }
    
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public IngredientCategory getCategory() {
        return category;
    }
    
    public void setCategory(IngredientCategory category) {
        this.category = category;
    }
    
    public boolean isCheckedOff() {
        return checkedOff;
    }
    
    public void setCheckedOff(boolean checkedOff) {
        this.checkedOff = checkedOff;
    }
    
    public void toggleCheckOff() {
        this.checkedOff = !this.checkedOff;
    }
    
    /**
     * Get the formatted quantity string (removes .0 for whole numbers)
     */
    public String getFormattedQuantity() {
        if (quantity == Math.floor(quantity)) {
            return String.format("%.0f %s", quantity, unit);
        } else {
            return String.format("%.1f %s", quantity, unit);
        }
    }
    
    /**
     * Get checkbox symbol based on checked off status
     */
    public String getCheckboxSymbol() {
        return checkedOff ? "✅" : "☐";
    }
    
    @Override
    public String toString() {
        return String.format("%s %s - %s", getCheckboxSymbol(), name, getFormattedQuantity());
    }
}