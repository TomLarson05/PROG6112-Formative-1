package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.model.Macro;
import com.mycompany.smartmealplanner.service.MacroCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Class for MacroCalculator Service
 * 
 * Comprehensive test suite validating BMR calculations, TDEE calculations,
 * and macro-nutrient target generation. Tests use real-world scenarios
 * and scientific formulas to ensure accuracy.
 */
public class MacroCalculatorTest {
    
    /**
     * Default constructor for test class.
     * Required by JUnit framework.
     */
    public MacroCalculatorTest() {
    }
    
    // ===== TEST CONSTANTS =====
    private static final double DELTA = 0.01; // Tolerance for floating-point comparisons
    /* JUnit Team 2025
       JUnit 5 User Guide - Assertions
       JUnit
       https://junit.org/junit5/docs/current/user-guide/#writing-tests-assertions
       Accessed 30 August 2025
    */
    
    // ---------- BMR CALCULATION TESTS ----------
    
    /**
     * TEST CASE: BMR calculation for a male
     * 
     * Validates the Mifflin-St Jeor equation implementation for male subjects.
     * Uses realistic test data to ensure accurate metabolic rate calculations.
     * 
     * Test scenario:
     * - 25-year-old male, 80kg, 175cm
     * - Expected BMR: (10 * 80) + (6.25 * 175) - (5 * 25) + 5 = 1773.75
     * 
     * @see MacroCalculator#calculateBMR(int, double, double, boolean)
     */
    @Test
    public void testBMRCalculationMale() {
        // ===== TEST DATA SETUP =====
        int age = 25;           // Years
        double weight = 80.0;   // Kilograms
        double height = 175.0;  // Centimeters
        boolean isMale = true;  // Gender flag
        
        // ===== EXECUTION =====
        double actual = MacroCalculator.calculateBMR(age, weight, height, isMale);
        double expected = 1773.75;  // Calculated using Mifflin-St Jeor formula
        
        // ===== VERIFICATION =====
        assertEquals(expected, actual, DELTA, "BMR calculation for male should be accurate");
    }
    
    /**
     * TEST CASE: BMR calculation for a female
     * - Using Mifflin-St Jeor equation for females  
     * - Test data: 25-year-old female, 65kg, 165cm
     * - Expected BMR: (10 * 65) + (6.25 * 165) - (5 * 25) - 161 = 1315.25
     */
    @Test
    public void testBMRCalculationFemale() {
        int age = 25;
        double weight = 65.0;
        double height = 165.0;
        boolean isMale = false;
        
        double actual = MacroCalculator.calculateBMR(age, weight, height, isMale);
        double expected = 1395.25;
        
        assertEquals(expected, actual, DELTA, "BMR calculation for female should be accurate");
    }
    
    // ---------- TDEE CALCULATION TESTS ----------
    
    /**
     * TEST CASE: TDEE calculation with sedentary activity level
     * - BMR * 1.2 for sedentary lifestyle
     * - Test with BMR of 1500
     */
    @Test
    public void testTDEECalculationSedentary() {
        double bmr = 1500.0;
        double activityMultiplier = MacroCalculator.SEDENTARY; // 1.2
        
        double actual = MacroCalculator.calculateTDEE(bmr, activityMultiplier);
        double expected = 1800.0; // 1500 * 1.2
        
        assertEquals(expected, actual, DELTA, "TDEE calculation for sedentary should be BMR * 1.2");
    }
    
    /**
     * TEST CASE: TDEE calculation with very active level
     * - BMR * 1.725 for very active lifestyle
     * - Test with BMR of 1500
     */
    @Test
    public void testTDEECalculationVeryActive() {
        double bmr = 1500.0;
        double activityMultiplier = MacroCalculator.VERY_ACTIVE; // 1.725
        
        double actual = MacroCalculator.calculateTDEE(bmr, activityMultiplier);
        double expected = 2587.5; // 1500 * 1.725
        
        assertEquals(expected, actual, DELTA, "TDEE calculation for very active should be BMR * 1.725");
    }
    
    // ---------- ACTIVITY MULTIPLIER TESTS ----------
    
    /**
     * TEST CASE: All activity level multipliers are correct
     * - Tests that getActivityMultiplier returns correct values for levels 1-5
     */
    @Test
    public void testActivityMultipliers() {
        assertEquals(MacroCalculator.SEDENTARY, MacroCalculator.getActivityMultiplier(1), DELTA, "Level 1 should be sedentary (1.2)");
        assertEquals(MacroCalculator.LIGHTLY_ACTIVE, MacroCalculator.getActivityMultiplier(2), DELTA, "Level 2 should be lightly active (1.375)");
        assertEquals(MacroCalculator.MODERATELY_ACTIVE, MacroCalculator.getActivityMultiplier(3), DELTA, "Level 3 should be moderately active (1.55)");
        assertEquals(MacroCalculator.VERY_ACTIVE, MacroCalculator.getActivityMultiplier(4), DELTA, "Level 4 should be very active (1.725)");
        assertEquals(MacroCalculator.EXTRA_ACTIVE, MacroCalculator.getActivityMultiplier(5), DELTA, "Level 5 should be extra active (1.9)");
    }
    
    /**
     * TEST CASE: Invalid activity level defaults to sedentary
     * - Test that invalid activity levels (0, 6, etc.) default to sedentary
     */
    @Test
    public void testInvalidActivityLevel() {
        assertEquals(MacroCalculator.SEDENTARY, MacroCalculator.getActivityMultiplier(0), DELTA, "Invalid level should default to sedentary");
        assertEquals(MacroCalculator.SEDENTARY, MacroCalculator.getActivityMultiplier(6), DELTA, "Invalid level should default to sedentary");
        assertEquals(MacroCalculator.SEDENTARY, MacroCalculator.getActivityMultiplier(-1), DELTA, "Invalid level should default to sedentary");
    }
    
    // ---------- MACRO GENERATION TESTS ----------
    
    /**
     * TEST CASE: Macro generation for maintenance goal
     * - Goal 1 (Maintain): 100% TDEE, 30% protein, 40% carbs, 30% fat
     * - Test TDEE of 2000 calories
     */
    @Test
    public void testMacroGenerationMaintenance() {
        double tdee = 2000.0;
        int goal = 1; // Maintain
        
        Macro result = MacroCalculator.generateMacros(tdee, goal);
        
        // Expected: 2000 kcal, 150g protein (30%), 200g carbs (40%), 67g fat (30%)
        assertEquals(2000, result.getCalories(), DELTA, "Maintenance calories should be 100% TDEE");
        assertEquals(150, result.getProtein(), DELTA, "Maintenance protein should be 30% of calories / 4");
        assertEquals(200, result.getCarbs(), DELTA, "Maintenance carbs should be 40% of calories / 4");
        assertEquals(67, result.getFat(), DELTA, "Maintenance fat should be 30% of calories / 9");
    }
    
    /**
     * TEST CASE: Macro generation for cutting goal  
     * - Goal 2 (Cut): 85% TDEE, 35% protein, 35% carbs, 30% fat
     * - Test TDEE of 2000 calories
     */
    @Test
    public void testMacroGenerationCutting() {
        double tdee = 2000.0;
        int goal = 2; // Cut
        
        Macro result = MacroCalculator.generateMacros(tdee, goal);
        
        // Expected: 1700 kcal (85%), 149g protein (35%), 149g carbs (35%), 57g fat (30%)
        assertEquals(1700, result.getCalories(), DELTA, "Cutting calories should be 85% TDEE");
        assertEquals(149, result.getProtein(), DELTA, "Cutting protein should be 35% of calories / 4");
        assertEquals(149, result.getCarbs(), DELTA, "Cutting carbs should be 35% of calories / 4");
        assertEquals(57, result.getFat(), DELTA, "Cutting fat should be 30% of calories / 9");
    }
    
    /**
     * TEST CASE: Macro generation for bulking goal
     * - Goal 3 (Bulk): 110% TDEE, 25% protein, 45% carbs, 30% fat
     * - Test TDEE of 2000 calories
     */
    @Test
    public void testMacroGenerationBulking() {
        double tdee = 2000.0;
        int goal = 3; // Bulk
        
        Macro result = MacroCalculator.generateMacros(tdee, goal);
        
        // Expected: 2200 kcal (110%), 138g protein (25%), 248g carbs (45%), 73g fat (30%)
        assertEquals(2200, result.getCalories(), DELTA, "Bulking calories should be 110% TDEE");
        assertEquals(138, result.getProtein(), DELTA, "Bulking protein should be 25% of calories / 4");
        assertEquals(248, result.getCarbs(), DELTA, "Bulking carbs should be 45% of calories / 4");
        assertEquals(73, result.getFat(), DELTA, "Bulking fat should be 30% of calories / 9");
    }
    
    /**
     * TEST CASE: Invalid goal defaults to maintenance
     * - Test that invalid goal numbers default to maintenance macros
     */
    @Test
    public void testInvalidGoalDefaultsToMaintenance() {
        double tdee = 2000.0;
        int invalidGoal = 0;
        
        Macro result = MacroCalculator.generateMacros(tdee, invalidGoal);
        
        // Should default to maintenance (same as goal 1)
        assertEquals(2000, result.getCalories(), DELTA, "Invalid goal should default to maintenance calories");
        assertEquals(150, result.getProtein(), DELTA, "Invalid goal should default to maintenance protein");
        assertEquals(200, result.getCarbs(), DELTA, "Invalid goal should default to maintenance carbs");
        assertEquals(67, result.getFat(), DELTA, "Invalid goal should default to maintenance fat");
    }
    
    // ---------- HELPER METHOD TESTS ----------
    
    /**
     * TEST CASE: Activity level descriptions are not null
     * - Ensures all activity descriptions return valid strings
     */
    @Test
    public void testActivityDescriptions() {
        for (int i = 1; i <= 5; i++) {
            String description = MacroCalculator.getActivityDescription(i);
            assertNotNull(description, "Activity description for level " + i + " should not be null");
            assertFalse(description.isEmpty(), "Activity description for level " + i + " should not be empty");
        }
    }
    
    /**
     * TEST CASE: Goal descriptions are not null
     * - Ensures all goal descriptions return valid strings
     */
    @Test
    public void testGoalDescriptions() {
        for (int i = 1; i <= 3; i++) {
            String description = MacroCalculator.getGoalDescription(i);
            assertNotNull(description, "Goal description for goal " + i + " should not be null");
            assertFalse(description.isEmpty(), "Goal description for goal " + i + " should not be empty");
        }
    }
    
    /**
     * TEST CASE: Edge case - very young person
     * - Test BMR calculation with minimum realistic age
     */
    @Test
    public void testBMRYoungPerson() {
        int age = 18;
        double weight = 60.0;
        double height = 170.0;
        boolean isMale = true;
        
        double actual = MacroCalculator.calculateBMR(age, weight, height, isMale);
        
        // Should produce a reasonable BMR (positive number)
        assertTrue(actual > 1000, "BMR for young person should be reasonable (>1000)");
        assertTrue(actual < 3000, "BMR for young person should be reasonable (<3000)");
    }
    
    /**
     * TEST CASE: Edge case - older person
     * - Test BMR calculation with higher age
     */
    @Test
    public void testBMROlderPerson() {
        int age = 70;
        double weight = 70.0;
        double height = 165.0;
        boolean isMale = false;
        
        double actual = MacroCalculator.calculateBMR(age, weight, height, isMale);
        
        // Should produce a reasonable BMR (positive number, lower due to age)
        assertTrue(actual > 800, "BMR for older person should be reasonable (>800)");
        assertTrue(actual < 2000, "BMR for older person should be reasonable (<2000)");
    }
}