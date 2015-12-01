package com.kylehodgetts.interactiveinfographic.controller.data;

import android.content.Context;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the Education data from the World Bank API
 */
public class GetEducationDataTask extends GetDataTask {
    private Context applicationContext;

    public GetEducationDataTask(Context context) {
        this.applicationContext = context;
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        Log.d("pull: ", dataEntries[0].toString());
        DataBank.getDataBank(applicationContext).addEducationEntry(dataEntries[0]);
    }
}
