package com.kylehodgetts.interactiveinfographic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kylehodgetts.interactiveinfographic.controller.data.GetEducationDataTask;

public class InfoGraphicActivity extends AppCompatActivity {
    private static final String EDUCATION_URL = "http://api.worldbank.org/countries/gbr" +
                                                "/indicators/SL.UEM.1524.ZS?" +
                                                "&date=1991:2013&format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infographic);
        new GetEducationDataTask().execute(EDUCATION_URL);
    }
}
