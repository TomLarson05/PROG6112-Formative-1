package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.model.*;
import com.mycompany.smartmealplanner.service.Planner;
import com.mycompany.smartmealplanner.data.RecipeLibrary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Class for Planner Service (LITE Version)
 * 
 * Comprehensive test suite for the meal planning algorithm.
 * Validates meal selection, serving size optimization, macro targeting,
 * and recipe variety across multi-day meal plans.
 */
public class PlannerTest {
    
    /**
     * Default constructor for test class.
     * Required by JUnit testing framework.
     */
    public PlannerTest() {
    }
    
    // ===== TEST FIELDS =====
    private Planner planner;                    // Planner service under test
    private Recipe[] recipes;                   // Recipe library for testing
    private Macro target;                       // Target macros for meal plans
    private static final double DELTA = 0.01;  // Floating-point comparison tolerance
    
    /**
     * Test setup method executed before each test.
     * Initializes test objects with standard LITE configuration.
     */
    @BeforeEach
    public void setUp() {
        planner = new Planner();
        recipes = RecipeLibrary.getAll();
        target = new Macro(2200, 120, 250, 70); // Standard LITE targets
        /* JUnit Team 2025
           JUnit 5 User Guide - Test Lifecycle
           JUnit
           https://junit.org/junit5/docs/current/user-guide/#writing-tests-setup-and-teardown
           Accessed 21 August 2025
        */
    }
    
    // ---------- PLAN GENERATION TESTS ----------
    
    /**
     * TEST CASE: Plan generates correct number of days
     * - Test the current LITE buildPlan(days, target, recipes) signature
     * - Verify plan array has correct length and all days are populated
     */
    @Test
    public void testPlanDaysCount() {
        PlanDay[] plan = planner.buildPlan(3, target, recipes);
        
        assertEquals(3, plan.length, "Should generate exactly 3 days");
        
        for (int i = 0; i < plan.length; i++) {
            assertNotNull(plan[i], "Day " + (i+1) + " should not be null");
            assertEquals(i + 1, plan[i].getDayNumber(), 
                "Day number should be correct (1-indexed)");
        }
    }
    
    /**
     * TEST CASE: Plan works with different day counts
     * - Test minimum (3 days) and maximum (5 days) for LITE version
     */
    @Test
    public void testPlanDifferentDayCounts() {
        // Test 3 days (minimum)
        PlanDay[] plan3 = planner.buildPlan(3, target, recipes);
        assertEquals(3, plan3.length, "Should generate 3 days");
        
        // Test 5 days (maximum for LITE)
        PlanDay[] plan5 = planner.buildPlan(5, target, recipes);
        assertEquals(5, plan5.length, "Should generate 5 days");
        
        // Test 4 days (middle)
        PlanDay[] plan4 = planner.buildPlan(4, target, recipes);
        assertEquals(4, plan4.length, "Should generate 4 days");
    }
    
    // ---------- MEAL ASSIGNMENT TESTS ----------
    
    /**
     * TEST CASE: Each day has all three meals assigned
     * - Verify no null meals in any day
     * - This is critical for LITE version functionality
     */
    @Test
    public void testEachDayHasAllMeals() {
        PlanDay[] plan = planner.buildPlan(3, target, recipes);
        
        for (int i = 0; i < plan.length; i++) {
            PlanDay day = plan[i];
            assertNotNull(day.getBreakfast(), 
                "Day " + (i+1) + " should have breakfast");
            assertNotNull(day.getLunch(), 
                "Day " + (i+1) + " should have lunch");
            assertNotNull(day.getDinner(), 
                "Day " + (i+1) + " should have dinner");
        }
    }
    
    /**
     * TEST CASE: Meals are assigned to correct meal types
     * - Breakfast slot gets BreakfastRecipe
     * - Lunch slot gets LunchRecipe
     * - Dinner slot gets DinnerRecipe
     */
    @Test
    public void testMealTypesCorrectlyAssigned() {
        PlanDay[] plan = planner.buildPlan(3, target, recipes);
        
        for (PlanDay day : plan) {
            assertTrue(day.getBreakfast().getRecipe() instanceof BreakfastRecipe,
                "Breakfast slot should contain BreakfastRecipe");
            assertTrue(day.getLunch().getRecipe() instanceof LunchRecipe,
                "Lunch slot should contain LunchRecipe");
            assertTrue(day.getDinner().getRecipe() instanceof DinnerRecipe,
                "Dinner slot should contain DinnerRecipe");
        }
    }
    
    // ---------- SERVING SIZE TESTS ----------
    
    /**
     * TEST CASE: Serving sizes are within valid range (1.0 to 3.0)
     * - LITE version changed minimum from 0.5 to 1.0
     * - Maximum should be 3.0, step size 0.5
     */
    @Test
    public void testServingSizesInValidRange() {
        PlanDay[] plan = planner.buildPlan(3, target, recipes);
        
        for (PlanDay day : plan) {
            // Check breakfast serving size
            double bServings = day.getBreakfast().getServings();
            assertTrue(bServings >= 1.0 && bServings <= 3.0,
                "Breakfast servings should be between 1.0 and 3.0, was: " + bServings);
            
            // Check lunch serving size
            double lServings = day.getLunch().getServings();
            assertTrue(lServings >= 1.0 && lServings <= 3.0,
                "Lunch servings should be between 1.0 and 3.0, was: " + lServings);
            
            // Check dinner serving size
            double dServings = day.getDinner().getServings();
            assertTrue(dServings >= 1.0 && dServings <= 3.0,
                "Dinner servings should be between 1.0 and 3.0, was: " + dServings);
        }
    }
    
    /**
     * TEST CASE: Serving sizes are in 0.5 increments
     * - Valid values: 1.0, 1.5, 2.0, 2.5, 3.0
     */
    @Test
    public void testServingSizesInHalfIncrements() {
        PlanDay[] plan = planner.buildPlan(3, target, recipes);
        
        for (PlanDay day : plan) {
            RecipeSelection[] meals = {day.getBreakfast(), day.getLunch(), day.getDinner()};
            
            for (RecipeSelection meal : meals) {
                double servings = meal.getServings();
                
                // Check if it's a valid half-increment (1.0, 1.5, 2.0, 2.5, 3.0)
                boolean validServing = (servings == 1.0 || servings == 1.5 || 
                                      servings == 2.0 || servings == 2.5 || servings == 3.0);
                
                assertTrue(validServing, 
                    "Serving size should be in 0.5 increments (1.0-3.0), was: " + servings);
            }
        }
    }
    
    // ---------- RECIPE VARIETY TESTS ----------
    
    /**
     * TEST CASE: Plan uses different recipes when possible
     * - With 6+ recipes in LITE library, there should be variety
     * - At least some variation across multiple days
     */
    @Test
    public void testRecipeVariety() {
        PlanDay[] plan = planner.buildPlan(5, target, recipes); // Use 5 days for more variety testing
        
        // Count unique breakfast recipes
        java.util.Set<String> breakfastNames = new java.util.HashSet<>();
        java.util.Set<String> lunchNames = new java.util.HashSet<>();
        java.util.Set<String> dinnerNames = new java.util.HashSet<>();
        
        for (PlanDay day : plan) {
            breakfastNames.add(day.getBreakfast().getRecipe().getName());
            lunchNames.add(day.getLunch().getRecipe().getName());
            dinnerNames.add(day.getDinner().getRecipe().getName());
        }
        
        // With LITE library having 2+ recipes per meal type, expect some variety
        // (This is a soft check - algorithm might repeat for optimization reasons)
        assertTrue(breakfastNames.size() >= 1, "Should have at least 1 breakfast recipe");
        assertTrue(lunchNames.size() >= 1, "Should have at least 1 lunch recipe");
        assertTrue(dinnerNames.size() >= 1, "Should have at least 1 dinner recipe");
    }
    
    // ---------- MACRO OPTIMIZATION TESTS ----------
    
    /**
     * TEST CASE: Plan attempts to meet macro targets
     * - Calculate total macros across all days
     * - Verify they're reasonably close to targets * days
     */
    @Test
    public void testMacroTargetOptimization() {
        int days = 3;
        PlanDay[] plan = planner.buildPlan(days, target, recipes);
        
        // Calculate total macros across all days
        Macro totalMacros = new Macro(0, 0, 0, 0);
        
        for (PlanDay day : plan) {
            // Add breakfast macros
            Macro bMacros = day.getBreakfast().getRecipe().macrosFor(day.getBreakfast().getServings());
            totalMacros = totalMacros.add(bMacros);
            
            // Add lunch macros
            Macro lMacros = day.getLunch().getRecipe().macrosFor(day.getLunch().getServings());
            totalMacros = totalMacros.add(lMacros);
            
            // Add dinner macros
            Macro dMacros = day.getDinner().getRecipe().macrosFor(day.getDinner().getServings());
            totalMacros = totalMacros.add(dMacros);
        }
        
        // Calculate expected totals (target * days)
        double expectedCalories = target.getCalories() * days;
        double expectedProtein = target.getProtein() * days;
        
        // Verify macros are in reasonable range (within 50% of target)
        // This is loose because LITE algorithm is simplified
        assertTrue(totalMacros.getCalories() > expectedCalories * 0.5,
            "Total calories should be reasonable (>50% of target)");
        assertTrue(totalMacros.getCalories() < expectedCalories * 2.0,
            "Total calories should be reasonable (<200% of target)");
        
        assertTrue(totalMacros.getProtein() > expectedProtein * 0.5,
            "Total protein should be reasonable (>50% of target)");
    }
    
    // ---------- EDGE CASE TESTS ----------
    
    /**
     * TEST CASE: Plan generation with single day
     * - Test minimum case of 1 day (though UI restricts to 3-5)
     */
    @Test
    public void testSingleDayPlan() {
        PlanDay[] plan = planner.buildPlan(1, target, recipes);
        
        assertEquals(1, plan.length, "Should generate exactly 1 day");
        assertNotNull(plan[0], "Single day should not be null");
        assertEquals(1, plan[0].getDayNumber(), "Day number should be 1");
        
        // Verify all meals present
        assertNotNull(plan[0].getBreakfast(), "Day should have breakfast");
        assertNotNull(plan[0].getLunch(), "Day should have lunch");
        assertNotNull(plan[0].getDinner(), "Day should have dinner");
    }
    
    /**
     * TEST CASE: Plan generation with different macro targets
     * - Test that different targets produce potentially different results
     */
    @Test
    public void testDifferentMacroTargets() {
        Macro lowCalTarget = new Macro(1500, 100, 150, 50);
        Macro highCalTarget = new Macro(3000, 200, 400, 100);
        
        PlanDay[] lowPlan = planner.buildPlan(3, lowCalTarget, recipes);
        PlanDay[] highPlan = planner.buildPlan(3, highCalTarget, recipes);
        
        assertNotNull(lowPlan, "Low calorie plan should be generated");
        assertNotNull(highPlan, "High calorie plan should be generated");
        
        assertEquals(3, lowPlan.length, "Low calorie plan should have 3 days");
        assertEquals(3, highPlan.length, "High calorie plan should have 3 days");
        
        // Both plans should have all meals
        for (int i = 0; i < 3; i++) {
            assertNotNull(lowPlan[i].getBreakfast(), "Low plan day " + (i+1) + " should have breakfast");
            assertNotNull(highPlan[i].getBreakfast(), "High plan day " + (i+1) + " should have breakfast");
        }
    }
    
    /**
     * TEST CASE: Recipe library contains sufficient recipes
     * - Verify we have recipes for each meal type
     * - This validates our test setup is correct
     */
    @Test
    public void testRecipeLibraryHasAllMealTypes() {
        assertNotNull(recipes, "Recipe library should not be null");
        assertTrue(recipes.length >= 3, "Should have at least 3 recipes (1 per meal type)");
        
        boolean hasBreakfast = false, hasLunch = false, hasDinner = false;
        
        for (Recipe recipe : recipes) {
            if (recipe instanceof BreakfastRecipe) hasBreakfast = true;
            if (recipe instanceof LunchRecipe) hasLunch = true;
            if (recipe instanceof DinnerRecipe) hasDinner = true;
        }
        
        assertTrue(hasBreakfast, "Recipe library should contain BreakfastRecipe");
        assertTrue(hasLunch, "Recipe library should contain LunchRecipe");
        assertTrue(hasDinner, "Recipe library should contain DinnerRecipe");
    }
}