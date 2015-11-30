package com.kylehodgetts.interactiveinfographic.model;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Models base data entry for one year
 */
public abstract class DataEntry {
    protected String indicator;
    protected String countryCode;
    protected String country;
    protected int year;
    protected double value;

    /**
     * Default Constructor
     * @param indicator     Data's indicator
     * @param value         Value for amount invested in education in a given year
     * @param year          Corresponding year
     * @param country       The country the data is related to
     * @param countryCode   Shorthand code for the given country
     */
    protected DataEntry(String indicator, String countryCode, String country, int year, double value) {
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
    protected String getIndicator() {
        return indicator;
    }

    /**
     *
     * @return the data value
     */
    protected double getValue() {
        return value;
    }

    /**
     *
     * @return the year that the data refers to
     */
    protected int getYear() {
        return year;
    }

    /**
     *
     * @return the data's corresponding country
     */
    protected String getCountry() {
        return country;
    }

    /**
     *
     * @return The country code of the data's given country
     */
    protected String getCountryCode() {
        return countryCode;
    }
}
