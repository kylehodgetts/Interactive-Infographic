package com.kylehodgetts.interactiveinfographic.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.controller.data.GetEducationDataTask;
import com.kylehodgetts.interactiveinfographic.controller.data.GetEmploymentDataTask;
import com.kylehodgetts.interactiveinfographic.controller.data.GetUnemploymentPercentagesTask;

public class InfoGraphicActivity extends AppCompatActivity {
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
        if (savedInstanceState == null) {

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new ComboChartFragment())
                    .commit();
        }
    }
}
