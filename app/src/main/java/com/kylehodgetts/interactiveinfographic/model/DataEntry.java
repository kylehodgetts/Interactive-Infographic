package com.kylehodgetts.interactiveinfographic.model;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Models base data entry for one year
 */
public class DataEntry {
    public String indicator;
    public String countryCode;
    public String country;
    public int year;
    public float value;

    /**
     * Default Constructor
     * @param indicator     Data's indicator
     * @param value         Value for amount invested in education in a given year
     * @param year          Corresponding year
     * @param country       The country the data is related to
     * @param countryCode   Shorthand code for the given country
     */
    public DataEntry(String indicator, String countryCode, String country, int year, float value) {
        this.indicator = indicator;
        this.countryCode = countryCode;
        this.country = country;
        this.year = year;
        this.value = value;
    }

    /**
     *
     * @return toString representation
     */
    @Override
    public String toString() {
        return String.format("%s (%s): %.2f (%d)",
                             this.indicator,
                             this.countryCode,
                             this.value,
                             this.year);
    }

    /**
     *
     * @return data indicator
     */
    public String getIndicator() {
        return indicator;
    }

    /**
     *
     * @return the data value
     */
    public float getValue() {
        return value;
    }

    /**
     *
     * @return the year that the data refers to
     */
    public int getYear() {
        return year;
    }

    /**
     *
     * @return the data's corresponding country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @return The country code of the data's given country
     */
    public String getCountryCode() {
        return countryCode;
    }
}
