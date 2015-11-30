package com.kylehodgetts.interactiveinfographic.controller.data;

import android.os.AsyncTask;

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
public abstract class GetDataTask<T> extends AsyncTask<String, T, Void> {
    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

    protected String readData(String urlName) throws IOException {
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
    protected double formatData(String value) {
        DECIMAL_FORMAT.setRoundingMode(RoundingMode.CEILING);
        String formattedValue = DECIMAL_FORMAT.format(Double.parseDouble(value));
        double dataValue = Double.parseDouble(formattedValue);
        return dataValue;
    }

    protected abstract Void doInBackground(String... params);
    protected abstract void onProgressUpdate(T... data);
}
