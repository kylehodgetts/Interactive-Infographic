package com.kylehodgetts.interactiveinfographic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
public class InfoGraphicActivity extends AppCompatActivity {
    TextView txtV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infographic);
        txtV = (TextView) findViewById(R.id.txtListit);
        new PullData().execute("http://api.worldbank.org/countries/gbr/indicators/SL.UEM.1524.ZS?format=json");
    }
    }



