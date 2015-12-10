package com.kylehodgetts.interactiveinfographic.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.controller.data.GetEducationDataTask;
import com.kylehodgetts.interactiveinfographic.controller.data.GetEmploymentDataTask;
import com.kylehodgetts.interactiveinfographic.controller.data.GetUnemploymentPercentagesTask;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * @author Kyle Hodgetts
 * @author Tolga Cakici
 * @author Tautvilas Simkus
 * @author Svetoslav Mechev
 * @author Bau Nguyen
 * @author Mofe Omatsone
 * @version 1.5
 *
 * Main launch activity and fragment host
 */
public class InfoGraphicActivity extends FragmentActivity
        implements ComboChartFragment.OnYearSelectedListener {

    private GenderStatisticsFragment genderStatisticsFragment;
    private InvestmentStatisticsFragment investmentStatisticsFragment;

    private DataBank dataBank;

    private static final String EMPLOYMENT_URL =
            "http://api.worldbank.org/countries/gbr" +
                    "/indicators/SL.UEM.1524.ZS?" +
                    "&date=1991:2011&format=json";

    private static final String MALE_UNEMPLOYMENT_URL =
            "http://api.worldbank.org/countries/gbr/" +
                    "indicators/SL.UEM.1524.MA.NE.ZS?" +
                    "&date=1991%3A2011&format=json";

    private static final String FEMALE_UNEMPLOYMENT_URL =
            "http://api.worldbank.org/countries/" +
                    "gbr/indicators/SL.UEM.1524.FE.NE.ZS?" +
                    "&date=1991%3A2011&format=json";

    private static final String EDUCATION_URL =
            "http://api.worldbank.org/countries/gbr/" +
                    "indicators/SE.XPD.TOTL.GD.ZS?&" +
                    "date=1991:2011&format=json";

    private GetEmploymentDataTask getEmploymentDataTask;
    private GetEducationDataTask getEducationDataTask;
    private GetUnemploymentPercentagesTask getUnemploymentPercentagesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prevents top bar from displaying
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infographic);

        getEmploymentDataTask = new GetEmploymentDataTask(this);
        getEmploymentDataTask.execute(EMPLOYMENT_URL);
        getEducationDataTask = new GetEducationDataTask(this);
        getEducationDataTask.execute(EDUCATION_URL);
        getUnemploymentPercentagesTask = new GetUnemploymentPercentagesTask(this);
        getUnemploymentPercentagesTask.execute(MALE_UNEMPLOYMENT_URL, FEMALE_UNEMPLOYMENT_URL);
    }


    /**
     * Callback method for point selection
     * @param position point that was selected
     */
    @Override
    public void onPointSelected(int position) {
        // Attach updated fragment
        genderStatisticsFragment = new GenderStatisticsFragment();
        genderStatisticsFragment.setArguments(setGenderStats(position));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.gender_statistics, genderStatisticsFragment)
                .commit();
    }

    /**
     * Callback method for column selection
     * @param position column that was selected
     */
    @Override
    public void onColumnSelected(int position) {
        // Attach updated fragment
        investmentStatisticsFragment = new InvestmentStatisticsFragment();
        investmentStatisticsFragment.setArguments(setEducationStats(position));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.education_statistics, investmentStatisticsFragment)
                .commit();
    }

    private Bundle setEducationStats(int index) {
        // Get DataBank in current state
        dataBank = DataBank.getDataBank(this);

        // Bundle to be returned for GenderStatisticsFragment
        Bundle bundle = new Bundle();
        DataEntry dataEntry, prevDataEntry;

        // Get the selected Point
        dataEntry = dataBank.getEducationEntries().get((dataBank.getEducationEntries().size() - 1) - index);

        // Attempt to get the previous point
        // If an exception is thrown, then selected point was at index 0
        try{
            prevDataEntry = dataBank.getEducationEntries()
                    .get((dataBank.getEducationEntries().size() - 1) - (index - 1));
        }
        catch(IndexOutOfBoundsException e) {
            prevDataEntry = dataEntry;
        }

        bundle.putSerializable("dataEntry", dataEntry);
        bundle.putSerializable("prevDataEntry", prevDataEntry);

        return bundle;
    }

    private Bundle setGenderStats(int index) {
        // Get DataBank in current state
        dataBank = DataBank.getDataBank(this);

        // Bundle to be returned for InvestmentStatisticsFragment
        Bundle bundle = new Bundle();
        DataEntry dataEntry, prevDataEntry;

        // Get the selected Point
        dataEntry = dataBank.getEmploymentEntries()
                .get((dataBank.getEmploymentEntries().size() - 1) - index);

        // Attempt to get the previous point
        // If an exception is thrown, then selected point was at index 0
        try{
            prevDataEntry = dataBank.getEmploymentEntries()
                    .get((dataBank.getEmploymentEntries().size() - 1) - (index - 1));
        }
        catch(IndexOutOfBoundsException e) {
            prevDataEntry = dataEntry;
        }

        bundle.putSerializable("dataEntry", dataEntry);
        bundle.putSerializable("prevDataEntry", prevDataEntry);

        int year = dataEntry.getYear();
        boolean maleEntryAssigned = false;
        boolean femaleEntryAssigned = false;

        // Iterate through Gender Entries to find entries for the selected year
        for(DataEntry de : dataBank.getUnemploymentPercentages()){
            if(!maleEntryAssigned && de.getYear() == year
                    && (de.getIndicator().contains("youth male"))) {
                maleEntryAssigned = true;
                bundle.putSerializable("maleDataEntry", de);
            }
            else if(!femaleEntryAssigned && de.getYear() == year
                    && (de.getIndicator().contains("youth female"))) {
                femaleEntryAssigned = true;
                bundle.putSerializable("femaleDataEntry", de);
            }

            if(maleEntryAssigned && femaleEntryAssigned) {
                break;
            }
        }
        return bundle;
    }
}
