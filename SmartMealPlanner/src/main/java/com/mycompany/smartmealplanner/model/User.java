package com.mycompany.smartmealplanner.model;

/**
 * User Model Class - Authentication and Preferences
 * 
 * Represents a registered user with authentication credentials and
 * saved meal planning preferences. Handles password hashing and
 * persistent storage of user settings and meal plans.
 */
public class User {
    
    // ===== FIELDS =====
    private String username;        // Unique identifier for user
    private int passwordHash;       // Simple hash for academic purposes
    private Macro savedTargets;     // User's macro-nutrient targets
    private int savedDays;          // Preferred number of planning days
    private PlanDay[] lastPlan;     // Previously generated meal plan
    
    /**
     * Constructor for new user registration.
     * Creates a new user with default settings and hashed password.
     * 
     * @param username The unique username
     * @param password The password (will be hashed using String.hashCode())
     */
    public User(String username, String password) {
        this.username = username;
        this.passwordHash = password.hashCode();  // Simple hash for academic use
        
        // Initialize with default values suitable for average adult
        this.savedTargets = new Macro(2200, 120, 250, 70);  // Moderate targets
        this.savedDays = 3;                                   // Minimum planning period
        this.lastPlan = null;                                 // No saved plan initially
        
        /* Oracle 2025
           String.hashCode() Method
           Oracle Documentation
           https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#hashCode--
           Accessed 23 August 2025
        */
    }
    
    /**
     * Constructor for loading existing user
     * @param username The username
     * @param passwordHash The hashed password
     * @param savedTargets The saved macro targets
     * @param savedDays The saved days preference
     */
    public User(String username, int passwordHash, Macro savedTargets, int savedDays) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.savedTargets = savedTargets;
        this.savedDays = savedDays;
        this.lastPlan = null;
    }
    
    /**
     * Verify password for authentication
     * @param password The password to check
     * @return true if password matches
     */
    public boolean authenticate(String password) {
        return password.hashCode() == this.passwordHash;
    }
    
    /**
     * Convert user data to string for file storage
     * @return String representation for saving
     */
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("username=").append(username).append("\n");
        sb.append("password=").append(passwordHash).append("\n");
        sb.append("calories=").append(savedTargets.getCalories()).append("\n");
        sb.append("protein=").append(savedTargets.getProtein()).append("\n");
        sb.append("carbs=").append(savedTargets.getCarbs()).append("\n");
        sb.append("fat=").append(savedTargets.getFat()).append("\n");
        sb.append("days=").append(savedDays).append("\n");
        
        // Save meal plan if exists
        if (lastPlan != null && lastPlan.length > 0) {
            sb.append("plan_exists=true").append("\n");
            for (int i = 0; i < lastPlan.length; i++) {
                PlanDay day = lastPlan[i];
                sb.append("plan_day_").append(i).append("=");
                
                // Format: BreakfastName,BreakfastServing,LunchName,LunchServing,DinnerName,DinnerServing
                if (day.getBreakfast() != null) {
                    sb.append(day.getBreakfast().getRecipe().getName()).append(",");
                    sb.append(day.getBreakfast().getServings()).append(",");
                } else {
                    sb.append("null,0.0,");
                }
                
                if (day.getLunch() != null) {
                    sb.append(day.getLunch().getRecipe().getName()).append(",");
                    sb.append(day.getLunch().getServings()).append(",");
                } else {
                    sb.append("null,0.0,");
                }
                
                if (day.getDinner() != null) {
                    sb.append(day.getDinner().getRecipe().getName()).append(",");
                    sb.append(day.getDinner().getServings());
                } else {
                    sb.append("null,0.0");
                }
                
                sb.append("\n");
            }
        } else {
            sb.append("plan_exists=false").append("\n");
        }
        
        return sb.toString();
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public Macro getSavedTargets() {
        return savedTargets;
    }
    
    public void setSavedTargets(Macro targets) {
        this.savedTargets = targets;
    }
    
    public int getSavedDays() {
        return savedDays;
    }
    
    public void setSavedDays(int days) {
        this.savedDays = days;
    }
    
    public PlanDay[] getLastPlan() {
        return lastPlan;
    }
    
    public void setLastPlan(PlanDay[] plan) {
        this.lastPlan = plan;
    }
    
    public int getPasswordHash() {
        return passwordHash;
    }
}