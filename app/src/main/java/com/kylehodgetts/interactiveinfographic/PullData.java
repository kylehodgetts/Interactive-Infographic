package com.kylehodgetts.interactiveinfographic;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by bau1 on 28/11/2015.
 */
public class PullData extends AsyncTask<String,String,JSONArray> {
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

        protected JSONArray doInBackground(String... urls) {
            try {
                String data = readData(urls[0]);
                JSONArray dataSet = new JSONArray(data);
                return dataSet;
            } catch (Exception e) {
                Log.e("DataDownloadTask", "An error occurred while reading from the server");
                //return "An error occurred while reading from the server";
            }
            return null;
        }

        protected void onPostExecute(JSONArray result) {
            try {
                for (int i = 0; i <= result.length(); i++){
                    JSONObject pageObj = result.getJSONObject(i);
                    JSONArray pages = result.getJSONArray(1);

                    for (int z = 0; z < pages.length(); z++){

                        JSONObject obj2 = pages.getJSONObject(z);
                        int     decimal = obj2.getInt("decimal"),
                                date = obj2.getInt("date");
                        JSONObject indiObj = obj2.getJSONObject("indicator");
                        JSONObject countryObj = obj2.getJSONObject("country");

                        String value = obj2.getString("value"),
                                indiId = indiObj.getString("id"),
                                indiValue = indiObj.getString("value"),
                                countryID = countryObj.getString("id"),
                                countryValue = countryObj.getString("value");
                        DataSet ds = new DataSet();
                        ds.setDataValues(decimal,date,value,indiId,indiValue,countryID,countryValue);
                    }
                }
            } catch (Exception e){

            }

        }
    }
