package com.kylehodgetts.interactiveinfographic.controller.data;

import android.content.Context;
import android.util.Log;

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
    public GetEmploymentDataTask(Context context) {
        super(context, CACHE_FILE);
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        Log.d("pull: ", dataEntries[0].toString());
        DataBank.getDataBank(super.context).addEmploymentEntry(dataEntries[0]);
    }

}
