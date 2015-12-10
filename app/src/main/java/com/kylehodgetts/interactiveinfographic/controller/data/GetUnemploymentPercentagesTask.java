package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;
import com.kylehodgetts.interactiveinfographic.view.GenderStatisticsFragment;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the Employment data from the World Bank API
 */
public class GetUnemploymentPercentagesTask extends GetDataTask {
    private static final String MALE_CACHE_FILE = "maleUnemployment.txt";
    private static final String FEMALE_CACHE_FILE = "femaleUnemployment.txt";

    /**
     * Public Constructor
     * @param context current application context
     */
    public GetUnemploymentPercentagesTask(Activity context) {
        super(context, MALE_CACHE_FILE, FEMALE_CACHE_FILE);
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        DataBank.getDataBank(super.context).addUnemploymentPercentageEntry(dataEntries[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        DataBank dataBank = DataBank.getDataBank(super.context);
        int firstEmploymentEntry = dataBank.getEmploymentEntries().size() - 1;
        Bundle bundle = new Bundle();
        bundle.putSerializable("prevDataEntry", dataBank.getEmploymentEntries().get(firstEmploymentEntry));
        bundle.putSerializable("dataEntry", dataBank.getEmploymentEntries().get(firstEmploymentEntry));
        DataEntry maleDataEntry = null, femaleDataEntry = null;
        for (int i = (dataBank.getUnemploymentPercentages().size() - 1); i >= 0; i--) {
            DataEntry de = dataBank.getUnemploymentPercentages().get(i);
            if (maleDataEntry == null && de.getIndicator().contains("youth male")) {
                maleDataEntry = de;
            } else if (femaleDataEntry == null && de.getIndicator().contains("youth female")) {
                femaleDataEntry = de;
            }
            if (maleDataEntry != null && femaleDataEntry != null) {
                break;
            }
        }

        bundle.putSerializable("maleDataEntry", maleDataEntry);
        bundle.putSerializable("femaleDataEntry", femaleDataEntry);

        final GenderStatisticsFragment genderStatisticsFragment = new GenderStatisticsFragment();
        genderStatisticsFragment.setArguments(bundle);

        new Thread(new Runnable() {
            public void run() {
                context.getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.gender_statistics, genderStatisticsFragment)
                        .commit();
            }
        }).run();
    }
}
