
package com.mycompany.tvseriesmanager;

/**
 * The SeriesModel class represents a TV series with its details.
 * This model stores information about individual TV series including
 * ID, name, age restriction, and number of episodes.
 */
public class SeriesModel {
    // ===== Fields to store series details =====
    public String SeriesId;
    public String SeriesName;
    public String SeriesAge;
    public String SeriesNumberOfEpisodes;
    
    /**
     * Default constructor for SeriesModel.
     */
    public SeriesModel() {
    }
    
    /**
     * Parameterized constructor for SeriesModel.
     * @param seriesId the unique identifier for the series
     * @param seriesName the name of the series
     * @param seriesAge the age restriction for the series
     * @param seriesNumberOfEpisodes the number of episodes in the series
     */
    public SeriesModel(String seriesId, String seriesName, String seriesAge, String seriesNumberOfEpisodes) {
        this.SeriesId = seriesId;
        this.SeriesName = seriesName;
        this.SeriesAge = seriesAge;
        this.SeriesNumberOfEpisodes = seriesNumberOfEpisodes;
    }
}