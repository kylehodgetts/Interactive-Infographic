package com.kylehodgetts.interactiveinfographic.model;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Models Education data entry for one year
 */
public class EducationEntry {
    private String countryCode;
    private String country;
    private int year;
    private double value;

    /**
     * Default Constructor
     * @param value         Value for amount invested in education in a given year
     * @param year          Corresponding year
     * @param country       The country the data is related to
     * @param countryCode   Shorthand code for the given country
     */
    public EducationEntry(String countryCode, String country, int year, double value) {
        this.countryCode = countryCode;
        this.country = country;
        this.year = year;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f (%d)", this.countryCode, this.value, this.year);
    }

    /**
     *
     * @return the data value
     */
    public double getValue() {
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
