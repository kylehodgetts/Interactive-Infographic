package com.kylehodgetts.interactiveinfographic.controller.data;

import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.EmploymentEntry;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Bau Nguyen
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the education data from the World Bank API
 */
public class GetEmploymentDataTask extends GetDataTask<EmploymentEntry>{

    protected Void doInBackground(String... params) {
        try {
            /* Get JSON object, extracting the second array in the object, where the data is */
            JSONArray jsonObject = new JSONArray(super.readData(params[0]));
            JSONArray array = jsonObject.getJSONArray(1);

            /* Iterate through JSONArray to parse values for each EmploymentEntry field */
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = jsonObject.getJSONArray(1).getJSONObject(i);
                JSONObject countryData = data.getJSONObject("country");
                String countryCode = countryData.getString("id");
                String country = countryData.getString("value");
                double dataValue = super.formatData(data.getString("value"));
                int year = Integer.parseInt(data.getString("date"));
                publishProgress(new EmploymentEntry(countryCode, country, year, dataValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(EmploymentEntry... employmentEntry) {
        //TODO Decide what we are going to do with the education entry once created
        Log.d("pull: ", employmentEntry[0].toString());
    }

}
