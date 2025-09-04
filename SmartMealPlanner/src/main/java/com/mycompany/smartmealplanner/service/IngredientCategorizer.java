package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.IngredientCategory;

/**
 * Service for categorizing ingredients into grocery store sections
 * Based on the actual ingredients used in RecipeLibrary.java
 */
public class IngredientCategorizer {
    
    /**
     * Categorize an ingredient by its name
     * @param ingredientName Name of the ingredient (case insensitive)
     * @return Appropriate IngredientCategory for shopping organization
     */
    public static IngredientCategory categorizeIngredient(String ingredientName) {
        String name = ingredientName.toLowerCase().trim();
        
        // PRODUCE
        if (name.equals("banana") || name.equals("mixed berries") || name.equals("broccoli") ||
            name.equals("mixed vegetables") || name.equals("sweet potato") || name.equals("spinach") ||
            name.equals("avocado") || name.equals("mixed greens") || name.equals("onion") ||
            name.equals("lemon") || name.equals("vegetables") || name.equals("asparagus") ||
            name.equals("green beans") || name.equals("tomato") || name.equals("red onion") ||
            name.equals("corn") || name.equals("potatoes")) {
            return IngredientCategory.PRODUCE;
        }
        
        // MEAT & SEAFOOD
        if (name.equals("chicken breast") || name.equals("tuna") || name.equals("tofu") ||
            name.equals("beef") || name.equals("turkey mince") || name.equals("salmon fillet") ||
            name.equals("chicken thighs") || name.equals("beef strips") || name.equals("chickpeas") ||
            name.equals("bacon") || name.equals("pork chops") || name.equals("shrimp") ||
            name.equals("smoked salmon") || name.equals("lentils (cooked)")) {
            return IngredientCategory.MEAT;
        }
        
        // DAIRY & EGGS
        if (name.equals("milk") || name.equals("greek yogurt") || name.equals("eggs") ||
            name.equals("butter") || name.equals("cheese") || name.equals("feta cheese") ||
            name.equals("cream cheese") || name.equals("sour cream") || name.equals("parmesan") ||
            name.equals("coconut milk") || name.equals("egg")) {
            return IngredientCategory.DAIRY;
        }
        
        // BAKERY
        if (name.equals("whole grain bread") || name.equals("bread slices") ||
            name.equals("whole wheat tortilla") || name.equals("bagel") || name.equals("pancake mix")) {
            return IngredientCategory.BAKERY;
        }
        
        // PANTRY & DRY GOODS
        if (name.equals("oats") || name.equals("rice (cooked)") || name.equals("pasta") ||
            name.equals("quinoa (cooked)") || name.equals("brown rice (cooked)") ||
            name.equals("noodles") || name.equals("spaghetti") || name.equals("kidney beans") ||
            name.equals("black beans") || name.equals("peanut butter") || name.equals("protein powder") ||
            name.equals("honey") || name.equals("rice (day-old)") || name.equals("black pepper")) {
            return IngredientCategory.PANTRY;
        }
        
        // CONDIMENTS & SAUCES
        if (name.equals("olive oil") || name.equals("tomato sauce") || name.equals("soy sauce") ||
            name.equals("sesame oil") || name.equals("hummus") || name.equals("maple syrup") ||
            name.equals("teriyaki sauce") || name.equals("curry sauce") || name.equals("salsa")) {
            return IngredientCategory.CONDIMENTS;
        }
        
        // DEFAULT - if we can't categorize, put in pantry
        return IngredientCategory.PANTRY;
    }
}