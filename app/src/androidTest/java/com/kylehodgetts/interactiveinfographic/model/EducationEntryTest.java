package com.kylehodgetts.interactiveinfographic.model;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author KyleHodgetts
 * @version 1.0
 * Education Entry Model Testing
 */
public class EducationEntryTest extends TestCase {
    private static final String INDICATOR = "Indicator";
    private static final String COUNTRY_CODE = "GBR";
    private static final String COUNTRY = "Great Britain";
    private static final int YEAR = 1990;
    private static final double VALUE = 23.12;

    private DataEntry educationEntry;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        educationEntry = new EducationEntry(INDICATOR, COUNTRY_CODE, COUNTRY, YEAR, VALUE);
    }

    @Test
    public void testToString() throws Exception {
        String expected = String.format("%s (%s): %.2f (%d)", INDICATOR, COUNTRY_CODE, VALUE, YEAR);
        String actual = educationEntry.toString();
        assertEquals("toString() wasn\'t formatted as expected", expected, actual);
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals("Different values were returned for value", VALUE, educationEntry.getValue());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals("Different values were returned for year", YEAR, educationEntry.getYear());
    }

    @Test
    public void testGetCountry() throws Exception {
        assertEquals("Different values were returned for country", COUNTRY, educationEntry.getCountry());
    }

    @Test
    public void testGetCountryCode() throws Exception {
        assertEquals("Different values were returned for country code", COUNTRY_CODE, educationEntry.getCountryCode());
    }
}