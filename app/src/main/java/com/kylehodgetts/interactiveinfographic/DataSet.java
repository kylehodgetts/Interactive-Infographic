package com.kylehodgetts.interactiveinfographic;

import java.util.ArrayList;

/**
 * Created by bau1 on 28/11/2015.
 */
public class DataSet {
    ArrayList<Integer> decimal, date = new ArrayList<>();
    ArrayList<String> value, indiID, indiValue, countryID, countryValue = new ArrayList<>();

    public void setDataValues(int decimal,int date,String value, String indiID, String indiValue, String countryID, String countryValue){
        this.decimal.add(decimal);
        this.date.add(date);
        this.value.add(value);
        this.indiID.add(indiID);
        this.indiValue.add(indiValue);
        this.countryID.add(countryID);
        this.countryValue.add(countryValue);
    }

}
