
package com.mycompany.smartmealplanner.data;

import com.mycompany.smartmealplanner.model.*;


public class RecipeLibrary {

    /** Return all recipes (LITE) */
    public static Recipe[] getAll() {
    return new Recipe[] {
        // Breakfast (8 total)
        createOatsWithMilkAndBanana(),
        createGreekYogurtAndBerries(),
        createProteinSmoothieBanana(),      
        createAvocadoToast(),               
        createScrambledEggsAndToast(),      
        createPancakesWithSyrup(),          // NEW - high carb
        createFrenchToastWithBerries(),     // NEW - high carb
        createBagelWithCreamCheese(),       // NEW - high carb
        
        // Lunch (9 total)
        createChickenAndRiceBowl(),
        createTunaPasta(),
        createTurkeyChili(),                
        createSalmonSalad(),                
        createChickenWrap(),                
        createBurritoBowl(),                // NEW - high carb
        createTeriyakiChickenRice(),        // NEW - high carb
        createBeefStirFryNoodles(),         // NEW - balanced
        createChickpeaCurryRice(),          // NEW - high carb, vegetarian
        
        // Dinner (8 total)  
        createStirFryTofuAndVeg(),
        createBeefAndSweetPotato(),
        createLentilBolognesePasta(),       
        createSalmonWithQuinoa(),           
        createChickenThighsAndRice(),       
        createSpaghettiCarbonara(),         // NEW - high carb
        createPorkChopsAndMash(),           // NEW - balanced
        createShrimpFriedRice()             // NEW - high carb
    };
}

public static Recipe[] getBreakfastRecipes() {
    return new Recipe[] {
        createOatsWithMilkAndBanana(),
        createGreekYogurtAndBerries(),
        createProteinSmoothieBanana(),
        createAvocadoToast(),
        createScrambledEggsAndToast(),
        createPancakesWithSyrup(),
        createFrenchToastWithBerries(),
        createBagelWithCreamCheese()
    };
}

public static Recipe[] getLunchRecipes() {
    return new Recipe[] {
        createChickenAndRiceBowl(),
        createTunaPasta(),
        createTurkeyChili(),
        createSalmonSalad(),
        createChickenWrap(),
        createBurritoBowl(),
        createTeriyakiChickenRice(),
        createBeefStirFryNoodles(),
        createChickpeaCurryRice()
    };
}

public static Recipe[] getDinnerRecipes() {
    return new Recipe[] {
        createStirFryTofuAndVeg(),
        createBeefAndSweetPotato(),
        createLentilBolognesePasta(),
        createSalmonWithQuinoa(),
        createChickenThighsAndRice(),
        createSpaghettiCarbonara(),
        createPorkChopsAndMash(),
        createShrimpFriedRice()
    };
}

    // ================= Breakfast =================

    private static Recipe createOatsWithMilkAndBanana() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("oats", "g", 60),
            new Ingredient("milk", "ml", 200),
            new Ingredient("banana", "pc", 1)
        };
        String[] instructions = new String[] {
            "Measure oats and milk into a small pot",
            "Bring to a gentle boil, then simmer 5–7 minutes until creamy",
            "Slice banana on top and serve"
        };
        return new BreakfastRecipe("Oats with Milk & Banana", ingredients,
                370, 14, 62, 7, instructions, 5, 10, "Easy");
    }

    private static Recipe createGreekYogurtAndBerries() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("greek yogurt", "g", 170),
            new Ingredient("mixed berries", "g", 100),
            new Ingredient("honey", "g", 10)
        };
        String[] instructions = new String[] {
            "Add yogurt to bowl",
            "Top with berries and drizzle honey"
        };
        return new BreakfastRecipe("Greek Yogurt & Berries", ingredients,
                250, 17, 30, 6, instructions, 5, 0, "Easy");
    }

    // ================= Lunch =================

    private static Recipe createChickenAndRiceBowl() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("chicken breast", "g", 150),
            new Ingredient("rice (cooked)", "g", 150),
            new Ingredient("broccoli", "g", 100),
            new Ingredient("olive oil", "ml", 10)
        };
        String[] instructions = new String[] {
            "Season and cook chicken in oil until done",
            "Steam broccoli",
            "Serve over cooked rice"
        };
        return new LunchRecipe("Chicken & Rice Bowl", ingredients,
                520, 42, 58, 12, instructions, 10, 25, "Easy");
    }

    private static Recipe createTunaPasta() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("pasta", "g", 120),
            new Ingredient("tuna", "g", 120),
            new Ingredient("olive oil", "ml", 10),
            new Ingredient("tomato sauce", "g", 80)
        };
        String[] instructions = new String[] {
            "Boil pasta in salted water",
            "Warm tomato sauce with drained tuna in oil",
            "Combine with pasta"
        };
        return new LunchRecipe("Tuna Pasta", ingredients,
                600, 35, 70, 16, instructions, 5, 15, "Easy");
    }

    // ================= Dinner =================

    private static Recipe createStirFryTofuAndVeg() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("tofu", "g", 150),
            new Ingredient("mixed vegetables", "g", 200),
            new Ingredient("soy sauce", "ml", 15),
            new Ingredient("sesame oil", "ml", 10),
            new Ingredient("rice (cooked)", "g", 100)
        };
        String[] instructions = new String[] {
            "Fry tofu in a little oil until golden",
            "Stir-fry vegetables briefly",
            "Add soy sauce and combine",
            "Serve over cooked rice"
        };
        return new DinnerRecipe("Stir-Fry Tofu & Veg", ingredients,
                450, 24, 40, 18, instructions, 10, 15, "Easy");
    }

    private static Recipe createBeefAndSweetPotato() {
        Ingredient[] ingredients = new Ingredient[] {
            new Ingredient("beef", "g", 150),
            new Ingredient("sweet potato", "g", 250),
            new Ingredient("spinach", "g", 80),
            new Ingredient("olive oil", "ml", 10)
        };
        String[] instructions = new String[] {
            "Roast sweet potato cubes until tender",
            "Pan-sear beef to desired doneness",
            "Quickly sauté spinach",
            "Serve together"
        };
        return new DinnerRecipe("Beef & Sweet Potato", ingredients,
                550, 38, 50, 18, instructions, 10, 35, "Medium");
    }
    
    private static Recipe createProteinSmoothieBanana() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("greek yogurt", "g", 150),
        new Ingredient("milk", "ml", 250),
        new Ingredient("banana", "pc", 1),
        new Ingredient("peanut butter", "g", 20),
        new Ingredient("protein powder", "g", 30)
    };
    String[] instructions = new String[] {
        "Add all ingredients to blender",
        "Blend until smooth"
    };
    // Approx macros: 450 kcal | P 35g | C 45g | F 12g
    return new BreakfastRecipe("Protein Smoothie (Banana)", ingredients,
            450, 35, 45, 12, instructions, 3, 0, "Easy");
}

private static Recipe createTurkeyChili() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("turkey mince", "g", 200),
        new Ingredient("kidney beans", "g", 120),
        new Ingredient("tomato sauce", "g", 200),
        new Ingredient("onion", "g", 80),
        new Ingredient("olive oil", "ml", 10)
    };
    String[] instructions = new String[] {
        "Sauté onion in oil",
        "Brown turkey mince",
        "Add tomato sauce and beans, simmer 15–20 min"
    };
    // Approx macros: 620 kcal | P 45g | C 45g | F 20g
    return new DinnerRecipe("Turkey Chili", ingredients,
            620, 45, 45, 20, instructions, 10, 25, "Easy");
}

private static Recipe createLentilBolognesePasta() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("pasta", "g", 120),
        new Ingredient("lentils (cooked)", "g", 120),
        new Ingredient("tomato sauce", "g", 150),
        new Ingredient("olive oil", "ml", 10),
        new Ingredient("onion", "g", 60)
    };
    String[] instructions = new String[] {
        "Cook pasta al dente",
        "Sauté onion in oil, add sauce and lentils",
        "Simmer 5–7 min, combine with pasta"
    };
    // Approx macros: 640 kcal | P 28g | C 100g | F 14g
    return new DinnerRecipe("Lentil Bolognese Pasta", ingredients,
            640, 28, 100, 14, instructions, 10, 15, "Easy");
}

// ================= NEW BREAKFAST RECIPES =================

private static Recipe createAvocadoToast() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("whole grain bread", "slices", 2),
        new Ingredient("avocado", "pc", 1),
        new Ingredient("eggs", "pc", 2),
        new Ingredient("olive oil", "ml", 5)
    };
    String[] instructions = new String[] {
        "Toast the bread slices",
        "Mash avocado and spread on toast",
        "Fry or poach eggs",
        "Place eggs on avocado toast"
    };
    // Higher fat breakfast option
    return new BreakfastRecipe("Avocado Toast with Eggs", ingredients,
            420, 18, 35, 22, instructions, 5, 10, "Easy");
}

private static Recipe createScrambledEggsAndToast() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("eggs", "pc", 3),
        new Ingredient("whole grain bread", "slices", 2),
        new Ingredient("butter", "g", 10),
        new Ingredient("milk", "ml", 30),
        new Ingredient("cheese", "g", 20)
    };
    String[] instructions = new String[] {
        "Beat eggs with milk",
        "Scramble eggs in butter",
        "Add cheese near the end",
        "Toast bread and serve"
    };
    // Balanced breakfast with good fats
    return new BreakfastRecipe("Scrambled Eggs and Toast", ingredients,
            380, 24, 28, 18, instructions, 5, 10, "Easy");
}

// ================= NEW LUNCH RECIPES =================

private static Recipe createSalmonSalad() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("salmon fillet", "g", 150),
        new Ingredient("mixed greens", "g", 100),
        new Ingredient("quinoa (cooked)", "g", 100),
        new Ingredient("olive oil", "ml", 15),
        new Ingredient("lemon", "pc", 0.5)
    };
    String[] instructions = new String[] {
        "Grill or bake salmon",
        "Prepare salad greens",
        "Add cooked quinoa",
        "Drizzle with olive oil and lemon"
    };
    // Higher fat lunch with omega-3
    return new LunchRecipe("Salmon Quinoa Salad", ingredients,
            580, 38, 42, 26, instructions, 10, 20, "Easy");
}

private static Recipe createChickenWrap() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("chicken breast", "g", 120),
        new Ingredient("whole wheat tortilla", "pc", 1),
        new Ingredient("hummus", "g", 40),
        new Ingredient("vegetables", "g", 80),
        new Ingredient("feta cheese", "g", 30)
    };
    String[] instructions = new String[] {
        "Cook and slice chicken",
        "Warm tortilla",
        "Spread hummus on tortilla",
        "Add chicken, vegetables, and feta",
        "Wrap tightly"
    };
    // Balanced lunch option
    return new LunchRecipe("Mediterranean Chicken Wrap", ingredients,
            520, 38, 48, 18, instructions, 10, 15, "Easy");
}

// ================= NEW DINNER RECIPES =================

private static Recipe createSalmonWithQuinoa() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("salmon fillet", "g", 180),
        new Ingredient("quinoa (cooked)", "g", 150),
        new Ingredient("asparagus", "g", 150),
        new Ingredient("olive oil", "ml", 12),
        new Ingredient("lemon", "pc", 0.5)
    };
    String[] instructions = new String[] {
        "Season and bake salmon",
        "Cook quinoa according to package",
        "Roast asparagus with olive oil",
        "Serve together with lemon"
    };
    // Higher fat dinner with balanced macros
    return new DinnerRecipe("Salmon with Quinoa", ingredients,
            650, 42, 55, 28, instructions, 10, 25, "Easy");
}

private static Recipe createChickenThighsAndRice() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("chicken thighs", "g", 180),
        new Ingredient("brown rice (cooked)", "g", 150),
        new Ingredient("green beans", "g", 120),
        new Ingredient("olive oil", "ml", 10)
    };
    String[] instructions = new String[] {
        "Season and bake chicken thighs",
        "Cook brown rice",
        "Steam green beans",
        "Serve together"
    };
    // Higher fat than chicken breast
    return new DinnerRecipe("Chicken Thighs with Rice", ingredients,
            600, 40, 60, 22, instructions, 10, 30, "Easy");
}

// ================= NEW HIGH-CARB BREAKFAST RECIPES =================

private static Recipe createPancakesWithSyrup() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("pancake mix", "g", 100),
        new Ingredient("milk", "ml", 150),
        new Ingredient("egg", "pc", 1),
        new Ingredient("maple syrup", "ml", 40),
        new Ingredient("butter", "g", 10)
    };
    String[] instructions = new String[] {
        "Mix pancake batter with milk and egg",
        "Cook pancakes on griddle",
        "Serve with butter and maple syrup"
    };
    // High carb breakfast
    return new BreakfastRecipe("Pancakes with Maple Syrup", ingredients,
            480, 12, 85, 10, instructions, 5, 15, "Easy");
}

private static Recipe createFrenchToastWithBerries() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("bread slices", "pc", 3),
        new Ingredient("eggs", "pc", 2),
        new Ingredient("milk", "ml", 60),
        new Ingredient("mixed berries", "g", 100),
        new Ingredient("maple syrup", "ml", 20),
        new Ingredient("butter", "g", 10)
    };
    String[] instructions = new String[] {
        "Whisk eggs with milk",
        "Dip bread in egg mixture",
        "Cook on griddle until golden",
        "Serve with berries and syrup"
    };
    // High carb breakfast
    return new BreakfastRecipe("French Toast with Berries", ingredients,
            420, 16, 68, 12, instructions, 5, 15, "Easy");
}

private static Recipe createBagelWithCreamCheese() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("bagel", "pc", 1),
        new Ingredient("cream cheese", "g", 50),
        new Ingredient("smoked salmon", "g", 40),
        new Ingredient("tomato", "slices", 2),
        new Ingredient("red onion", "slices", 2)
    };
    String[] instructions = new String[] {
        "Toast bagel halves",
        "Spread cream cheese",
        "Add salmon, tomato, and onion"
    };
    // High carb breakfast
    return new BreakfastRecipe("Bagel with Cream Cheese", ingredients,
            400, 14, 72, 8, instructions, 5, 5, "Easy");
}

// ================= NEW LUNCH RECIPES =================

private static Recipe createBurritoBowl() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("chicken breast", "g", 120),
        new Ingredient("rice (cooked)", "g", 200),
        new Ingredient("black beans", "g", 100),
        new Ingredient("corn", "g", 50),
        new Ingredient("salsa", "g", 50),
        new Ingredient("avocado", "pc", 0.5),
        new Ingredient("sour cream", "g", 20)
    };
    String[] instructions = new String[] {
        "Cook and season chicken",
        "Prepare rice and beans",
        "Assemble bowl with all ingredients"
    };
    // High carb lunch
    return new LunchRecipe("Burrito Bowl", ingredients,
            650, 35, 82, 18, instructions, 10, 20, "Easy");
}

private static Recipe createTeriyakiChickenRice() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("chicken thighs", "g", 150),
        new Ingredient("rice (cooked)", "g", 180),
        new Ingredient("teriyaki sauce", "ml", 40),
        new Ingredient("broccoli", "g", 100),
        new Ingredient("sesame oil", "ml", 5)
    };
    String[] instructions = new String[] {
        "Cook chicken with teriyaki sauce",
        "Steam broccoli",
        "Serve over rice with sauce"
    };
    // High carb lunch
    return new LunchRecipe("Teriyaki Chicken Rice", ingredients,
            580, 40, 75, 12, instructions, 10, 20, "Easy");
}

private static Recipe createBeefStirFryNoodles() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("beef strips", "g", 140),
        new Ingredient("noodles", "g", 120),
        new Ingredient("mixed vegetables", "g", 150),
        new Ingredient("soy sauce", "ml", 20),
        new Ingredient("sesame oil", "ml", 10)
    };
    String[] instructions = new String[] {
        "Cook noodles according to package",
        "Stir-fry beef until browned",
        "Add vegetables and noodles",
        "Toss with soy sauce"
    };
    // Balanced lunch
    return new LunchRecipe("Beef Stir Fry Noodles", ingredients,
            620, 38, 68, 20, instructions, 10, 20, "Medium");
}

private static Recipe createChickpeaCurryRice() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("chickpeas", "g", 180),
        new Ingredient("rice (cooked)", "g", 200),
        new Ingredient("curry sauce", "g", 150),
        new Ingredient("spinach", "g", 50),
        new Ingredient("coconut milk", "ml", 50)
    };
    String[] instructions = new String[] {
        "Heat curry sauce with coconut milk",
        "Add chickpeas and simmer",
        "Add spinach at the end",
        "Serve over rice"
    };
    // High carb vegetarian lunch
    return new LunchRecipe("Chickpea Curry with Rice", ingredients,
            550, 20, 85, 15, instructions, 5, 15, "Easy");
}

// ================= NEW DINNER RECIPES =================

private static Recipe createSpaghettiCarbonara() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("spaghetti", "g", 150),
        new Ingredient("bacon", "g", 80),
        new Ingredient("eggs", "pc", 2),
        new Ingredient("parmesan", "g", 40),
        new Ingredient("black pepper", "g", 2)
    };
    String[] instructions = new String[] {
        "Cook spaghetti al dente",
        "Fry bacon until crispy",
        "Mix eggs with parmesan",
        "Toss hot pasta with egg mixture and bacon"
    };
    // High carb dinner
    return new DinnerRecipe("Spaghetti Carbonara", ingredients,
            680, 32, 78, 24, instructions, 10, 20, "Medium");
}

private static Recipe createPorkChopsAndMash() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("pork chops", "g", 180),
        new Ingredient("potatoes", "g", 250),
        new Ingredient("green beans", "g", 100),
        new Ingredient("butter", "g", 15),
        new Ingredient("milk", "ml", 50)
    };
    String[] instructions = new String[] {
        "Season and pan-fry pork chops",
        "Boil and mash potatoes with butter and milk",
        "Steam green beans",
        "Serve together"
    };
    // Balanced dinner
    return new DinnerRecipe("Pork Chops with Mashed Potatoes", ingredients,
            620, 45, 65, 20, instructions, 10, 30, "Easy");
}

private static Recipe createShrimpFriedRice() {
    Ingredient[] ingredients = new Ingredient[] {
        new Ingredient("shrimp", "g", 150),
        new Ingredient("rice (day-old)", "g", 200),
        new Ingredient("eggs", "pc", 2),
        new Ingredient("mixed vegetables", "g", 100),
        new Ingredient("soy sauce", "ml", 20),
        new Ingredient("sesame oil", "ml", 10)
    };
    String[] instructions = new String[] {
        "Scramble eggs and set aside",
        "Stir-fry shrimp until pink",
        "Add rice and vegetables",
        "Mix in eggs and season with soy sauce"
    };
    // High carb dinner
    return new DinnerRecipe("Shrimp Fried Rice", ingredients,
            580, 35, 72, 18, instructions, 10, 15, "Easy");
}
    
}

/* BBC Good Food 2025
   Healthy Recipes Collection
   BBC Good Food
   https://www.bbcgoodfood.com/recipes/collection/healthy
   Accessed 29 August 2025
*/

/* MyFitnessPal 2025
   Sample Meal Prep Recipes for Balanced Nutrition
   MyFitnessPal Blog
   https://blog.myfitnesspal.com/category/recipes/
   Accessed 29 August 2025
*/
