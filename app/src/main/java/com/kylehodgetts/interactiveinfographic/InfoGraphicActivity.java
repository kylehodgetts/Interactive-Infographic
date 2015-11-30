package com.kylehodgetts.interactiveinfographic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kylehodgetts.interactiveinfographic.controller.data.GetEducationDataTask;
import com.kylehodgetts.interactiveinfographic.controller.data.GetEmploymentDataTask;
import com.kylehodgetts.interactiveinfographic.model.DataBank;

public class InfoGraphicActivity extends AppCompatActivity {
    private static final String EMPLOYMENT_URL = "http://api.worldbank.org/countries/gbr" +
                                                 "/indicators/SL.UEM.1524.ZS?" +
                                                 "&date=1991:2013&format=json";

    private static final String EDUCATION_URL = "http://api.worldbank.org/countries/gbr/" +
                                                "indicators/SE.XPD.TOTL.GD.ZS?&" +
                                                "date=1991:2013&format=json";

    private DataBank dataBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infographic);
        new GetEmploymentDataTask(getApplicationContext()).execute(EMPLOYMENT_URL);
        new GetEducationDataTask(getApplicationContext()).execute(EDUCATION_URL);
        dataBank = DataBank.getDataBank(getApplicationContext());
    }
}
