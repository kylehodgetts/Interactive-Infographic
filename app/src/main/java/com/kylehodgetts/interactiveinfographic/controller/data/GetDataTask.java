package com.kylehodgetts.interactiveinfographic.controller.data;

import android.os.AsyncTask;

import com.kylehodgetts.interactiveinfographic.model.DataEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Base class for downloading data from World Bank API
 * @see {@link 'http://data.worldbank.org/developers'}
 */
public abstract class GetDataTask extends AsyncTask<String, DataEntry, Void> {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    protected Void doInBackground(String... params) {
        try {
            /* Get JSON object, extracting the second array in the object, where the data is */
            JSONArray array = new JSONArray(readData(params[0])).getJSONArray(1);
            String previousDataValue = "0.0";

            /* Iterate through JSONArray to parse values for each EmploymentEntry field */
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = array.getJSONObject(i);
                String indicator = data.getJSONObject("indicator").getString("value").split(",")[0];
                String countryCode = data.getJSONObject("country").getString("id");
                String country = data.getJSONObject("country").getString("value");
                String value = data.getString("value");
                if(value.equals("null")) {
                    value = previousDataValue;
                }
                double dataValue = formatData(value);
                int year = Integer.parseInt(data.getString("date"));
                publishProgress(new DataEntry(indicator, countryCode, country, year, dataValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String readData(String urlName) throws IOException {
        StringBuffer buffer = new StringBuffer();
        URL url = new URL(urlName);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();
        BufferedReader in;
        in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = in.readLine();
        while (inputLine != null) {
            buffer.append(inputLine);
            inputLine = in.readLine();
        }
        in.close();
        connection.disconnect();
        return(buffer.toString());
    }

    /**
     *
     * @param value decimal value to be formatted, in string form
     * @return decimal value rounded to 2 decimal places
     */
    private double formatData(String value) {
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.CEILING);
        String formattedValue = DECIMAL_FORMAT.format(Double.parseDouble(value));
        double dataValue = Double.parseDouble(formattedValue);
        return dataValue;
    }
}
