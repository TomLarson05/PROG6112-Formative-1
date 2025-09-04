
package com.mycompany.tvseriesmanager;

import java.util.Scanner;

/**
 * The main class for the TV Series Manager application.
 * This class provides the menu system and coordinates the series management operations.
 */
public class TVSeriesManager {
    
    // ===== Fields =====
    private static Series seriesManager;
    private static Scanner scanner;
    
    /**
     * Main method to run the TV Series Manager application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        seriesManager = new Series();
        scanner = new Scanner(System.in);
        
        // Display initial welcome screen
        displayWelcomeScreen();
    }
    
    /**
     * Displays the welcome screen and prompts user to launch menu or exit.
     */
    private static void displayWelcomeScreen() {
        System.out.println("\nLATEST SERIES - 2025");
        System.out.println("********************************************");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        
        String choice = scanner.nextLine();
        
        if (choice.equals("1")) {
            displayMainMenu();
        } else {
            exitApplication();
        }
    }
    
    /**
     * Displays the main menu and handles user menu selection.
     */
    private static void displayMainMenu() {
        while (true) {
            System.out.println("\nPlease select one of the following menu items:");
            System.out.println("(1) Capture a new series.");
            System.out.println("(2) Search for a series.");
            System.out.println("(3) Update series age restriction");
            System.out.println("(4) Delete a series.");
            System.out.println("(5) Print series report - 2025");
            System.out.println("(6) Exit Application.");
            
            String choice = scanner.nextLine();
            
            processMenuChoice(choice);
        }
    }
    
    /**
     * Processes the user's menu choice and calls appropriate methods.
     * @param choice the user's menu selection
     */
    private static void processMenuChoice(String choice) {
        switch (choice) {
            case "1":
                // Capture a new series
                seriesManager.CaptureSeries();
                promptToContinue();
                break;
                
            case "2":
                // Search for a series
                seriesManager.SearchSeries();
                promptToContinue();
                break;
                
            case "3":
                // Update series
                seriesManager.UpdateSeries();
                promptToContinue();
                break;
                
            case "4":
                // Delete a series
                seriesManager.DeleteSeries();
                promptToContinue();
                break;
                
            case "5":
                // Print series report
                seriesManager.SeriesReport();
                promptToContinue();
                break;
                
            case "6":
                // Exit application
                exitApplication();
                break;
                
            default:
                System.out.println("Invalid option. Please select a valid menu item.");
        }
    }
    
    /**
     * Prompts the user to continue or exit after completing an operation.
     */
    private static void promptToContinue() {
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        
        if (!choice.equals("1")) {
            exitApplication();
        }
    }
    
    /**
     * Exits the application gracefully.
     */
    private static void exitApplication() {
        System.out.println("\nThank you for using the TV Series Manager!");
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }
}
