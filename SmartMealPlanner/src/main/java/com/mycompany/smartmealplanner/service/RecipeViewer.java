package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.*;
import com.mycompany.smartmealplanner.service.IngredientCategorizer;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Recipe Viewer Service
 * 
 * Provides formatted display functionality for recipes and meal plans.
 * Handles scaling of ingredients and nutritional information for
 * different serving sizes with user-friendly formatting.
 */
public class RecipeViewer {
    
    /**
     * Display a complete recipe card with formatted instructions.
     * Shows scaled ingredients, cooking instructions, timing, and nutrition.
     * 
     * @param recipe The recipe to display
     * @param servings The number of servings to scale ingredients to
     */
    public static void displayRecipeCard(Recipe recipe, double servings) {
        // Create formatted header
        System.out.println("\n" + "=".repeat(60));
        System.out.println("RECIPE: " + recipe.getName().toUpperCase());
        System.out.println("=".repeat(60));
        /* Oracle 2025
           String.repeat() Method
           Oracle Documentation
           https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#repeat(int)
           Accessed 31 August 2025
        */
        
        // Display timing and difficulty
        System.out.printf("Prep Time: %d min | Cook Time: %d min | Total: %d min%n",
            recipe.getPrepTime(), recipe.getCookTime(), recipe.getTotalTime());
        System.out.println("Difficulty: " + recipe.getDifficulty());
        System.out.println("Servings: " + servings);
        
        System.out.println("\n--- INGREDIENTS ---");
        displayScaledIngredients(recipe, servings);
        
        System.out.println("\n--- INSTRUCTIONS ---");
        System.out.print(recipe.getFormattedInstructions());
        
        System.out.println("\n--- NUTRITION (per serving) ---");
        displayNutrition(recipe.macrosFor(servings));
        
        System.out.println("\n" + "=".repeat(60));
    }
    
    /**
     * Display scaled ingredients list with categorization
     * @param recipe The recipe
     * @param servings Number of servings to scale to
     */
    private static void displayScaledIngredients(Recipe recipe, double servings) {
        Ingredient[] scaledIngredients = recipe.ingredientsFor(servings);
        
        if (scaledIngredients.length == 0) {
            System.out.println("(No ingredients)");
            return;
        }
        
        // Group ingredients by category
        Map<IngredientCategory, List<Ingredient>> categorizedIngredients = 
            Arrays.stream(scaledIngredients)
                .collect(Collectors.groupingBy(ingredient -> 
                    IngredientCategorizer.categorizeIngredient(ingredient.getName())));
        
        // Display ingredients in store layout order
        for (IngredientCategory category : IngredientCategory.getShoppingOrder()) {
            if (categorizedIngredients.containsKey(category) && !categorizedIngredients.get(category).isEmpty()) {
                System.out.println("\n" + category.getDisplayName());
                System.out.println("─".repeat(30));
                
                for (Ingredient ingredient : categorizedIngredients.get(category)) {
                    // Format quantity nicely (remove .0 for whole numbers)
                    String quantityStr;
                    double qty = ingredient.getQuantityPerServing();
                    if (qty == Math.floor(qty)) {
                        quantityStr = String.format("%.0f", qty);
                    } else {
                        quantityStr = String.format("%.1f", qty);
                    }
                    
                    System.out.printf("• %s %s %s%n", 
                        quantityStr,
                        ingredient.getUnit(),
                        ingredient.getName()
                    );
                }
            }
        }
    }
    
    /**
     * Display nutrition information
     * @param macros The macro nutrients to display
     */
    private static void displayNutrition(Macro macros) {
        System.out.printf("Calories: %.0f kcal%n", macros.getCalories());
        System.out.printf("Protein: %.1f g%n", macros.getProtein());
        System.out.printf("Carbohydrates: %.1f g%n", macros.getCarbs());
        System.out.printf("Fat: %.1f g%n", macros.getFat());
    }
    
    /**
     * View a recipe from the current meal plan
     * @param plan The meal plan
     * @param scanner Scanner for user input
     */
    public static void viewRecipeFromPlan(PlanDay[] plan, Scanner scanner) {
        if (plan == null || plan.length == 0) {
            System.out.println("No meal plan available. Please generate a plan first.");
            return;
        }
        
        // Display available meals
        System.out.println("\n=== SELECT RECIPE TO VIEW ===");
        int index = 1;
        
        for (PlanDay day : plan) {
            System.out.println("\nDay " + day.getDayNumber() + ":");
            
            if (day.getBreakfast() != null) {
                System.out.printf("  %d. Breakfast: %s%n", index++, day.getBreakfast());
            }
            if (day.getLunch() != null) {
                System.out.printf("  %d. Lunch: %s%n", index++, day.getLunch());
            }
            if (day.getDinner() != null) {
                System.out.printf("  %d. Dinner: %s%n", index++, day.getDinner());
            }
        }
        
        // Get user selection
        int choice = InputHelper.readIntInRange(scanner, 
            "\nSelect recipe number (0 to cancel): ", 0, index - 1);
        
        /* Stack Overflow 2010
        Loop until valid integer input using Scanner in Java
        Stack Overflow
        https://stackoverflow.com/questions/3059333/validating-input-using-java-util-scanner
        Author: dimo414
                https://stackoverflow.com/users/1048572/dimo414
        Accessed 4 September 2025
        */
        
        if (choice == 0) {
            return;
        }
        
        // Find and display the selected recipe
        index = 1;
        for (PlanDay day : plan) {
            RecipeSelection[] meals = {day.getBreakfast(), day.getLunch(), day.getDinner()};
            
            for (RecipeSelection meal : meals) {
                if (meal != null) {
                    if (index == choice) {
                        displayRecipeCard(meal.getRecipe(), meal.getServings());
                        return;
                    }
                    index++;
                }
            }
        }
    }
    
    /**
     * Browse all available recipes with full details
     * @param recipes Array of all recipes
     * @param scanner Scanner for user input
     */
    public static void browseAllRecipes(Recipe[] recipes, Scanner scanner) {
        boolean browsing = true;
        
        while (browsing) {
            System.out.println("\n=== RECIPE BROWSER ===");
            
            // Display recipes grouped by meal type
            System.out.println("\nBREAKFAST:");
            int index = 1;
            for (Recipe recipe : recipes) {
                if (recipe.getMealType().equals("Breakfast")) {
                    System.out.printf("  %d. %s (%s, %d min)%n", 
                        index++, recipe.getName(), recipe.getDifficulty(), 
                        recipe.getTotalTime());
                }
            }
            
            System.out.println("\nLUNCH:");
            for (Recipe recipe : recipes) {
                if (recipe.getMealType().equals("Lunch")) {
                    System.out.printf("  %d. %s (%s, %d min)%n", 
                        index++, recipe.getName(), recipe.getDifficulty(), 
                        recipe.getTotalTime());
                }
            }
            
            System.out.println("\nDINNER:");
            for (Recipe recipe : recipes) {
                if (recipe.getMealType().equals("Dinner")) {
                    System.out.printf("  %d. %s (%s, %d min)%n", 
                        index++, recipe.getName(), recipe.getDifficulty(), 
                        recipe.getTotalTime());
                }
            }
            
            // Get user selection
            int choice = InputHelper.readIntInRange(scanner, 
                "\nSelect recipe to view (0 to exit): ", 0, recipes.length);
            
            if (choice == 0) {
                browsing = false;
            } else {
                // Display the selected recipe
                Recipe selectedRecipe = recipes[choice - 1];
                displayRecipeCard(selectedRecipe, 1.0);
                
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Display a quick recipe summary without full instructions
     * @param recipe The recipe to summarize
     */
    public static void displayRecipeSummary(Recipe recipe) {
        System.out.printf("%s - %s - %d min total%n", 
            recipe.getName(), recipe.getDifficulty(), recipe.getTotalTime());
        System.out.printf("  Nutrition: %s%n", recipe.macrosFor(1.0));
    }
    
    /**
     * View all recipes for a specific day
     * @param day The plan day to view recipes for
     * @param dayNumber The day number for display
     */
    public static void viewDayRecipes(PlanDay day, int dayNumber) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("DAY " + dayNumber + " RECIPES");
        System.out.println("=".repeat(60));
        
        // Display breakfast
        if (day.getBreakfast() != null) {
            System.out.println("\n>>> BREAKFAST <<<");
            displayRecipeCard(day.getBreakfast().getRecipe(), day.getBreakfast().getServings());
        }
        
        // Display lunch
        if (day.getLunch() != null) {
            System.out.println("\n>>> LUNCH <<<");
            displayRecipeCard(day.getLunch().getRecipe(), day.getLunch().getServings());
        }
        
        // Display dinner  
        if (day.getDinner() != null) {
            System.out.println("\n>>> DINNER <<<");
            displayRecipeCard(day.getDinner().getRecipe(), day.getDinner().getServings());
        }
        
        System.out.println("\n" + "=".repeat(60));
    }
}