package com.kylehodgetts.interactiveinfographic.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Central data store for all <code>DataEntry</code>s
 */
public class DataBank {
    private static final String TAG = "DATA-BANK";
    private static DataBank dataBank;
    Context appContext;

    private ArrayList<DataEntry> educationEntries;
    private ArrayList<DataEntry> employmentEntries;
    private ArrayList<DataEntry> unemploymentPercentages;

    private DataBank(Context context) {
        this.appContext = context;
        educationEntries = new ArrayList<>();
        employmentEntries = new ArrayList<>();
        unemploymentPercentages = new ArrayList<>();
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
     * @param educationEntry <code>DataEntry</code> to add
     */
    public void addEducationEntry(DataEntry educationEntry) {
        educationEntries.add(educationEntry);
        Log.d(TAG, educationEntry.toString() + " added to educationEntries");
    }

    /**
     * Adds a new Employment Entry to the DataBank
     * @param employmentEntry <code>DataEntry</code> to add
     */
    public void addEmploymentEntry(DataEntry employmentEntry) {
        employmentEntries.add(employmentEntry);
        Log.d(TAG, employmentEntry.toString() + " added to employmentEntries");

    }

    public void addUnemploymentPercentageEntry(DataEntry percentageEntry) {
        unemploymentPercentages.add(percentageEntry);
        Log.d(TAG, percentageEntry.toString() + " added to percentageEntries");
    }

    /**
     *
     * @return Collection of <code>DataEntry</code>s
     */
    public ArrayList<DataEntry> getEducationEntries() {
        return educationEntries;
    }

    /**
     *
     * @return Collection of <code>DataEntry</code>s
     */
    public ArrayList<DataEntry> getEmploymentEntries() {
        return employmentEntries;
    }

    public ArrayList<DataEntry> getUnemploymentPercentages() {
        return unemploymentPercentages;
    }
}
