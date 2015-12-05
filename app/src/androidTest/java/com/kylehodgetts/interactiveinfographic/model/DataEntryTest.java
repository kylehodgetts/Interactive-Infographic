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
}