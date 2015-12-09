package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the Education data from the World Bank API
 */
public class GetEducationDataTask extends GetDataTask {
    private static final String CACHE_FILE = "education.txt";
    /**
     * Default Constructor
     * @param context current application context
     */
    public GetEducationDataTask(Activity context) {
        super(context, CACHE_FILE);
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        DataBank.getDataBank(super.context).addEducationEntry(dataEntries[0]);
    }
}
