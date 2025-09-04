package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.*;

/**
 * Meal Planning Service - Core Algorithm Implementation
 * 
 * Implements a greedy optimization algorithm to generate meal plans that
 * match user's macro-nutrient targets. Features include:
 * - Meal-specific target distribution (25% breakfast, 40% lunch, 35% dinner)
 * - Serving size optimization (1.0x to 3.0x in 0.5 increments)
 * - Weighted macro distance minimization
 * - Recipe variety through repetition penalties
 */
public class Planner {

    // ===== CONSTANTS =====
    
    // Serving size constraints
    private static final double MIN_SERV = 1.0;   // Minimum serving multiplier
    private static final double MAX_SERV = 3.0;   // Maximum serving multiplier
    private static final double SERV_STEP = 0.5;  // Increment step for serving sizes

    // Algorithm tuning parameters
    private static final double REPEAT_PENALTY = 300.0;  // Penalty for consecutive day repetition

    // Macro-nutrient weights for scoring function
    // Higher weights = more importance in optimization
    private static final double W_KCAL = 1.0;   // Calorie weight (highest priority)
    private static final double W_PROT = 0.9;   // Protein weight
    private static final double W_CARB = 0.4;   // Carbohydrate weight
    private static final double W_FAT  = 0.3;   // Fat weight (lowest priority)

    // Meal target distribution percentages
    // Based on typical meal distribution patterns
    private static final double BREAKFAST_PERCENT = 0.25; // 25% of daily target
    private static final double LUNCH_PERCENT = 0.40;     // 40% of daily target  
    private static final double DINNER_PERCENT = 0.35;    // 35% of daily target

    /**
     * Builds a complete meal plan for the specified number of days.
     * Uses a greedy algorithm to select recipes and serving sizes that
     * minimize the weighted distance to macro targets.
     * 
     * @param days Number of days to plan (typically 3-5)
     * @param target Daily macro-nutrient targets
     * @param library Available recipes to choose from
     * @return Array of PlanDay objects containing the meal plan
     */
    public PlanDay[] buildPlan(int days, Macro target, Recipe[] library) {
        PlanDay[] plan = new PlanDay[days];
        /* Oracle 2025
           Arrays (Java Platform SE 8)
           Oracle Documentation
           https://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html
           Accessed 25 August 2025
        */

        for (int d = 0; d < days; d++) {
            Macro soFar = new Macro(0, 0, 0, 0);

            // Breakfast
            Recipe prevB = (d > 0) ? plan[d - 1].getBreakfast().getRecipe() : null;
            Macro breakfastTarget = getMealTarget(target, "breakfast");
            RecipeSelection bSel = pickBest(new Macro(0, 0, 0, 0), breakfastTarget, library, BreakfastRecipe.class, prevB);
            soFar = addScaled(soFar, bSel.getRecipe(), bSel.getServings());

            // Lunch
            Recipe prevL = (d > 0) ? plan[d - 1].getLunch().getRecipe() : null;
            Macro lunchTarget = getMealTarget(target, "lunch");
            RecipeSelection lSel = pickBest(new Macro(0, 0, 0, 0), lunchTarget, library, LunchRecipe.class, prevL);
            soFar = addScaled(soFar, lSel.getRecipe(), lSel.getServings());

            // Dinner
            Recipe prevD = (d > 0) ? plan[d - 1].getDinner().getRecipe() : null;
            Macro dinnerTarget = getMealTarget(target, "dinner");
            RecipeSelection eSel = pickBest(new Macro(0, 0, 0, 0), dinnerTarget, library, DinnerRecipe.class, prevD);

            plan[d] = new PlanDay(d + 1, bSel, lSel, eSel);
        }

        return plan;
    }

    /**
     * Selects the best recipe and serving size for a meal slot.
     * Implements greedy optimization by evaluating all combinations
     * and choosing the one with minimum weighted distance to target.
     * 
     * @param current Current accumulated macros (not used in new version)
     * @param target Target macros for this specific meal
     * @param lib Recipe library to choose from
     * @param type Recipe type class (BreakfastRecipe, LunchRecipe, or DinnerRecipe)
     * @param prevSameSlot Previous day's recipe for this slot (for variety)
     * @return RecipeSelection with chosen recipe and serving size
     */
    private RecipeSelection pickBest(Macro current, Macro target, Recipe[] lib, Class<?> type, Recipe prevSameSlot) {
        Recipe best = null;
        double bestServ = 1.0;
        double bestScore = Double.POSITIVE_INFINITY;
        /* Cormen et al. 2009
        "Introduction to Algorithms" - Chapter 16: Greedy Algorithms
        MIT Press, 3rd Edition
        ISBN: 978-0262033848
        https://mitpress.mit.edu/9780262033848/introduction-to-algorithms/
        Accessed 21 August 2025
        */

        for (Recipe r : lib) {
            if (!type.isInstance(r)) continue;

            for (double s = MIN_SERV; s <= MAX_SERV + 1e-9; s += SERV_STEP) {
                Macro future = addScaled(current, r, s);

                double score =
                        W_KCAL * Math.abs(future.getCalories() - target.getCalories()) +
                        W_PROT * Math.abs(future.getProtein()  - target.getProtein())  +
                        W_CARB * Math.abs(future.getCarbs()    - target.getCarbs())    +
                        W_FAT  * Math.abs(future.getFat()      - target.getFat());

                // discourage repeating yesterday's same-slot recipe
                if (prevSameSlot != null && prevSameSlot.getName().equalsIgnoreCase(r.getName())) {
                    score += REPEAT_PENALTY;
                }

                if (score < bestScore) {
                    bestScore = score;
                    best = r;
                    bestServ = roundHalf(s); // keep a tidy one-decimal value
                }
            }
        }

        return new RecipeSelection(best, bestServ);
    }

    private Macro addScaled(Macro base, Recipe r, double s) {
        return new Macro(
                base.getCalories() + r.getCalories() * s,
                base.getProtein()  + r.getProtein()  * s,
                base.getCarbs()    + r.getCarbs()    * s,
                base.getFat()      + r.getFat()      * s
        );
    }

    // Get meal-specific target based on daily target and meal type
    private Macro getMealTarget(Macro dailyTarget, String mealType) {
        double percent;
        switch (mealType.toLowerCase()) {
            case "breakfast":
                percent = BREAKFAST_PERCENT;
                break;
            case "lunch":
                percent = LUNCH_PERCENT;
                break;
            case "dinner":
                percent = DINNER_PERCENT;
                break;
            default:
                percent = 0.33; // fallback to 1/3
        }
        
        return new Macro(
            dailyTarget.getCalories() * percent,
            dailyTarget.getProtein() * percent,
            dailyTarget.getCarbs() * percent,
            dailyTarget.getFat() * percent
        );
    }

    // Utility to avoid floating-point noise (e.g., 1.4999999)
    private double roundHalf(double v) {
        return Math.round(v * 2.0) / 2.0;
    }
    
    /* Stack Overflow 2011
Round a double to the nearest 0.5 in Java
Stack Overflow
https://stackoverflow.com/questions/3571385/rounding-to-nearest-0-5-in-java
Author: Bohemian♦
        https://stackoverflow.com/users/256196/bohemian
Accessed 22 August 2025
*/
}
