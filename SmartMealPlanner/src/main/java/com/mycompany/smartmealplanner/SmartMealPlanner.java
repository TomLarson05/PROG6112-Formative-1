
package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.data.RecipeLibrary;
import com.mycompany.smartmealplanner.model.Macro;
import com.mycompany.smartmealplanner.model.PlanDay;
import com.mycompany.smartmealplanner.model.Recipe;
import com.mycompany.smartmealplanner.model.User;
import com.mycompany.smartmealplanner.model.GroceryItem;
import com.mycompany.smartmealplanner.service.InputHelper;
import com.mycompany.smartmealplanner.service.MacroCalculator;
import com.mycompany.smartmealplanner.service.Planner;
import com.mycompany.smartmealplanner.service.RecipeViewer;
import com.mycompany.smartmealplanner.service.ReportPrinter;
import com.mycompany.smartmealplanner.service.UserManager;

import java.util.Scanner;
import java.util.List;

/**
 * SmartMeal Application - Main Entry Point
 * 
 * A meal planning application that generates personalized meal plans based on
 * user's macro-nutrient targets. This LITE version provides essential features:
 * user authentication, macro calculation, meal plan generation, and grocery lists.
 */
public class SmartMealPlanner {

    /**
     * Main method - Application entry point
     * Manages the complete application flow including login, meal planning,
     * and user interaction through a menu-driven interface.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // ===== INITIALIZATION =====
        Scanner scanner = new Scanner(System.in);
        /* Oracle 2025
           Scanner (Java Platform SE 8)
           Oracle Documentation
           https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
           Accessed 23 August 2025
        */
        
        Planner planner = new Planner();
        ReportPrinter printer = new ReportPrinter();

        // ===== USER AUTHENTICATION =====
        // Handle login flow - returns authenticated user or null for guest
        User currentUser = UserManager.handleLoginFlow(scanner);
        
        // ===== USER PREFERENCES INITIALIZATION =====
        // Initialize with defaults or user's saved settings
        int days;        // Number of days for meal planning
        Macro target;    // Daily macro-nutrient targets
        PlanDay[] currentPlan;  // Array to store the meal plan
        
        if (currentUser != null) {
            // Authenticated user - load saved preferences from User object
            days = currentUser.getSavedDays();
            target = currentUser.getSavedTargets();
            currentPlan = currentUser.getLastPlan(); // Load previously saved meal plan
            
            System.out.println("\nLoaded your saved preferences:");
            System.out.println("Days: " + days);
            System.out.println("Targets: " + target);
            
            if (currentPlan != null) {
                System.out.println("Previous meal plan loaded!");
            }
        } else {
            // Guest user - use default values
            days = 3;
            target = new Macro(2200, 120, 250, 70);  // Default macro targets
            currentPlan = null;
        }

        // ===== MAIN APPLICATION LOOP =====
        boolean running = true;
        /* W3Schools 2025
           Java While Loop
           W3Schools
           https://www.w3schools.com/java/java_while_loop.asp
           Accessed 27 August 2025
        */
        
        while (running) {
            // Display main menu
            System.out.println("\n==================================================");
            System.out.println("                 SMART MEAL PLANNER");
            System.out.println("==================================================");
            if (currentUser != null) {
                System.out.println("Logged in as: " + currentUser.getUsername());
            } else {
                System.out.println("Mode: Guest");
            }
            System.out.println();
            System.out.println("[ 1 ]    🎯 Configure targets");
            System.out.println("[ 2 ]    📅 Set days (3–5)");
            System.out.println("[ 3 ]    🥗 Generate meal plan");
            System.out.println("[ 4 ]    📖 View meal plan");
            System.out.println("[ 5 ]    🛒 View grocery list");
            System.out.println("[ 6 ]    🚪 Exit");
            System.out.println();
            System.out.println("--------------------------------------------------");
            
            // Read user's menu choice with validation
            int choice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, 6);

            // ===== MENU OPTION HANDLING =====
            switch (choice) {
                case 1 -> {
                    // OPTION 1: Configure macro targets
                    System.out.println("\n==================================================");
                    System.out.println("                CONFIGURE TARGETS");
                    System.out.println("==================================================");
                    System.out.println();
                    System.out.println("[ 1 ]    Help me calculate my macros");
                    System.out.println("[ 2 ]    I know my macros (manual entry)");
                    System.out.println();
                    System.out.println("--------------------------------------------------");
                    int configChoice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, 2);
                    
                    if (configChoice == 1) {
                        // Automated macro calculation using user's physical stats
                        System.out.println("\n=== MACRO CALCULATOR ===");
                        
                        // Get personal stats
                        int age = InputHelper.readIntInRange(scanner, "Age (years): ", 18, 100);
                        double weight = InputHelper.readDoubleInRange(scanner, "Weight (kg): ", 30, 300);
                        double height = InputHelper.readDoubleInRange(scanner, "Height (cm): ", 100, 250);
                        char gender = InputHelper.readGender(scanner, "Gender (M/F): ");
                        boolean isMale = (gender == 'M');
                        
                        // Get activity level
                        System.out.println("\nActivity Level:");
                        for (int i = 1; i <= 5; i++) {
                            System.out.println(i + ") " + MacroCalculator.getActivityDescription(i));
                        }
                        int activityLevel = InputHelper.readIntInRange(scanner, "Select activity level: ", 1, 5);
                        
                        // Get goal
                        System.out.println("\nYour Goal:");
                        for (int i = 1; i <= 3; i++) {
                            System.out.println(i + ") " + MacroCalculator.getGoalDescription(i));
                        }
                        int goal = InputHelper.readIntInRange(scanner, "Select goal: ", 1, 3);
                        
                        // Calculate macros using Mifflin-St Jeor equation
                        double bmr = MacroCalculator.calculateBMR(age, weight, height, isMale);
                        double activityMultiplier = MacroCalculator.getActivityMultiplier(activityLevel);
                        double tdee = MacroCalculator.calculateTDEE(bmr, activityMultiplier);
                        Macro calculatedMacros = MacroCalculator.generateMacros(tdee, goal);
                        /* Mifflin et al. 1990
                           "A new predictive equation for resting energy expenditure in healthy individuals"
                           The American Journal of Clinical Nutrition, Volume 51, Issue 2
                           https://doi.org/10.1093/ajcn/51.2.241
                           Accessed 30 August 2025
                        */
                        
                        // Display results
                        System.out.println("\n=== CALCULATED MACROS ===");
                        System.out.printf("BMR: %.0f kcal/day\n", bmr);
                        System.out.printf("TDEE: %.0f kcal/day\n", tdee);
                        System.out.println("\nRecommended Daily Targets:");
                        System.out.println(calculatedMacros);
                        
                        // Ask if user wants to use these
                        System.out.print("\nUse these targets? (Y/N): ");
                        String useTargets = scanner.nextLine().trim().toUpperCase();
                        if (useTargets.startsWith("Y")) {
                            target = calculatedMacros;
                            System.out.println("Targets updated with calculated values.");
                        } else {
                            System.out.println("Targets not changed. You can enter custom values from the menu.");
                        }
                        
                    } else {
                        // Manual entry (existing code)
                        double kcal = InputHelper.readDoubleInRange(scanner, "Daily target – Calories (kcal): ", 1200, 4000);
                        double p = InputHelper.readDoubleInRange(scanner, "Daily target – Protein (g): ", 50, 300);
                        double c = InputHelper.readDoubleInRange(scanner, "Daily target – Carbs (g): ", 100, 500);
                        double f = InputHelper.readDoubleInRange(scanner, "Daily target – Fat (g): ", 30, 200);
                        target = new Macro(kcal, p, c, f);
                        System.out.println("Targets updated.");
                    }
                }
                case 2 -> {
                    days = InputHelper.readIntInRange(scanner, "How many days (3–5)? ", 3, 5);
                    System.out.println("Days set to: " + days);
                }
                case 3 -> {
                    Recipe[] library = RecipeLibrary.getAll(); // LITE: 6 recipes total
                    currentPlan = planner.buildPlan(days, target, library);
                    
                    // Auto-save for logged-in users
                    if (currentUser != null) {
                        currentUser.setLastPlan(currentPlan);
                        UserManager.saveUser(currentUser);
                    }
                    
                    System.out.println("\n✅ Meal plan generated successfully!");
                    
                    // Sub-menu for immediate actions
                    boolean inPostGenerationMenu = true;
                    while (inPostGenerationMenu) {
                        System.out.println("\n==================================================");
                        System.out.println("                   WHAT'S NEXT?");
                        System.out.println("==================================================");
                        System.out.println();
                        System.out.println("[ 1 ]    📖 View meal plan");
                        System.out.println("[ 2 ]    🛒 View grocery list");  
                        System.out.println("[ 3 ]    🏠 Back to main menu");
                        System.out.println();
                        System.out.println("--------------------------------------------------");
                        
                        int subChoice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, 3);
                        
                        switch (subChoice) {
                            case 1 -> {
                                // View meal plan (reuse case 4 logic)
                                handleMealPlanViewing(scanner, printer, currentPlan, target, days);
                            }
                            case 2 -> {
                                // View grocery list (reuse case 5 logic)
                                handleGroceryListViewing(scanner, printer, currentPlan, days);
                            }
                            case 3 -> inPostGenerationMenu = false;
                        }
                    }
                }
                case 4 -> {
                    if (currentPlan == null) {
                        System.out.println("Generate a plan first (option 3).");
                    } else {
                        handleMealPlanViewing(scanner, printer, currentPlan, target, days);
                    }
                }
                case 5 -> {
                    if (currentPlan == null) {
                        System.out.println("Generate a plan first (option 3).");
                    } else {
                        handleGroceryListViewing(scanner, printer, currentPlan, days);
                    }
                }
                case 6 -> running = false;
            }
        }
        
        // Save on exit for logged-in users
        if (currentUser != null) {
            System.out.println("Saving your preferences...");
            currentUser.setSavedTargets(target);
            currentUser.setSavedDays(days);
            UserManager.saveUser(currentUser);
        }
        
        System.out.println("Goodbye!");
    }
    
    /**
     * Handle meal plan viewing with recipe options
     */
    private static void handleMealPlanViewing(Scanner scanner, ReportPrinter printer, PlanDay[] currentPlan, Macro target, int days) {
        boolean viewingPlan = true;
        while (viewingPlan) {
            // First show the meal plan overview
            printer.printPlan(currentPlan, target);
            
            // Then show recipe viewing options
            System.out.println("\n==================================================");
            System.out.println("                  RECIPE OPTIONS");
            System.out.println("==================================================");
            System.out.println();
            
            // Show options for each day
            for (int i = 0; i < days; i++) {
                System.out.println("[ " + (i + 1) + " ]    View Day " + (i + 1) + " recipes");
            }
            System.out.println("[ " + (days + 1) + " ]    View all recipes");
            System.out.println("[ " + (days + 2) + " ]    Back to previous menu");
            System.out.println();
            System.out.println("--------------------------------------------------");
            
            int viewChoice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, days + 2);
            
            if (viewChoice >= 1 && viewChoice <= days) {
                // Show specific day's recipes
                int dayIndex = viewChoice - 1;
                RecipeViewer.viewDayRecipes(currentPlan[dayIndex], dayIndex + 1);
                System.out.print("\nPress Enter to return to meal plan...");
                scanner.nextLine();
                // Loop continues, will show meal plan again
            } else if (viewChoice == days + 1) {
                // Show all recipes for the entire plan
                System.out.println("\n" + "=".repeat(60));
                System.out.println("ALL RECIPES FOR YOUR MEAL PLAN");
                System.out.println("=".repeat(60));
                for (int i = 0; i < days; i++) {
                    RecipeViewer.viewDayRecipes(currentPlan[i], i + 1);
                }
                System.out.print("\nPress Enter to return to meal plan...");
                scanner.nextLine();
                // Loop continues, will show meal plan again
            } else if (viewChoice == days + 2) {
                // Back to previous menu
                viewingPlan = false;
            }
        }
    }
    
    /**
     * Handle grocery list viewing with all options
     */
    private static void handleGroceryListViewing(Scanner scanner, ReportPrinter printer, PlanDay[] currentPlan, int days) {
        boolean viewingGrocery = true;
        List<GroceryItem> groceryItems = null;
        
        while (viewingGrocery) {
            System.out.println("\n==================================================");
            System.out.println("               GROCERY LIST OPTIONS");
            System.out.println("==================================================");
            System.out.println();
            
            // Show options for each day
            for (int i = 0; i < days; i++) {
                System.out.println("[ " + (i + 1) + " ]    View Day " + (i + 1) + " list");
            }
            System.out.println("[ " + (days + 1) + " ]    View overall list");
            System.out.println("[ " + (days + 2) + " ]    Interactive shopping list");
            System.out.println("[ " + (days + 3) + " ]    Back to previous menu");
            System.out.println();
            System.out.println("--------------------------------------------------");
            
            int groceryChoice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, days + 3);
            
            if (groceryChoice >= 1 && groceryChoice <= days) {
                // Show specific day's grocery list
                int dayIndex = groceryChoice - 1;
                printer.printDayGroceryList(currentPlan[dayIndex], dayIndex + 1);
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            } else if (groceryChoice == days + 1) {
                // Show overall grocery list
                printer.printGroceryList(currentPlan);
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            } else if (groceryChoice == days + 2) {
                // Interactive shopping list
                if (groceryItems == null) {
                    groceryItems = printer.getGroceryItems(currentPlan);
                }
                handleInteractiveGroceryList(scanner, printer, groceryItems);
            } else if (groceryChoice == days + 3) {
                // Back to previous menu
                viewingGrocery = false;
            }
        }
    }
    
    /**
     * Handle interactive grocery list with check-off functionality
     */
    private static void handleInteractiveGroceryList(Scanner scanner, ReportPrinter printer, List<GroceryItem> groceryItems) {
        boolean inInteractiveMode = true;
        
        while (inInteractiveMode) {
            // Display the current list
            printer.printInteractiveGroceryList(groceryItems);
            
            System.out.println("\n==================================================");
            System.out.println("               INTERACTIVE OPTIONS");
            System.out.println("==================================================");
            System.out.println();
            System.out.println("[ 1 ]    Mark item as collected");
            System.out.println("[ 2 ]    Unmark item");
            System.out.println("[ 3 ]    Refresh list");
            System.out.println("[ 4 ]    Back to grocery menu");
            System.out.println();
            System.out.println("--------------------------------------------------");
            
            int choice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, 4);
            
            switch (choice) {
                case 1 -> {
                    // Mark item as collected
                    System.out.println("\nItems to mark as collected:");
                    for (int i = 0; i < groceryItems.size(); i++) {
                        GroceryItem item = groceryItems.get(i);
                        if (!item.isCheckedOff()) {
                            System.out.printf("%d) %s - %s\n", i + 1, item.getName(), item.getFormattedQuantity());
                        }
                    }
                    
                    int itemChoice = InputHelper.readIntInRange(scanner, "Select item number (0 to cancel): ", 0, groceryItems.size());
                    if (itemChoice > 0) {
                        GroceryItem selectedItem = groceryItems.get(itemChoice - 1);
                        if (!selectedItem.isCheckedOff()) {
                            selectedItem.setCheckedOff(true);
                            System.out.println("✓ Marked '" + selectedItem.getName() + "' as collected!");
                        } else {
                            System.out.println("Item already collected!");
                        }
                    }
                }
                case 2 -> {
                    // Unmark item
                    System.out.println("\nCollected items to unmark:");
                    boolean hasCollectedItems = false;
                    for (int i = 0; i < groceryItems.size(); i++) {
                        GroceryItem item = groceryItems.get(i);
                        if (item.isCheckedOff()) {
                            System.out.printf("%d) %s - %s\n", i + 1, item.getName(), item.getFormattedQuantity());
                            hasCollectedItems = true;
                        }
                    }
                    
                    if (!hasCollectedItems) {
                        System.out.println("No items are currently marked as collected.");
                    } else {
                        int itemChoice = InputHelper.readIntInRange(scanner, "Select item number (0 to cancel): ", 0, groceryItems.size());
                        if (itemChoice > 0) {
                            GroceryItem selectedItem = groceryItems.get(itemChoice - 1);
                            if (selectedItem.isCheckedOff()) {
                                selectedItem.setCheckedOff(false);
                                System.out.println("✓ Unmarked '" + selectedItem.getName() + "'!");
                            } else {
                                System.out.println("Item is not currently collected!");
                            }
                        }
                    }
                }
                case 3 -> {
                    // Refresh list - just continue the loop
                    System.out.println("List refreshed!");
                }
                case 4 -> inInteractiveMode = false;
            }
            
            if (inInteractiveMode && choice != 3) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
    }
}

