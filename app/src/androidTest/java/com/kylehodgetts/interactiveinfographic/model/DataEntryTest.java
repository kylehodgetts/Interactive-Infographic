package com.kylehodgetts.interactiveinfographic.model;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author KyleHodgetts
 * @version 1.0
 * Data Entry Model Testing
 */
public class DataEntryTest extends TestCase {
    
    private static final String INDICATOR = "Indicator";
    private static final String COUNTRY_CODE = "GBR";
    private static final String COUNTRY = "Great Britain";
    private static final int YEAR = 1990;
    private static final float VALUE = 23.3f;

    private DataEntry dataEntry;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        dataEntry = new DataEntry(INDICATOR, COUNTRY_CODE, COUNTRY, YEAR, VALUE);
    }

    @Test
    public void testToString() throws Exception {
        String expected = String.format("%s (%s): %.2f (%d)", INDICATOR, COUNTRY_CODE, VALUE, YEAR);
        String actual = dataEntry.toString();
        assertEquals("toString() wasn\'t formatted as expected", expected, actual);
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals("Different values were returned for value", VALUE, dataEntry.getValue());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals("Different values were returned for year", YEAR, dataEntry.getYear());
    }

    @Test
    public void testGetCountry() throws Exception {
        assertEquals("Different values were returned for country", COUNTRY, dataEntry.getCountry());
    }

    @Test
    public void testGetCountryCode() throws Exception {
        assertEquals("Different values were returned for country code", COUNTRY_CODE, dataEntry.getCountryCode());
    }

    @Test
    public void testSetValue() throws Exception {
        float currentValue = dataEntry.getValue();
        float newValue = 2.0f;
        dataEntry.setValue(newValue);
        assertFalse(dataEntry.getValue() == currentValue);
        assertEquals("Different values were returned for value", newValue, dataEntry.getValue());
    }

    @Test
    public void testSetYear() throws Exception {
        int currentYear = dataEntry.getYear();
        int newYear = 3000;
        dataEntry.setYear(newYear);
        assertFalse(dataEntry.getYear() == currentYear);
        assertEquals("Different values were returned for year", newYear, dataEntry.getYear());
    }

    @Test
    public void testSetCountry() throws Exception {
        String currentCountry = dataEntry.getCountry();
        String newCountry = "Poland";
        dataEntry.setCountry(newCountry);
        assertFalse(dataEntry.getCountry().equals(currentCountry));
        assertEquals("Different values were returned for country", newCountry, dataEntry.getCountry());
    }

    @Test
    public void testSetCountryCode() throws Exception {
        String currentCountryCode = dataEntry.getCountryCode();
        String newCountryCode = "PL";
        dataEntry.setCountryCode(newCountryCode);
        assertFalse(dataEntry.getCountryCode().equals(currentCountryCode));
        assertEquals("Different values were returned for country code", newCountryCode, dataEntry.getCountryCode());
    }
}