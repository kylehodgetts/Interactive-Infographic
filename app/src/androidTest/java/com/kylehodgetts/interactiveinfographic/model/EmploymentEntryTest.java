package com.kylehodgetts.interactiveinfographic.model;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author KyleHodgetts
 * @version 1.0
 * Year Entry Model Testing
 */
public class EmploymentEntryTest extends TestCase {
    private static final String COUNTRY_CODE = "GB";
    private static final String COUNTRY = "Great Britain";
    private static final int YEAR = 1990;
    private static final double VALUE = 23.12;

    private EmploymentEntry employmentEntry;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        employmentEntry = new EmploymentEntry(COUNTRY_CODE, COUNTRY, YEAR, VALUE);
    }

    @Test
    public void testToString() throws Exception {
        String expected = String.format("%s: %.2f (%d)", COUNTRY_CODE, VALUE, YEAR);
        String actual = employmentEntry.toString();
        assertEquals("toString() wasn\'t formatted as expected", expected, actual);
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals("Different values were returned for value", VALUE, employmentEntry.getValue());
    }

    @Test
    public void testGetYear() throws Exception {
        assertEquals("Different values were returned for year", YEAR, employmentEntry.getYear());
    }

    @Test
    public void testGetCountry() throws Exception {
        assertEquals("Different values were returned for country", COUNTRY, employmentEntry.getCountry());
    }

    @Test
    public void testGetCountryCode() throws Exception {
        assertEquals("Different values were returned for country code", COUNTRY_CODE, employmentEntry.getCountryCode());
    }
}
