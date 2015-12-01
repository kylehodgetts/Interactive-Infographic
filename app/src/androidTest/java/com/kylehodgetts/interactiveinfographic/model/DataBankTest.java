package com.kylehodgetts.interactiveinfographic.model;

import android.content.Context;
import android.test.InstrumentationTestCase;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Tests Data Bank ensuring collections return as expected
 */
public class DataBankTest extends InstrumentationTestCase {

    private Context context;
    private DataBank dataBank;

    @BeforeClass
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        context = getInstrumentation().getContext();
        dataBank = DataBank.getDataBank(context);
    }

    @Test
    public void testGetDataBank() throws Exception {
        assertNotNull("Null DataBank returned", DataBank.getDataBank(context));
    }

    @Test
    public void testAddEducationEntry() throws Exception {
        dataBank.addEducationEntry(new DataEntry("a", "b", "c", 1, 2));
        assertNotNull("EducationEntries ArrayList was null", dataBank.getEducationEntries());
        assertNotNull("Added Education Entry returned null", dataBank.getEducationEntries().get(0));
    }

    @Test
    public void testAddEmploymentEntry() throws Exception {
        dataBank.addEmploymentEntry(new DataEntry("a", "b", "c", 1, 2));
        assertNotNull("EmploymentEntries ArrayList was null", dataBank.getEmploymentEntries());
        assertNotNull("Added Employment Entry returned null", dataBank.getEmploymentEntries().get(0));
    }

    @Test
    public void testGetEducationEntries() throws Exception {
        assertNotNull("EducationEntries ArrayList was null", dataBank.getEducationEntries());
    }

    @Test
    public void testGetEmploymentEntries() throws Exception {
        assertNotNull("EmploymentEntries ArrayList was null", dataBank.getEmploymentEntries());
    }
}