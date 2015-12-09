package com.kylehodgetts.interactiveinfographic.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

import java.util.ArrayList;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * @author Kyle Hodgetts
 * @author Svetoslav Mechev
 * @version 1.0
 * Fragment to display unemployment data for a given year based on Gender and comparison on the
 * previous year
 */
public class GenderStatisticsFragment extends Fragment {


    private PieChartView chart;
    private DataEntry prevDataEntry;
    private DataEntry dataEntry;
    private DataEntry maleDataEntry;
    private DataEntry femaleDataEntry;

    private ImageView upArrow;
    private TextView previousText;
    private ImageView downArrow;
    private TextView currentText;
    private TextView femaleStat;
    private TextView maleStat;


    public GenderStatisticsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gender_unemployment, container, false);
        chart = (PieChartView) rootView.findViewById(R.id.pie_chart);
        upArrow = (ImageView) rootView.findViewById(R.id.up_arrow);
        previousText = (TextView) rootView.findViewById(R.id.previous_text);
        downArrow = (ImageView) rootView.findViewById(R.id.down_arrow);
        currentText = (TextView) rootView.findViewById(R.id.current_text);
        femaleStat = (TextView) rootView.findViewById(R.id.female_stat);
        maleStat = (TextView) rootView.findViewById(R.id.male_stat);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args != null) {
            prevDataEntry = (DataEntry) args.getSerializable("prevDataEntry");
            dataEntry = (DataEntry) args.getSerializable("dataEntry");
            maleDataEntry = (DataEntry) args.getSerializable("maleDataEntry");
            femaleDataEntry = (DataEntry) args.getSerializable("femaleDataEntry");
            setComparison();
            generatePieData();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setComparison() {
        if(dataEntry.getValue() > prevDataEntry.getValue()) {
            downArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_active, null));
        } else {
            upArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_active, null));
        }
        previousText.setText(String.format("%.1f (%d)", prevDataEntry.getValue(), prevDataEntry.getYear()));
        currentText.setText(String.format("%.1f (%d)", dataEntry.getValue(), dataEntry.getYear()));
    }

    private void generatePieData() {
        ArrayList<SliceValue> sliceValues = new ArrayList<>();
        sliceValues.add(new SliceValue(femaleDataEntry.getValue(),
                                       getResources().getColor(R.color.femaleStat)));
        sliceValues.add(new SliceValue(maleDataEntry.getValue(), ChartUtils.COLOR_BLUE));
        PieChartData data = new PieChartData(sliceValues);
        data.setHasCenterCircle(true);
        data.setCenterText1(Integer.toString(maleDataEntry.getYear()));
        data.setCenterCircleColor(getResources().getColor(R.color.centerCircle));
        data.setCenterText1Color(Color.WHITE);
        chart.setPieChartData(data);
        maleStat.setText(String.format("%.1f%", maleDataEntry.getValue()));
        femaleStat.setText(String.format("%.1f%", femaleDataEntry.getValue()));
    }
}
