package com.kylehodgetts.interactiveinfographic.controller.data;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;
import com.kylehodgetts.interactiveinfographic.view.ComboChartFragment;
import com.kylehodgetts.interactiveinfographic.view.GenderStatisticsFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Base class for downloading data from World Bank API
 * @see {@link 'http://data.worldbank.org/developers'}
 */
public abstract class GetDataTask extends AsyncTask<String, DataEntry, Void> {
    protected Activity context;
    private String fileName;

    /**
     * Protected Constructor
     * @param context current application context
     */
    protected GetDataTask(Activity context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    protected Void doInBackground(String... params) {
        for(String s : params) {
            try {
            /* Get JSON object, extracting the second array in the object, where the data is */
                JSONArray array = new JSONArray(readData(s)).getJSONArray(1);
                String previousDataValue = "0.0";

            /* Iterate through JSONArray to parse values for each EmploymentEntry field */
                for (int i = 0; i < array.length(); i++) {
                    JSONObject data = array.getJSONObject(i);
                    String indicator = data.getJSONObject("indicator").getString("value");
                    String countryCode = data.getJSONObject("country").getString("id");
                    String country = data.getJSONObject("country").getString("value");
                    String value = data.getString("value");
                    if(value.equals("null") || value.equals("0.00")) {
                        value = previousDataValue;
                    }
                    previousDataValue = value;
                    float dataValue = Float.parseFloat(value);
                    int year = Integer.parseInt(data.getString("date"));
                    publishProgress(new DataEntry(indicator, countryCode, country, year, dataValue));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        context.getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new ComboChartFragment())
                .commit();
    }

    private String readData(String urlName) throws Exception {
        StringBuilder builder;
        File file = new File(context.getCacheDir(), this.fileName);
        /* Download data and cache */
        if(networkIsAvailable()) {
            Log.d("Network ", "is available");
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();

            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine();
            builder = new StringBuilder();
            while (inputLine != null) {
                builder.append(inputLine);
                inputLine = in.readLine();
            }
            in.close();
            connection.disconnect();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(builder);
            outputStream.flush();
            outputStream.close();
        }
        /* Read from cache */
        else {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            builder = (StringBuilder) objectInputStream.readObject();
        }
        return(builder.toString());
    }

    /**
     * Network helper method
     * @return true if established connection to network, false otherwise
     */
    private boolean networkIsAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
