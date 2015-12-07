package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * Created by kylehodgetts on 07/12/2015.
 */
public class GetUnemploymentPercentagesTask extends GetDataTask {
    private static final String CACHE_FILE = "unemployment.txt";

    /**
     * Public Constructor
     * @param context current application context
     */
    public GetUnemploymentPercentagesTask(Activity context) {
        super(context, CACHE_FILE);
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        Log.d("pull: ", dataEntries[0].toString());
        DataBank.getDataBank(super.context).addUnemploymentPercentageEntry(dataEntries[0]);
    }
}
