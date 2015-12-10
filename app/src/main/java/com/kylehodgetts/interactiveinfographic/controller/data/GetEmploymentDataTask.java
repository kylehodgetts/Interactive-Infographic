package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * @author Kyle Hodgetts
 * @author Bau Nguyen
 * @version 1.0
 * AsyncTask that retrieves the Employment data from the World Bank API
 */
public class GetEmploymentDataTask extends GetDataTask {
    private static final String CACHE_FILE = "employment.txt";

    /**
     * Public Constructor
     * @param context current application context
     */
    public GetEmploymentDataTask(Activity context) {
        super(context, CACHE_FILE);
    }

    /**
     * Add the published <code>DataEntry</code> to <code>DataBank</code>
     * @param dataEntries published <code>DataEntry</code>
     */
    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        DataBank.getDataBank(super.context).addEmploymentEntry(dataEntries[0]);
    }
}
