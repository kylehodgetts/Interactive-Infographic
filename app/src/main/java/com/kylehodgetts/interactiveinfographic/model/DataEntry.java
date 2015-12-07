package com.kylehodgetts.interactiveinfographic.model;

import java.io.Serializable;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Models base data entry for one year
 */
public class DataEntry implements Serializable {
    private String indicator;
    private String countryCode;
    private String country;
    private int year;
    private float value;

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

    /**
     *
     * @param value new value
     */
    public void setValue(float value) {
        this.value = value;
    }

    /**
     *
     * @param year new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     *
     * @param country new country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     *
     * @param indicator new indicator
     */
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }
}
