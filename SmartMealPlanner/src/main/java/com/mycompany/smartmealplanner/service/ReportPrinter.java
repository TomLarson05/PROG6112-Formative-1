package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.*;
import com.mycompany.smartmealplanner.service.IngredientCategorizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Prints plan and consolidated grocery list.
 * (Now respects RecipeSelection.servings: 1.0x or 2.0x)
 */
public class ReportPrinter {

    public void printPlan(PlanDay[] days, Macro target) {
        System.out.println("\n==================================================");
        System.out.println("                  🍽️  MEAL PLAN  🍽️");
        System.out.println("==================================================");

        Macro total = new Macro(0, 0, 0, 0);

        for (PlanDay d : days) {
            RecipeSelection b = d.getBreakfast();
            RecipeSelection l = d.getLunch();
            RecipeSelection e = d.getDinner();

            int cookTime = b.getRecipe().getTotalTime()
                         + l.getRecipe().getTotalTime()
                         + e.getRecipe().getTotalTime();

            Macro day = sumDay(b, l, e);
            total = total.add(day);

            System.out.println();
            System.out.println("─────────────── DAY " + d.getDayNumber() + " ───────────────");
            System.out.printf("🍳 Breakfast : %-25s (x%.1f | %d min)\n", b.getRecipe().getName(), b.getServings(), b.getRecipe().getTotalTime());
            System.out.printf("🥪 Lunch     : %-25s (x%.1f | %d min)\n", l.getRecipe().getName(), l.getServings(), l.getRecipe().getTotalTime());
            System.out.printf("🍲 Dinner    : %-25s (x%.1f | %d min)\n", e.getRecipe().getName(), e.getServings(), e.getRecipe().getTotalTime());
            System.out.println("🕒 Total Cooking Time: " + cookTime + " minutes");
            System.out.println();

            System.out.printf("⚡ Macros (consumed) : %.0f kcal | P %.0fg | C %.0fg | F %.0fg\n",
                    day.getCalories(), day.getProtein(), day.getCarbs(), day.getFat());
            System.out.printf("🎯 Macros (target)   : %.0f kcal | P %.0fg | C %.0fg | F %.0fg\n",
                    target.getCalories(), target.getProtein(), target.getCarbs(), target.getFat());

            System.out.println();
            System.out.println("Progress:");
            System.out.println("Calories " + bar(day.getCalories(), target.getCalories()));
            System.out.println("Protein  " + bar(day.getProtein(),  target.getProtein()));
            System.out.println("Carbs    " + bar(day.getCarbs(),    target.getCarbs()));
            System.out.println("Fat      " + bar(day.getFat(),      target.getFat()));
            System.out.println();
            System.out.println("───────────────────────────────────────");
        }

        // Average across days
        double n = days.length;
        Macro avg = new Macro(total.getCalories()/n, total.getProtein()/n, total.getCarbs()/n, total.getFat()/n);

        System.out.println();
        System.out.println("📊 === AVERAGE DAILY MACROS ===");
        System.out.printf("%.0f kcal | P %.0fg | C %.0fg | F %.0fg\n",
                avg.getCalories(), avg.getProtein(), avg.getCarbs(), avg.getFat());
        System.out.println();
        System.out.println("Calories " + bar(avg.getCalories(), target.getCalories()));
        System.out.println("Protein  " + bar(avg.getProtein(),  target.getProtein()));
        System.out.println("Carbs    " + bar(avg.getCarbs(),    target.getCarbs()));
        System.out.println("Fat      " + bar(avg.getFat(),      target.getFat()));
        System.out.println();
    }

    public void printGroceryList(PlanDay[] days) {
        System.out.println("\n==================================================");
        System.out.println("              CONSOLIDATED GROCERY                ");
        System.out.println("==================================================\n");

        List<GroceryItem> groceryItems = consolidateGroceryItems(days);
        printCategorizedGroceryList(groceryItems);
    }
    
    public void printDayGroceryList(PlanDay day, int dayNumber) {
        System.out.println("\n==================================================");
        System.out.println("            DAY " + dayNumber + " GROCERY LIST              ");
        System.out.println("==================================================\n");
        
        PlanDay[] singleDay = { day };
        List<GroceryItem> groceryItems = consolidateGroceryItems(singleDay);
        printCategorizedGroceryList(groceryItems);
    }
    
    private List<GroceryItem> consolidateGroceryItems(PlanDay[] days) {
        Map<String, GroceryItem> itemMap = new LinkedHashMap<>();
        
        for (PlanDay d : days) {
            RecipeSelection[] sel = { d.getBreakfast(), d.getLunch(), d.getDinner() };
            for (RecipeSelection rs : sel) {
                Ingredient[] ing = rs.getRecipe().getIngredients();
                double s = rs.getServings();
                for (Ingredient it : ing) {
                    String key = (it.getName().toLowerCase() + "|" + it.getUnit());
                    double add = it.getQuantityPerServing() * s;
                    
                    if (itemMap.containsKey(key)) {
                        GroceryItem existing = itemMap.get(key);
                        existing.setQuantity(existing.getQuantity() + add);
                    } else {
                        IngredientCategory category = IngredientCategorizer.categorizeIngredient(it.getName());
                        itemMap.put(key, new GroceryItem(it.getName(), add, it.getUnit(), category));
                    }
                }
            }
        }
        
        return new ArrayList<>(itemMap.values());
    }
    
    private void printCategorizedGroceryList(List<GroceryItem> items) {
        if (items.isEmpty()) {
            System.out.println("(No items)");
            return;
        }
        
        // Separate completed and uncompleted items
        List<GroceryItem> uncompletedItems = items.stream()
            .filter(item -> !item.isCheckedOff())
            .collect(Collectors.toList());
        
        List<GroceryItem> completedItems = items.stream()
            .filter(GroceryItem::isCheckedOff)
            .collect(Collectors.toList());
        
        // Print uncompleted items by category
        Map<IngredientCategory, List<GroceryItem>> categorizedUncompleted = uncompletedItems.stream()
            .collect(Collectors.groupingBy(GroceryItem::getCategory));
        
        for (IngredientCategory category : IngredientCategory.getShoppingOrder()) {
            if (categorizedUncompleted.containsKey(category) && !categorizedUncompleted.get(category).isEmpty()) {
                System.out.println("\n" + category.getDisplayName());
                System.out.println("─".repeat(40));
                
                for (GroceryItem item : categorizedUncompleted.get(category)) {
                    System.out.printf("%s %-30s %s\n", 
                        item.getCheckboxSymbol(), 
                        item.getName(), 
                        item.getFormattedQuantity());
                }
            }
        }
        
        // Print completed items section at bottom
        if (!completedItems.isEmpty()) {
            System.out.println("\nCOMPLETED ITEMS:");
            System.out.println("─".repeat(40));
            
            for (GroceryItem item : completedItems) {
                System.out.printf("%s %-30s %s  ✓ DONE\n", 
                    item.getCheckboxSymbol(), 
                    item.getName(), 
                    item.getFormattedQuantity());
            }
        }
        
        System.out.println();
    }

    private Macro sumDay(RecipeSelection b, RecipeSelection l, RecipeSelection e) {
        return new Macro(
            b.getRecipe().getCalories()*b.getServings() + l.getRecipe().getCalories()*l.getServings() + e.getRecipe().getCalories()*e.getServings(),
            b.getRecipe().getProtein()*b.getServings()  + l.getRecipe().getProtein()*l.getServings()  + e.getRecipe().getProtein()*e.getServings(),
            b.getRecipe().getCarbs()*b.getServings()    + l.getRecipe().getCarbs()*l.getServings()    + e.getRecipe().getCarbs()*e.getServings(),
            b.getRecipe().getFat()*b.getServings()      + l.getRecipe().getFat()*l.getServings()      + e.getRecipe().getFat()*e.getServings()
        );
    }

    private static String bar(double value, double target) {
        int len = 20;
        if (target <= 0) return "[" + "░".repeat(len) + "] 0%";
        double ratio = Math.min(1.0, value / target);
        int filled = (int)Math.round(ratio * len);
        return "[" + "█".repeat(filled) + "░".repeat(len - filled) + "] " + (int)Math.round(ratio*100) + "%";
        
        /* Stack Overflow 2018
        Repeat string characters in Java (Java 11 String::repeat method)
        Stack Overflow
        https://stackoverflow.com/questions/1235179/simple-way-to-repeat-a-string-in-java
        Author: Michael Borgwardt
        https://stackoverflow.com/users/395098/michael-borgwardt
        Accessed 4 September 2025
        */
    }
    
    /**
     * Get grocery items for interactive functionality
     * @param days Array of plan days
     * @return List of GroceryItem objects for manipulation
     */
    public List<GroceryItem> getGroceryItems(PlanDay[] days) {
        return consolidateGroceryItems(days);
    }
    
    /**
     * Print grocery list with check-off status
     * @param items List of grocery items with potential check-off status
     */
    public void printInteractiveGroceryList(List<GroceryItem> items) {
        System.out.println("\n==================================================");
        System.out.println("           INTERACTIVE GROCERY LIST               ");
        System.out.println("==================================================\n");
        
        printCategorizedGroceryList(items);
        
        long checkedCount = items.stream().mapToLong(item -> item.isCheckedOff() ? 1 : 0).sum();
        System.out.printf("Progress: %d/%d items collected\n", checkedCount, items.size());
    }
}





