package com.kylehodgetts.interactiveinfographic.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

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
        setContentView(R.layout.activity_infographic);

        new GetEmploymentDataTask(this).execute(EMPLOYMENT_URL);
        new GetEducationDataTask(this).execute(EDUCATION_URL);
        new GetUnemploymentPercentagesTask(this).execute(MALE_UNEMPLOYMENT_URL, FEMALE_UNEMPLOYMENT_URL);
        dataBank = DataBank.getDataBank(this);
    }

    @Override
    public void onYearSelected(int position) {
        Toast.makeText(this, "POINT SELECTED AT " + position, Toast.LENGTH_SHORT).show();
    }

    public Bundle setGenderStats(int index) {
        dataBank = DataBank.getDataBank(this);
        Bundle bundle = new Bundle();
        DataEntry dataEntry;
        dataEntry = dataBank.getEmploymentEntries()
                .get((dataBank.getEmploymentEntries().size() - 1) - index);
        bundle.putSerializable("dataEntry", dataEntry);
        bundle.putSerializable("prevDataEntry", dataEntry);

        int year = dataEntry.getYear();
        for(DataEntry de : dataBank.getUnemploymentPercentages()){
            if(de.getYear() == year && (de.getIndicator().contains("youth male"))) {
                bundle.putSerializable("maleDataEntry", de);
            }
            else if(de.getYear() == year && (de.getIndicator().contains("youth female"))) {
                bundle.putSerializable("femaleDataEntry", de);
            }
        }
        return bundle;
    }
}
