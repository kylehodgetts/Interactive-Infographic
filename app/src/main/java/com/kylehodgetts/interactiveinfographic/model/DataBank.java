package com.kylehodgetts.interactiveinfographic.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Central data store for all <code>DataEntry</code>s
 */
public class DataBank {
    private static DataBank dataBank;
    private Context appContext;

    private ArrayList<EducationEntry> educationEntries;
    private ArrayList<EmploymentEntry> employmentEntries;

    private DataBank(Context context) {
        this.appContext = context;
        educationEntries = new ArrayList<>();
        employmentEntries = new ArrayList<>();
    }

    /**
     *
     * @param context   Current Application Context
     * @return          <code>DataBank</code>, a new instance if current instance is null
     */
    public static DataBank getDataBank(Context context) {
        if (dataBank == null) {
            dataBank = new DataBank(context.getApplicationContext());
        }
        return dataBank;
    }

    /**
     * Adds a new Education Entry to the DataBank
     * @param educationEntry <code>EducationEntry</code> to add
     */
    public void addEducationEntry(EducationEntry educationEntry) {
        educationEntries.add(educationEntry);
    }

    /**
     * Adds a new Employment Entry to the DataBank
     * @param employmentEntry <code>EmploymentEntry</code> to add
     */
    public void addEmploymentEntry(EmploymentEntry employmentEntry) {
        employmentEntries.add(employmentEntry);
    }

    /**
     *
     * @return Collection of <code>EducationEntries</code>
     */
    public ArrayList<EducationEntry> getEducationEntries() {
        return educationEntries;
    }

    /**
     *
     * @return Collection of <code>EmploymentEntries</code>
     */
    public ArrayList<EmploymentEntry> getEmploymentEntries() {
        return employmentEntries;
    }
}
