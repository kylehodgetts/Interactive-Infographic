package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;
import com.kylehodgetts.interactiveinfographic.view.InvestmentStatisticsFragment;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the Education data from the World Bank API
 */
public class GetEducationDataTask extends GetDataTask {
    private static final String CACHE_FILE = "education.txt";

    /**
     * Default Constructor
     *
     * @param context current application context
     */
    public GetEducationDataTask(Activity context) {
        super(context, CACHE_FILE);
    }

    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        DataBank.getDataBank(super.context).addEducationEntry(dataEntries[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        DataBank dataBank = DataBank.getDataBank(super.context);
        int firstEducationEntry = dataBank.getEducationEntries().size() - 1;
        Bundle bundle = new Bundle();
        bundle.putSerializable("prevDataEntry", dataBank.getEmploymentEntries().get(firstEducationEntry));
        bundle.putSerializable("dataEntry", dataBank.getEmploymentEntries().get(firstEducationEntry));

        final InvestmentStatisticsFragment investmentStatisticsFragment = new InvestmentStatisticsFragment();
        investmentStatisticsFragment.setArguments(bundle);
        new Thread(new Runnable() {
            public void run() {
                context.getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.education_statistics, investmentStatisticsFragment)
                        .commit();
            }
        }).run();
    }
}
