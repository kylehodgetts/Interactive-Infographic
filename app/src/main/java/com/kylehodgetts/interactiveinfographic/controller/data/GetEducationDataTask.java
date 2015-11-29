package com.kylehodgetts.interactiveinfographic.controller.data;

import android.os.AsyncTask;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.EducationEntry;

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
 * @author Bau Nguyen
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the education data from the World Bank API
 */
public class GetEducationDataTask extends AsyncTask<String, EducationEntry, Void> {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

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

    protected Void doInBackground(String... params) {
        try {
            /* Get JSON object, extracting the second array in the object, where the data is */
            JSONArray jsonObject = new JSONArray(readData(params[0]));
            JSONArray array = jsonObject.getJSONArray(1);

            /* Iterate through JSONArray to parse values for each EducationEntry field */
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = jsonObject.getJSONArray(1).getJSONObject(i);
                JSONObject countryData = data.getJSONObject("country");
                String countryCode = countryData.getString("id");
                String country = countryData.getString("value");
                double dataValue = formatData(data.getString("value"));
                int year = Integer.parseInt(data.getString("date"));
                publishProgress(new EducationEntry(countryCode, country, year, dataValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(EducationEntry... educationEntry) {
        //TODO Decide what we are going to do with the education entry once created
        Log.d("pull: ", educationEntry[0].toString());
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
