package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;
import android.os.Bundle;

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

    /**
     * Add the published <code>DataEntry</code> to <code>DataBank</code>
     * @param dataEntries published <code>DataEntry</code>
     */
    @Override
    protected void onProgressUpdate(DataEntry... dataEntries) {
        DataBank.getDataBank(super.context).addEducationEntry(dataEntries[0]);
    }

    /**
     * Attaches <code>InvestmentStatisticsFragment</code>
     * when data is available
     * @param aVoid void
     */
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        DataBank dataBank = DataBank.getDataBank(super.context);
        int firstEducationEntry = dataBank.getEducationEntries().size() - 1;

        //Fragment arguments require a bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("prevDataEntry", dataBank.getEmploymentEntries().get(firstEducationEntry));
        bundle.putSerializable("dataEntry", dataBank.getEmploymentEntries().get(firstEducationEntry));

        // Replace old fragment with a new one with the updated arguments
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
