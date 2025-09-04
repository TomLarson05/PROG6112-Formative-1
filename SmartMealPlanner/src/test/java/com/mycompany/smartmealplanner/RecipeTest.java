package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Recipe classes.
 * Tests recipe scaling, dislike filtering, and inheritance.
 */
public class RecipeTest {
    
    private Recipe testRecipe;
    private Ingredient[] ingredients;
    
    @BeforeEach
    public void setUp() {
        ingredients = new Ingredient[] {
            new Ingredient("chicken", "g", 150),
            new Ingredient("rice", "g", 100),
            new Ingredient("broccoli", "g", 50)
        };
        
        testRecipe = new LunchRecipe(
            "Chicken Bowl", 
            ingredients, 
            450, 35, 50, 12
        );
    }
    
    /**
     * Test recipe macros scaling
     */
    @Test
    public void testRecipeMacroScaling() {
        // Test scaling to 1.5 servings
        Macro scaled = testRecipe.macrosFor(1.5);
        
        assertEquals(675, scaled.getCalories(), 0.01, 
            "Calories should be scaled to 1.5x");
        assertEquals(52.5, scaled.getProtein(), 0.01, 
            "Protein should be scaled to 1.5x");
        assertEquals(75, scaled.getCarbs(), 0.01, 
            "Carbs should be scaled to 1.5x");
        assertEquals(18, scaled.getFat(), 0.01, 
            "Fat should be scaled to 1.5x");
    }
    
    /**
     * Test ingredient scaling
     */
    @Test
    public void testIngredientScaling() {
        Ingredient[] scaled = testRecipe.ingredientsFor(2.0);
        
        assertEquals(3, scaled.length, 
            "Should have same number of ingredients");
        assertEquals(300, scaled[0].getQuantityPerServing(), 0.01, 
            "Chicken should be doubled");
        assertEquals(200, scaled[1].getQuantityPerServing(), 0.01, 
            "Rice should be doubled");
        assertEquals(100, scaled[2].getQuantityPerServing(), 0.01, 
            "Broccoli should be doubled");
    }
    
    /**
     * Test dislike filtering - match in recipe name
     */
    @Test
    public void testDislikeFilteringByName() {
        String[] dislikes = {"chicken", "beef"};
        
        assertTrue(testRecipe.matchesDislikes(dislikes), 
            "Should match when dislike is in recipe name");
    }
    
    /**
     * Test dislike filtering - match in ingredients
     */
    @Test
    public void testDislikeFilteringByIngredient() {
        String[] dislikes = {"broccoli"};
        
        assertTrue(testRecipe.matchesDislikes(dislikes), 
            "Should match when dislike is in ingredients");
    }
    
    /**
     * Test dislike filtering - no match
     */
    @Test
    public void testDislikeFilteringNoMatch() {
        String[] dislikes = {"tuna", "beef"};
        
        assertFalse(testRecipe.matchesDislikes(dislikes), 
            "Should not match when no dislikes present");
    }
    
    /**
     * Test dislike filtering - case insensitive
     */
    @Test
    public void testDislikeFilteringCaseInsensitive() {
        String[] dislikes = {"CHICKEN"};
        
        assertTrue(testRecipe.matchesDislikes(dislikes), 
            "Should match regardless of case");
    }
    
    /**
     * Test empty dislikes array
     */
    @Test
    public void testEmptyDislikes() {
        String[] dislikes = {};
        
        assertFalse(testRecipe.matchesDislikes(dislikes), 
            "Should not match with empty dislikes");
    }
    
    /**
     * Test null dislikes array
     */
    @Test
    public void testNullDislikes() {
        assertFalse(testRecipe.matchesDislikes(null), 
            "Should handle null dislikes array");
    }
    
    /**
     * Test inheritance - meal type
     */
    @Test
    public void testMealTypeInheritance() {
        Recipe breakfast = new BreakfastRecipe("Test", ingredients, 300, 20, 30, 10);
        Recipe lunch = new LunchRecipe("Test", ingredients, 300, 20, 30, 10);
        Recipe dinner = new DinnerRecipe("Test", ingredients, 300, 20, 30, 10);
        
        assertEquals("Breakfast", breakfast.getMealType());
        assertEquals("Lunch", lunch.getMealType());
        assertEquals("Dinner", dinner.getMealType());
    }
}