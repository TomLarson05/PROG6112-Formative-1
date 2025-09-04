package com.mycompany.smartmealplanner.service;

import com.mycompany.smartmealplanner.model.*;
import java.io.*;
import java.util.Scanner;

/**
 * User Management Service - Authentication and Persistence
 * 
 * Handles all user-related operations including registration, login,
 * and data persistence to file system. Implements simple file-based
 * storage using text files for user data and preferences.
 */
public class UserManager {
    
    // ===== CONSTANTS =====
    private static final String USER_DIR = "users";  // Directory for user data files
    
    // ===== STATIC INITIALIZER =====
    /**
     * Ensure the users directory exists on class load
     */
    static {
        File dir = new File(USER_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }
        /* Oracle 2025
           File (Java Platform SE 8)
           Oracle Documentation  
           https://docs.oracle.com/javase/8/docs/api/java/io/File.html
           Accessed 24 August 2025
        */
    }
    
    /**
     * Register a new user with username and password.
     * Creates a new user file if username doesn't exist.
     * 
     * @param username The desired username
     * @param password The user's password (will be hashed)
     * @return The created User object, or null if username already exists
     */
    public static User register(String username, String password) {
        // Check if user already exists
        File userFile = new File(USER_DIR + "/" + username + ".dat");
        
        if (userFile.exists()) {
            System.out.println("Username already exists!");
            return null;
        }
        
        User newUser = new User(username, password);
        saveUser(newUser);
        System.out.println("Registration successful! Welcome, " + username);
        return newUser;
    }
    
    /**
     * Authenticate and login an existing user.
     * Loads user data from file and validates credentials.
     * 
     * @param username The username to authenticate
     * @param password The password to verify
     * @return The User object if login successful, null otherwise
     */
    public static User login(String username, String password) {
        User user = loadUser(username);
        
        if (user == null) {
            System.out.println("User not found!");
            return null;
        }
        
        if (!user.authenticate(password)) {
            System.out.println("Invalid password!");
            return null;
        }
        
        System.out.println("Login successful! Welcome back, " + username);
        return user;
    }
    
    /**
     * Save user data to file
     * @param user The user to save
     */
    public static void saveUser(User user) {
        try {
            File userFile = new File(USER_DIR + "/" + user.getUsername() + ".dat");
            FileWriter writer = new FileWriter(userFile);
            writer.write(user.toFileString());
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }
    
    /**
     * Load user data from file
     * @param username The username to load
     * @return The User object, or null if not found
     */
    public static User loadUser(String username) {
        File userFile = new File(USER_DIR + "/" + username + ".dat");
        
        if (!userFile.exists()) {
            return null;
        }
        
        try {
            Scanner fileScanner = new Scanner(userFile);
            String loadedUsername = "";
            int passwordHash = 0;
            double calories = 2200, protein = 120, carbs = 250, fat = 70;
            int days = 3;
            boolean planExists = false;
            java.util.Map<Integer, String> planData = new java.util.HashMap<>();
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0];
                    String value = parts[1];
                    
                    switch (key) {
                        case "username" -> loadedUsername = value;
                        case "password" -> passwordHash = Integer.parseInt(value);
                        case "calories" -> calories = Double.parseDouble(value);
                        case "protein" -> protein = Double.parseDouble(value);
                        case "carbs" -> carbs = Double.parseDouble(value);
                        case "fat" -> fat = Double.parseDouble(value);
                        case "days" -> days = Integer.parseInt(value);
                        case "plan_exists" -> planExists = Boolean.parseBoolean(value);
                        default -> {
                            if (key.startsWith("plan_day_")) {
                                String dayNumStr = key.substring("plan_day_".length());
                                int dayNum = Integer.parseInt(dayNumStr);
                                planData.put(dayNum, value);
                            }
                        }
                    }
                }
            }
            
            fileScanner.close();
            
            Macro savedTargets = new Macro(calories, protein, carbs, fat);
            User user = new User(loadedUsername, passwordHash, savedTargets, days);
            
            // Load meal plan if it exists
            if (planExists && !planData.isEmpty()) {
                user.setLastPlan(loadMealPlan(planData, days));
            }
            
            return user;
            
        } catch (Exception e) {
            System.out.println("Error loading user data: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Load meal plan from saved data
     * @param planData Map of day number to plan data string
     * @param days Number of days
     * @return PlanDay array or null if loading fails
     */
    private static PlanDay[] loadMealPlan(java.util.Map<Integer, String> planData, int days) {
        try {
            // Get recipe library to match saved recipe names
            com.mycompany.smartmealplanner.data.RecipeLibrary library = new com.mycompany.smartmealplanner.data.RecipeLibrary();
            Recipe[] recipes = library.getAll();
            
            PlanDay[] plan = new PlanDay[days];
            
            for (int i = 0; i < days; i++) {
                String dayData = planData.get(i);
                if (dayData != null) {
                    String[] parts = dayData.split(",");
                    if (parts.length == 6) {
                        // Parse breakfast
                        RecipeSelection breakfast = null;
                        if (!"null".equals(parts[0])) {
                            Recipe bRecipe = findRecipeByName(recipes, parts[0]);
                            if (bRecipe != null) {
                                breakfast = new RecipeSelection(bRecipe, Double.parseDouble(parts[1]));
                            }
                        }
                        
                        // Parse lunch
                        RecipeSelection lunch = null;
                        if (!"null".equals(parts[2])) {
                            Recipe lRecipe = findRecipeByName(recipes, parts[2]);
                            if (lRecipe != null) {
                                lunch = new RecipeSelection(lRecipe, Double.parseDouble(parts[3]));
                            }
                        }
                        
                        // Parse dinner
                        RecipeSelection dinner = null;
                        if (!"null".equals(parts[4])) {
                            Recipe dRecipe = findRecipeByName(recipes, parts[4]);
                            if (dRecipe != null) {
                                dinner = new RecipeSelection(dRecipe, Double.parseDouble(parts[5]));
                            }
                        }
                        
                        plan[i] = new PlanDay(i + 1, breakfast, lunch, dinner);
                    }
                }
            }
            
            return plan;
            
        } catch (Exception e) {
            System.out.println("Error loading meal plan: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Find recipe by name in the recipe library
     * @param recipes Array of recipes
     * @param name Recipe name to find
     * @return Recipe object or null if not found
     */
    private static Recipe findRecipeByName(Recipe[] recipes, String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name)) {
                return recipe;
            }
        }
        return null;
    }
    
    /**
     * Handle the login/register flow
     * @param scanner Scanner for user input
     * @return The logged in User, or null for guest mode
     */
    public static User handleLoginFlow(Scanner scanner) {
        while (true) {
            
            String title = """
                           ███████╗███╗   ███╗ █████╗ ██████╗ ████████╗███╗   ███╗███████╗ █████╗ ██╗     
                           ██╔════╝████╗ ████║██╔══██╗██╔══██╗╚══██╔══╝████╗ ████║██╔════╝██╔══██╗██║     
                           ███████╗██╔████╔██║███████║██████╔╝   ██║   ██╔████╔██║█████╗  ███████║██║     
                           ╚════██║██║╚██╔╝██║██╔══██║██╔══██╗   ██║   ██║╚██╔╝██║██╔══╝  ██╔══██║██║     
                           ███████║██║ ╚═╝ ██║██║  ██║██║  ██║   ██║   ██║ ╚═╝ ██║███████╗██║  ██║███████╗
                           ╚══════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝   ╚═╝     ╚═╝╚══════╝╚═╝  ╚═╝╚══════╝
                           """;
            
            /* Oracle 2020
            Java Text Blocks (multiline string literals)
            Oracle Documentation
            https://docs.oracle.com/en/java/javase/17/language/text-blocks.html
            Author: Oracle
            Accessed 4 September 2025
            */
            
            /* ASCII Art Archive 2025
            Text to ASCII: The best ASCII Art Generator & Maker
            https://www.asciiart.eu/text-to-ascii-art#google_vignette
            Author: ASCII Art Archive 
            Accessed 4 September 2025
            */
            
            System.out.println(title);
            System.out.println("\n==================================================");
            System.out.println("                     LOGIN");
            System.out.println("==================================================");
            System.out.println();
            System.out.println("[ 1 ]    Login");
            System.out.println("[ 2 ]    Register new user");
            System.out.println("[ 3 ]    Continue as guest");
            System.out.println();
            System.out.println("--------------------------------------------------");
            
            int choice = InputHelper.readIntInRange(scanner, "Choose an option: ", 1, 3);
            
            switch (choice) {
                case 1 -> {
                    // Login flow
                    System.out.print("Username: ");
                    String username = scanner.nextLine().trim().toLowerCase();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    
                    User user = login(username, password);
                    if (user != null) {
                        return user;  // Successful login
                    }
                    // Failed login - loop continues, showing login menu again
                    System.out.println("\nPlease try again or register a new account.");
                }
                case 2 -> {
                    // Register flow
                    System.out.print("Choose a username: ");
                    String username = scanner.nextLine().trim().toLowerCase();
                    
                    if (username.isEmpty()) {
                        System.out.println("Username cannot be empty!");
                        System.out.println("\nPlease try again.");
                        continue;  // Back to login menu
                    }
                    
                    System.out.print("Choose a password: ");
                    String password = scanner.nextLine();
                    
                    if (password.length() < 4) {
                        System.out.println("Password must be at least 4 characters!");
                        System.out.println("\nPlease try again.");
                        continue;  // Back to login menu
                    }
                    
                    User newUser = register(username, password);
                    if (newUser != null) {
                        return newUser;  // Successful registration
                    }
                    // Failed registration (username exists) - loop continues
                    System.out.println("\nPlease try a different username or login.");
                }
                case 3 -> {
                    // Guest mode - explicitly chosen
                    System.out.println("Continuing as guest (settings won't be saved)");
                    return null;
                }
            }
        }
    }
}