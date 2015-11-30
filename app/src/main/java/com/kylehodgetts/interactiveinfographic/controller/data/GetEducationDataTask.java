package com.kylehodgetts.interactiveinfographic.controller.data;

import android.content.Context;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.EducationEntry;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * AsyncTask that retrieves the Education data from the World Bank API
 */
public class GetEducationDataTask extends GetDataTask<EducationEntry> {
    private Context applicationContext;

    public GetEducationDataTask(Context context) {
        this.applicationContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            /* Get JSON object, extracting the second array in the object, where the data is */
            JSONArray jsonObject = new JSONArray(super.readData(params[0]));
            JSONArray array = jsonObject.getJSONArray(1);
            String previousDataValue = "0.0";

            /* Iterate through JSONArray to parse values for each EmploymentEntry field */
            for (int i = 0; i < array.length(); i++) {
                JSONObject data = jsonObject.getJSONArray(1).getJSONObject(i);
                JSONObject indicatorData = data.getJSONObject("indicator");
                String indicator = indicatorData.getString("value").split(",")[0];
                JSONObject countryData = data.getJSONObject("country");
                String countryCode = countryData.getString("id");
                String country = countryData.getString("value");
                String value = data.getString("value");
                if(value.equals("null")) {
                    value = previousDataValue;
                }
                double dataValue = super.formatData(value);
                int year = Integer.parseInt(data.getString("date"));
                publishProgress(new EducationEntry(indicator, countryCode, country, year, dataValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(EducationEntry... educationEntries) {
        Log.d("pull: ", educationEntries[0].toString());
        DataBank.getDataBank(applicationContext).addEducationEntry(educationEntries[0]);
    }
}
