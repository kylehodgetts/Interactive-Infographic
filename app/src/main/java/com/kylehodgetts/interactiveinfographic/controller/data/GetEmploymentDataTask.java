package com.kylehodgetts.interactiveinfographic.controller.data;

import android.content.Context;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.EmploymentEntry;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Kyle Hodgetts
 * @author Bau Nguyen
 * @version 1.0
 * AsyncTask that retrieves the Employment data from the World Bank API
 */
public class GetEmploymentDataTask extends GetDataTask<EmploymentEntry> {
    private Context applicationContext;

    public GetEmploymentDataTask(Context context) {
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
                publishProgress(new EmploymentEntry(indicator, countryCode, country, year, dataValue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(EmploymentEntry... employmentEntries) {
        Log.d("pull: ", employmentEntries[0].toString());
        DataBank.getDataBank(applicationContext).addEmploymentEntry(employmentEntries[0]);
    }

}
