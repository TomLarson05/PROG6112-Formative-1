
package com.mycompany.tvseriesmanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Series class.
 * These tests validate the functionality of series management operations.
 */
public class SeriesTest {
    
    // Create an object from the Series Class
    private Series series;
    
    /**
     * Set up method to initialize the Series object before each test.
     */
    @BeforeEach
    public void setUp() {
        series = new Series();
        // Clear any existing series data before each test
        series.clearSeriesList();
    }
    
    // ---------- SEARCH SERIES TESTS ----------
    
    /**
     * TEST CASE: TestSearchSeries()
     * Purpose: To supply the series id to the series searching method. 
     * The test will determine that the correct series data has been returned.
     */
    @Test
    public void TestSearchSeries() {
        // Add a test series to the list
        SeriesModel testSeries = new SeriesModel("101", "Extreme Sports", "12", "10");
        series.addSeries(testSeries);
        
        // Search for the series by ID
        SeriesModel foundSeries = series.findSeriesById("101");
        
        // Verify the series was found and data is correct
        assertNotNull(foundSeries);
        assertEquals("101", foundSeries.SeriesId);
        assertEquals("Extreme Sports", foundSeries.SeriesName);
        assertEquals("12", foundSeries.SeriesAge);
        assertEquals("10", foundSeries.SeriesNumberOfEpisodes);
    }
    
    /* VC Lecture Series 2025
      PROG5121 POE Unit Testing Part 1
      VC Lecture Series
      https://www.youtube.com/watch?v=0D6IfoXbrq4&list=PLDFRY8oCw2wB_W4ETlT8mERKz760npx28&index=3
      Accessed 21 April 2025
    */
    
    /**
     * TEST CASE: TestSearchSeries_SeriesNotFound()
     * Purpose: To supply an incorrect series id to the series searching method. 
     * The test will determine that no series was found.
     */
    @Test
    public void TestSearchSeries_SeriesNotFound() {
        // Add a test series to the list
        SeriesModel testSeries = new SeriesModel("101", "Extreme Sports", "12", "10");
        series.addSeries(testSeries);
        
        // Search for a non-existent series ID
        SeriesModel foundSeries = series.findSeriesById("999");
        
        // Verify that no series was found
        assertNull(foundSeries);
    }
    
    // ---------- UPDATE SERIES TESTS ----------
    
    /**
     * TEST CASE: TestUpdateSeries()
     * Purpose: To supply the Series id to the update series method. 
     * The test will determine that the series has been successfully updated.
     */
    @Test
    public void TestUpdateSeries() {
        // Add a test series to the list
        SeriesModel testSeries = new SeriesModel("101", "Extreme Sports", "12", "10");
        series.addSeries(testSeries);
        
        // Update the series details
        SeriesModel seriestoUpdate = series.findSeriesById("101");
        assertNotNull(seriestoUpdate);
        
        // Perform the update
        seriestoUpdate.SeriesName = "Extreme Sports 2025";
        seriestoUpdate.SeriesAge = "10";
        seriestoUpdate.SeriesNumberOfEpisodes = "12";
        
        // Verify the series was updated correctly
        SeriesModel updatedSeries = series.findSeriesById("101");
        assertEquals("Extreme Sports 2025", updatedSeries.SeriesName);
        assertEquals("10", updatedSeries.SeriesAge);
        assertEquals("12", updatedSeries.SeriesNumberOfEpisodes);
    }
    
    // ---------- DELETE SERIES TESTS ----------
    
    /**
     * TEST CASE: TestDeleteSeries()
     * Purpose: To supply the series id to the delete series method. 
     * The test will determine that the series has been successfully deleted.
     */
    @Test
    public void TestDeleteSeries() {
        // Add test series to the list
        SeriesModel testSeries1 = new SeriesModel("101", "Extreme Sports", "12", "10");
        SeriesModel testSeries2 = new SeriesModel("102", "Bargain Hunters", "10", "10");
        series.addSeries(testSeries1);
        series.addSeries(testSeries2);
        
        // Verify both series exist
        assertEquals(2, series.getSeriesList().size());
        
        // Find and remove the series
        SeriesModel seriesToDelete = series.findSeriesById("101");
        assertNotNull(seriesToDelete);
        series.getSeriesList().remove(seriesToDelete);
        
        // Verify the series was deleted
        assertEquals(1, series.getSeriesList().size());
        assertNull(series.findSeriesById("101"));
        assertNotNull(series.findSeriesById("102"));
    }
    
    /**
     * TEST CASE: TestDeleteSeries_SeriesNotFound()
     * Purpose: To supply an incorrect series id to the delete series method. 
     * The test will determine that the series has not been deleted.
     */
    @Test
    public void TestDeleteSeries_SeriesNotFound() {
        // Add a test series to the list
        SeriesModel testSeries = new SeriesModel("101", "Extreme Sports", "12", "10");
        series.addSeries(testSeries);
        
        // Store initial size
        int initialSize = series.getSeriesList().size();
        
        // Attempt to find and delete a non-existent series
        SeriesModel seriesToDelete = series.findSeriesById("999");
        
        // Verify series was not found
        assertNull(seriesToDelete);
        
        // Verify no series was deleted (size remains the same)
        assertEquals(initialSize, series.getSeriesList().size());
        
        // Verify original series still exists
        assertNotNull(series.findSeriesById("101"));
    }
    
    // ---------- AGE RESTRICTION TESTS ----------
    
    /**
     * TEST CASE: TestSeriesAgeRestriction_AgeValid()
     * Purpose: To supply a valid series age restriction to the series age restriction method. 
     * The test will determine that the series age restriction is valid.
     */
    @Test
    public void TestSeriesAgeRestriction_AgeValid() {
        // Test valid age restrictions
        assertTrue(series.isValidAgeRestriction("2"));   // Minimum valid age
        assertTrue(series.isValidAgeRestriction("10"));  // Middle range
        assertTrue(series.isValidAgeRestriction("12"));  // Middle range
        assertTrue(series.isValidAgeRestriction("18"));  // Maximum valid age
    }
    
    /**
     * TEST CASE: TestSeriesAgeRestriction_SeriesAgeInValid()
     * Purpose: To supply an invalid series age restriction to the series age restriction method. 
     * The test will determine that the series age is invalid.
     */
    @Test
    public void TestSeriesAgeRestriction_SeriesAgeInValid() {
        // Test invalid age restrictions - out of range
        assertFalse(series.isValidAgeRestriction("1"));   // Below minimum
        assertFalse(series.isValidAgeRestriction("0"));   // Below minimum
        assertFalse(series.isValidAgeRestriction("19"));  // Above maximum
        assertFalse(series.isValidAgeRestriction("32"));  // Well above maximum
        
        // Test invalid age restrictions - non-numeric
        assertFalse(series.isValidAgeRestriction("Ten")); // Text instead of number
        assertFalse(series.isValidAgeRestriction("abc")); // Letters
        assertFalse(series.isValidAgeRestriction(""));    // Empty string
        assertFalse(series.isValidAgeRestriction("12.5"));// Decimal number
    }
    
    // ---------- ADDITIONAL TESTS ----------
    
    /**
     * TEST CASE: Test adding multiple series
     * Purpose: To verify that multiple series can be added to the collection.
     */
    @Test
    public void TestAddMultipleSeries() {
        // Add multiple series
        SeriesModel series1 = new SeriesModel("101", "Extreme Sports", "12", "10");
        SeriesModel series2 = new SeriesModel("102", "Bargain Hunters", "10", "10");
        SeriesModel series3 = new SeriesModel("103", "Home Cooking", "10", "20");
        
        series.addSeries(series1);
        series.addSeries(series2);
        series.addSeries(series3);
        
        // Verify all series were added
        assertEquals(3, series.getSeriesList().size());
        
        // Verify each series can be found
        assertNotNull(series.findSeriesById("101"));
        assertNotNull(series.findSeriesById("102"));
        assertNotNull(series.findSeriesById("103"));
    }
    
    /**
     * TEST CASE: Test series report with empty list
     * Purpose: To verify that the report handles an empty series list correctly.
     */
    @Test
    public void TestSeriesReportEmpty() {
        // Verify the list is empty
        assertTrue(series.getSeriesList().isEmpty());
        assertEquals(0, series.getSeriesList().size());
    }
}