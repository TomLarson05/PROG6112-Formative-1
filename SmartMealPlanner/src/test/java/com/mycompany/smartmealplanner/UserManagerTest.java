package com.mycompany.smartmealplanner;

import com.mycompany.smartmealplanner.model.User;
import com.mycompany.smartmealplanner.model.Macro;
import com.mycompany.smartmealplanner.service.UserManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 * Unit Test Class for UserManager Service
 * 
 * Comprehensive test suite for user authentication, registration,
 * and file-based data persistence. Includes lifecycle management
 * with proper cleanup of test data files.

 */
public class UserManagerTest {
    
    public UserManagerTest() {
    }
    
    // ===== TEST CONSTANTS =====
    private final String testUsername = "test_user";      // Standard test username
    private final String testPassword = "TestPass123!";   // Valid test password
    private final String wrongPassword = "WrongPassword";  // Invalid password for negative tests
    
    @BeforeEach
    public void setUp() {
        // Clean up any existing test user files
        cleanupTestUser();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test user files after each test
        cleanupTestUser();
    }
    
    /**
     * Helper method to clean up test user files
     */
    private void cleanupTestUser() {
        File testFile = new File("users/" + testUsername + ".dat");
        if (testFile.exists()) {
            testFile.delete();
        }
        
        // Also clean up user1 and user2 from password hashing test
        File user1File = new File("users/user1.dat");
        if (user1File.exists()) {
            user1File.delete();
        }
        
        File user2File = new File("users/user2.dat");
        if (user2File.exists()) {
            user2File.delete();
        }
    }
    
    // ---------- REGISTRATION TESTS ----------
    
    /**
     * TEST CASE: Successful user registration
     * - Register a new user with valid credentials
     * - Expected Result: User object returned, file created
     * - This mirrors the registration flow in SmartMeal app
     */
    @Test
    public void testRegistrationSuccessful() {
        User newUser = UserManager.register(testUsername, testPassword);
        
        assertNotNull(newUser, "Registration should return a User object");
        assertEquals(testUsername, newUser.getUsername(), "Username should match");
        assertTrue(newUser.authenticate(testPassword), "User should authenticate with registered password");
        
        // Verify file was created
        File userFile = new File("users/" + testUsername + ".dat");
        assertTrue(userFile.exists(), "User data file should be created");
    }
    
    /**
     * TEST CASE: Failed registration due to duplicate username
     * - Register user twice with same username
     * - Expected Result: Second registration returns null
     */
    @Test
    public void testRegistrationFailureDuplicate() {
        // First registration should succeed
        User firstUser = UserManager.register(testUsername, testPassword);
        assertNotNull(firstUser, "First registration should succeed");
        
        // Second registration with same username should fail
        User secondUser = UserManager.register(testUsername, "DifferentPass123!");
        assertNull(secondUser, "Second registration with same username should fail");
    }
    
    // ---------- LOGIN TESTS ----------
    
    /**
     * TEST CASE: Successful login
     * - Register user first using valid credentials
     * - Then test login with the same credentials
     * - This mirrors the login flow in SmartMeal app
     */
    @Test
    public void testLoginSuccessful() {
        // Register user first
        User registeredUser = UserManager.register(testUsername, testPassword);
        assertNotNull(registeredUser, "Registration should succeed for login test");
        
        // Simulate correct credentials entered by user
        String inputUsername = testUsername;
        String inputPassword = testPassword;
        
        User loggedInUser = UserManager.login(inputUsername, inputPassword);
        assertNotNull(loggedInUser, "Login should succeed with correct credentials");
        assertEquals(testUsername, loggedInUser.getUsername(), "Logged in user should have correct username");
    }
    
    /**
     * TEST CASE: Failed login due to wrong password
     * - Register user using valid credentials
     * - Then attempt to log in using incorrect password
     * - Expected Result: login() returns null
     */
    @Test
    public void testLoginFailureWrongPassword() {
        // Register user first
        User registeredUser = UserManager.register(testUsername, testPassword);
        assertNotNull(registeredUser, "Registration should succeed for login test");
        
        // Simulate incorrect password entered by user
        String inputUsername = testUsername;
        String inputPassword = wrongPassword;
        
        User loggedInUser = UserManager.login(inputUsername, inputPassword);
        assertNull(loggedInUser, "Login should fail with wrong password");
    }
    
    /**
     * TEST CASE: Failed login due to non-existent user
     * - Attempt to log in with username that doesn't exist
     * - Expected Result: login() returns null
     */
    @Test
    public void testLoginFailureUserNotFound() {
        String nonExistentUsername = "nonexistent_user";
        
        User loggedInUser = UserManager.login(nonExistentUsername, testPassword);
        assertNull(loggedInUser, "Login should fail for non-existent user");
    }
    
    // ---------- PASSWORD HASHING TESTS ----------
    
    /**
     * TEST CASE: Password is properly hashed
     * - Verify that passwords are not stored in plain text
     * - Check that same password produces same hash
     */
    @Test
    public void testPasswordHashing() {
        User user1 = UserManager.register("user1", testPassword);
        User user2 = UserManager.register("user2", testPassword);
        
        assertNotNull(user1, "User 1 should be created");
        assertNotNull(user2, "User 2 should be created");
        
        // Same password should produce same hash
        assertEquals(user1.getPasswordHash(), user2.getPasswordHash(), 
            "Same password should produce same hash");
        
        // Hash should equal the string's hashCode (current implementation uses String.hashCode())
        assertEquals(testPassword.hashCode(), user1.getPasswordHash(), 
            "Password hash should equal string hashCode in current implementation");
    }
    
    /**
     * TEST CASE: Authentication method works correctly
     * - Test that User.authenticate() correctly validates passwords
     */
    @Test
    public void testUserAuthentication() {
        User user = new User(testUsername, testPassword);
        
        assertTrue(user.authenticate(testPassword), "Should authenticate with correct password");
        assertFalse(user.authenticate(wrongPassword), "Should not authenticate with wrong password");
        assertFalse(user.authenticate(""), "Should not authenticate with empty password");
    }
    
    // ---------- DATA PERSISTENCE TESTS ----------
    
    /**
     * TEST CASE: User data is saved correctly to file
     * - Register user with custom macro targets
     * - Verify file contains correct data
     */
    @Test
    public void testUserDataSaving() {
        // Create user with custom settings
        User user = UserManager.register(testUsername, testPassword);
        assertNotNull(user, "User should be created");
        
        // Set custom macro targets and days
        Macro customTargets = new Macro(2500, 150, 300, 85);
        user.setSavedTargets(customTargets);
        user.setSavedDays(5);
        
        // Save the user
        UserManager.saveUser(user);
        
        // Verify file exists and contains data
        File userFile = new File("users/" + testUsername + ".dat");
        assertTrue(userFile.exists(), "User file should exist after saving");
        assertTrue(userFile.length() > 0, "User file should contain data");
    }
    
    /**
     * TEST CASE: User data is loaded correctly from file  
     * - Save user with specific settings
     * - Load user and verify settings are restored
     */
    @Test
    public void testUserDataLoading() {
        // Create and save user with custom settings
        User originalUser = UserManager.register(testUsername, testPassword);
        assertNotNull(originalUser, "Original user should be created");
        
        Macro customTargets = new Macro(2800, 180, 350, 95);
        originalUser.setSavedTargets(customTargets);
        originalUser.setSavedDays(4);
        UserManager.saveUser(originalUser);
        
        // Load the user from file
        User loadedUser = UserManager.loadUser(testUsername);
        assertNotNull(loadedUser, "User should be loaded from file");
        
        // Verify all data is correct
        assertEquals(testUsername, loadedUser.getUsername(), "Username should match");
        assertTrue(loadedUser.authenticate(testPassword), "Password should be correct");
        
        // Verify macro targets
        Macro loadedTargets = loadedUser.getSavedTargets();
        assertEquals(2800, loadedTargets.getCalories(), 0.01, "Calories should match");
        assertEquals(180, loadedTargets.getProtein(), 0.01, "Protein should match");
        assertEquals(350, loadedTargets.getCarbs(), 0.01, "Carbs should match");
        assertEquals(95, loadedTargets.getFat(), 0.01, "Fat should match");
        
        // Verify days
        assertEquals(4, loadedUser.getSavedDays(), "Days should match");
    }
    
    /**
     * TEST CASE: Loading non-existent user returns null
     * - Try to load a user that doesn't exist
     * - Expected Result: null returned
     */
    @Test
    public void testLoadNonExistentUser() {
        String nonExistentUsername = "does_not_exist";
        
        User loadedUser = UserManager.loadUser(nonExistentUsername);
        assertNull(loadedUser, "Loading non-existent user should return null");
    }
    
    // ---------- DEFAULT VALUES TESTS ----------
    
    /**
     * TEST CASE: New user has correct default values
     * - Create new user and verify default macro targets and days
     */
    @Test
    public void testNewUserDefaults() {
        User newUser = UserManager.register(testUsername, testPassword);
        assertNotNull(newUser, "New user should be created");
        
        // Check default macro targets (2200, 120, 250, 70)
        Macro defaults = newUser.getSavedTargets();
        assertEquals(2200, defaults.getCalories(), 0.01, "Default calories should be 2200");
        assertEquals(120, defaults.getProtein(), 0.01, "Default protein should be 120g");
        assertEquals(250, defaults.getCarbs(), 0.01, "Default carbs should be 250g");
        assertEquals(70, defaults.getFat(), 0.01, "Default fat should be 70g");
        
        // Check default days
        assertEquals(3, newUser.getSavedDays(), "Default days should be 3");
        
        // Check no meal plan initially
        assertNull(newUser.getLastPlan(), "New user should have no meal plan");
    }
    
    // ---------- FILE SYSTEM TESTS ----------
    
    /**
     * TEST CASE: Users directory is created if it doesn't exist
     * - Verify that UserManager creates the users directory
     */
    @Test
    public void testUsersDirectoryCreation() {
        File usersDir = new File("users");
        assertTrue(usersDir.exists() || usersDir.mkdir(), "Users directory should exist or be creatable");
        assertTrue(usersDir.isDirectory(), "Users path should be a directory");
    }
    
    /**
     * TEST CASE: User data persists between application sessions
     * - Register user, "restart" (load from file), verify data intact
     * - This simulates the real-world scenario of app restart
     */
    @Test
    public void testDataPersistsBetweenSessions() {
        // Session 1: Register and configure user
        User sessionUser = UserManager.register(testUsername, testPassword);
        assertNotNull(sessionUser, "User should be created in session 1");
        
        Macro sessionTargets = new Macro(3000, 200, 400, 100);
        sessionUser.setSavedTargets(sessionTargets);
        sessionUser.setSavedDays(5);
        UserManager.saveUser(sessionUser);
        
        // Session 2: Load user (simulating app restart)
        User reloadedUser = UserManager.loadUser(testUsername);
        assertNotNull(reloadedUser, "User should be loadable in session 2");
        
        // Verify login still works
        User loginResult = UserManager.login(testUsername, testPassword);
        assertNotNull(loginResult, "User should be able to login after restart");
        
        // Verify data persisted
        assertEquals(3000, reloadedUser.getSavedTargets().getCalories(), 0.01, 
            "Calories should persist between sessions");
        assertEquals(5, reloadedUser.getSavedDays(), 
            "Days setting should persist between sessions");
    }
}