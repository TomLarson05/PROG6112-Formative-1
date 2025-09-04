package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.Macro;

/**
 * Macro-Nutrient Calculator Service
 * 
 * Calculates personalized macronutrient targets using scientific formulas.
 * Implements the Mifflin-St Jeor equation for BMR calculation and
 * standard TDEE multipliers for activity levels. Generates macro
 * distributions based on fitness goals (maintain, cut, bulk).
 */
public class MacroCalculator {
    
    // ===== CONSTANTS =====
    
    // Activity level multipliers for TDEE calculation
    // Based on Harris-Benedict revised equation standards
    public static final double SEDENTARY = 1.2;          // Little to no exercise
    public static final double LIGHTLY_ACTIVE = 1.375;   // 1-3 days/week
    public static final double MODERATELY_ACTIVE = 1.55; // 3-5 days/week
    public static final double VERY_ACTIVE = 1.725;      // 6-7 days/week
    public static final double EXTRA_ACTIVE = 1.9;       // Physical job + exercise
    
    /* Harris and Benedict 1919 (revised in later research)
    "A Biometric Study of Basal Metabolism in Man"
    Proceedings of the National Academy of Sciences, Volume 4, Pages 370–373
    https://doi.org/10.1073/pnas.4.12.370
    Accessed 27 August 2025
    */
    
    // Goal multipliers for calorie adjustment
    public static final double MAINTAIN = 1.0;   // Maintenance calories
    public static final double CUT = 0.85;       // 15% deficit for fat loss
    public static final double BULK = 1.1;       // 10% surplus for muscle gain
    
    // Calories per gram of macronutrient (Atwater factors)
    private static final double CALORIES_PER_GRAM_PROTEIN = 4.0;
    private static final double CALORIES_PER_GRAM_CARB = 4.0;
    private static final double CALORIES_PER_GRAM_FAT = 9.0;
    
    /* Atwater and Woods 1896
    "The Chemical Composition of American Food Materials"
    U.S. Department of Agriculture, Bulletin No. 28
    https://naldc.nal.usda.gov/catalog/ORNL/2331207
    Accessed 28 August 2025
    */
    
    /**
     * Calculate Basal Metabolic Rate using Mifflin-St Jeor equation.
     * More accurate than Harris-Benedict for modern populations.
     * 
     * Formula:
     * Men: BMR = 10 × weight(kg) + 6.25 × height(cm) - 5 × age(y) + 5
     * Women: BMR = 10 × weight(kg) + 6.25 × height(cm) - 5 × age(y) - 161
     * 
     * @param age Age in years
     * @param weightKg Weight in kilograms
     * @param heightCm Height in centimeters
     * @param isMale True if male, false if female
     * @return BMR in calories per day
     */
    public static double calculateBMR(int age, double weightKg, double heightCm, boolean isMale) {
        // Mifflin-St Jeor Equation implementation
        double bmr = (10 * weightKg) + (6.25 * heightCm) - (5 * age);
        if (isMale) {
            bmr += 5;     // Male adjustment factor
        } else {
            bmr -= 161;   // Female adjustment factor
        }
        return bmr;
        /* Mifflin et al. 1990
           "A new predictive equation for resting energy expenditure in healthy individuals"
           The American Journal of Clinical Nutrition, Volume 51, Issue 2, Pages 241-247
           https://doi.org/10.1093/ajcn/51.2.241
           Accessed 26 August 2025
        */
    }
    
    /**
     * Calculate Total Daily Energy Expenditure
     * @param bmr Basal Metabolic Rate
     * @param activityMultiplier Activity level multiplier (1.2 to 1.9)
     * @return TDEE in calories
     */
    public static double calculateTDEE(double bmr, double activityMultiplier) {
        return bmr * activityMultiplier;
    }
    
    /**
     * Generate macro targets based on TDEE and fitness goal.
     * Uses evidence-based macro distributions for different goals.
     * 
     * Distributions:
     * - Maintain: 30% protein, 40% carbs, 30% fat
     * - Cut: 35% protein, 35% carbs, 30% fat (higher protein for muscle preservation)
     * - Bulk: 25% protein, 45% carbs, 30% fat (higher carbs for energy)
     * 
     * @param tdee Total Daily Energy Expenditure in calories
     * @param goal 1=Maintain, 2=Cut, 3=Bulk
     * @return Macro object with calculated targets
     */
    public static Macro generateMacros(double tdee, int goal) {
        /* Helms et al. 2014
           "Evidence-based recommendations for natural bodybuilding contest preparation"
           Journal of the International Society of Sports Nutrition, 11:20
           https://doi.org/10.1186/1550-2783-11-20
           Accessed 1 September 2025
        */
        double targetCalories;
        double proteinPercent, carbPercent, fatPercent;
        
        switch (goal) {
            case 2: // Cut - Higher protein, moderate carbs
                targetCalories = tdee * CUT;
                proteinPercent = 0.35;
                carbPercent = 0.35;
                fatPercent = 0.30;
                break;
            case 3: // Bulk - Moderate protein, higher carbs
                targetCalories = tdee * BULK;
                proteinPercent = 0.25;
                carbPercent = 0.45;
                fatPercent = 0.30;
                break;
            default: // Maintain - Balanced macros
                targetCalories = tdee * MAINTAIN;
                proteinPercent = 0.30;
                carbPercent = 0.40;
                fatPercent = 0.30;
                break;
        }
        
        // Calculate grams from percentages
        double proteinGrams = (targetCalories * proteinPercent) / CALORIES_PER_GRAM_PROTEIN;
        double carbGrams = (targetCalories * carbPercent) / CALORIES_PER_GRAM_CARB;
        double fatGrams = (targetCalories * fatPercent) / CALORIES_PER_GRAM_FAT;
        
        return new Macro(
            Math.round(targetCalories),
            Math.round(proteinGrams),
            Math.round(carbGrams),
            Math.round(fatGrams)
        );
    }
    
    /**
     * Get activity multiplier based on activity level choice
     * @param level Activity level (1-5)
     * @return Activity multiplier
     */
    public static double getActivityMultiplier(int level) {
        switch (level) {
            case 1: return SEDENTARY;
            case 2: return LIGHTLY_ACTIVE;
            case 3: return MODERATELY_ACTIVE;
            case 4: return VERY_ACTIVE;
            case 5: return EXTRA_ACTIVE;
            default: return SEDENTARY;
        }
    }
    
    /**
     * Get description for activity level
     * @param level Activity level (1-5)
     * @return Description string
     */
    public static String getActivityDescription(int level) {
        switch (level) {
            case 1: return "Sedentary (little to no exercise)";
            case 2: return "Lightly active (1-3 days/week)";
            case 3: return "Moderately active (3-5 days/week)";
            case 4: return "Very active (6-7 days/week)";
            case 5: return "Extra active (physical job + exercise)";
            default: return "Unknown";
        }
    }
    
    /**
     * Get goal description
     * @param goal Goal type (1-3)
     * @return Goal description
     */
    public static String getGoalDescription(int goal) {
        switch (goal) {
            case 1: return "Maintain weight";
            case 2: return "Lose weight (15% deficit)";
            case 3: return "Gain muscle (10% surplus)";
            default: return "Unknown";
        }
    }
}