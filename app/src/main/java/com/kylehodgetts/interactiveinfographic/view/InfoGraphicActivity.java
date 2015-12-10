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

public class InfoGraphicActivity extends FragmentActivity
        implements ComboChartFragment.OnYearSelectedListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infographic);

        new GetEmploymentDataTask(this).execute(EMPLOYMENT_URL);
        new GetEducationDataTask(this).execute(EDUCATION_URL);
        new GetUnemploymentPercentagesTask(this).execute(MALE_UNEMPLOYMENT_URL, FEMALE_UNEMPLOYMENT_URL);
    }

    @Override
    public void onYearSelected(int position) {
        GenderStatisticsFragment genderStatisticsFragment = new GenderStatisticsFragment();
        genderStatisticsFragment.setArguments(setGenderStats(position));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.gender_statistics, genderStatisticsFragment)
                .commit();
    }

    private Bundle setGenderStats(int index) {
        dataBank = DataBank.getDataBank(this);
        Bundle bundle = new Bundle();
        DataEntry dataEntry, prevDataEntry;
        dataEntry = dataBank.getEmploymentEntries()
                .get((dataBank.getEmploymentEntries().size() - 1) - index);
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
