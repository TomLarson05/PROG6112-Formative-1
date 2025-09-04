
package com.mycompany.tvseriesmanager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Series class manages TV series operations including
 * capturing, searching, updating, deleting, and reporting on series.
 */
public class Series {
    
    // ===== CONSTANTS =====
    public static final int MIN_AGE_RESTRICTION = 2;
    public static final int MAX_AGE_RESTRICTION = 18;
    
    // ===== Fields to store series collection =====
    private ArrayList<SeriesModel> seriesList;
    private Scanner scanner;
    
    /**
     * Constructor for Series class.
     * Initializes the series list and scanner for user input.
     */
    public Series() {
        this.seriesList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
    
    // ===== Age Restriction Validation =====
    
    /**
     * Validates if the age restriction is within valid range (2-18).
     * @param ageStr the age restriction as a string
     * @return true if valid, false otherwise
     */
    public boolean isValidAgeRestriction(String ageStr) {
        try {
            int age = Integer.parseInt(ageStr);
            return age >= MIN_AGE_RESTRICTION && age <= MAX_AGE_RESTRICTION;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Prompts user for a valid age restriction with validation.
     * @return a valid age restriction as a string
     */
    private String getValidAgeRestriction() {
        String age;
        while (true) {
            System.out.print("Enter the series age restriction: ");
            age = scanner.nextLine();
            
            if (!isValidAgeRestriction(age)) {
                System.out.println("You have entered an incorrect series age!!!");
                System.out.print("Please re-enter the series age >> ");
            } else {
                break;
            }
        }
        return age;
    }
    
    // ===== CAPTURE SERIES =====
    
    /**
     * Captures a new TV series from user input and adds it to the collection.
     */
    public void CaptureSeries() {
        System.out.println("\nCAPTURE A NEW SERIES");
        System.out.println("****************************************");
        
        // Get series ID
        System.out.print("Enter the series id: ");
        String seriesId = scanner.nextLine();
        
        // Get series name
        System.out.print("Enter the series name: ");
        String seriesName = scanner.nextLine();
        
        // Get valid age restriction
        String seriesAge = getValidAgeRestriction();
        
        // Get number of episodes
        System.out.print("Enter the number of episodes for " + seriesName + ": ");
        String numberOfEpisodes = scanner.nextLine();
        
        // Create and add new series to the list
        SeriesModel newSeries = new SeriesModel(seriesId, seriesName, seriesAge, numberOfEpisodes);
        seriesList.add(newSeries);
        
        System.out.println("Series processed successfully!!!");
    }
    
    // ===== SEARCH SERIES =====
    
    /**
     * Searches for a series by ID and displays its details if found.
     */
    public void SearchSeries() {
        System.out.print("\nEnter the series id to search: ");
        String searchId = scanner.nextLine();
        
        SeriesModel foundSeries = findSeriesById(searchId);
        
        System.out.println("----------------------------------------------------");
        if (foundSeries != null) {
            System.out.println("SERIES ID: " + foundSeries.SeriesId);
            System.out.println("SERIES NAME: " + foundSeries.SeriesName);
            System.out.println("SERIES AGE RESTRICTION: " + foundSeries.SeriesAge);
            System.out.println("SERIES NUMBER OF EPISODES: " + foundSeries.SeriesNumberOfEpisodes);
        } else {
            System.out.println("Series with Series Id: " + searchId + " was not found!");
        }
        System.out.println("----------------------------------------------------");
    }
    
    /**
     * Helper method to find a series by its ID.
     * @param seriesId the ID to search for
     * @return the SeriesModel if found, null otherwise
     */
    public SeriesModel findSeriesById(String seriesId) {
        for (SeriesModel series : seriesList) {
            if (series.SeriesId.equals(seriesId)) {
                return series;
            }
        }
        return null;
    }
    
    // ===== UPDATE SERIES =====
    
    /**
     * Updates an existing series' details.
     */
    public void UpdateSeries() {
        System.out.print("\nEnter the series id to update: ");
        String updateId = scanner.nextLine();
        
        SeriesModel seriestoUpdate = findSeriesById(updateId);
        
        if (seriestoUpdate != null) {
            // Get new series name
            System.out.print("Enter the series name: ");
            String newName = scanner.nextLine();
            
            // Get new valid age restriction
            String newAge = getValidAgeRestriction();
            
            // Get new number of episodes
            System.out.print("Enter the number of episodes: ");
            String newEpisodes = scanner.nextLine();
            
            // Update the series
            seriestoUpdate.SeriesName = newName;
            seriestoUpdate.SeriesAge = newAge;
            seriestoUpdate.SeriesNumberOfEpisodes = newEpisodes;
            
            System.out.println("Series updated successfully!");
        } else {
            System.out.println("Series with Series Id: " + updateId + " was not found!");
        }
    }
    
    // ===== DELETE SERIES =====
    
    /**
     * Deletes a series from the collection after confirmation.
     */
    public void DeleteSeries() {
        System.out.print("\nEnter the Series id to delete: ");
        String deleteId = scanner.nextLine();
        
        SeriesModel seriesToDelete = findSeriesById(deleteId);
        
        if (seriesToDelete != null) {
            System.out.print("Are you sure you want to delete series " + deleteId + " from the system? Yes (y) to delete.\n");
            String confirmation = scanner.nextLine();
            
            if (confirmation.equalsIgnoreCase("y")) {
                seriesList.remove(seriesToDelete);
                System.out.println("----------------------------------------------------");
                System.out.println("Series with Series Id: " + deleteId + " WAS deleted!");
                System.out.println("----------------------------------------------------");
            } else {
                System.out.println("Delete operation cancelled.");
            }
        } else {
            System.out.println("----------------------------------------------------");
            System.out.println("Series with Series Id: " + deleteId + " was not found!");
            System.out.println("----------------------------------------------------");
        }
    }
    
    // ===== SERIES REPORT =====
    
    /**
     * Displays a report of all series in the collection.
     */
    public void SeriesReport() {
        if (seriesList.isEmpty()) {
            System.out.println("\nNo series available to display.");
            return;
        }
        
        int seriesCount = 1;
        for (SeriesModel series : seriesList) {
            System.out.println("Series " + seriesCount);
            System.out.println("----------------------------------------------------");
            System.out.println("SERIES ID: " + series.SeriesId);
            System.out.println("SERIES NAME: " + series.SeriesName);
            System.out.println("SERIES AGE RESTRICTION: " + series.SeriesAge);
            System.out.println("NUMBER OF EPISODES: " + series.SeriesNumberOfEpisodes);
            System.out.println("----------------------------------------------------");
            seriesCount++;
        }
    }
    
    // ===== EXIT APPLICATION =====
    
    /**
     * Exits the series application.
     */
    public void ExitSeriesApplication() {
        System.out.println("\nThank you for using the TV Series Manager!");
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }
    
    // ===== Testing Helper Methods =====
    
    /**
     * Gets the current series list (for testing purposes).
     * @return the list of series
     */
    public ArrayList<SeriesModel> getSeriesList() {
        return seriesList;
    }
    
    /**
     * Adds a series directly to the list (for testing purposes).
     * @param series the series to add
     */
    public void addSeries(SeriesModel series) {
        seriesList.add(series);
    }
    
    /**
     * Clears all series from the list (for testing purposes).
     */
    public void clearSeriesList() {
        seriesList.clear();
    }
}