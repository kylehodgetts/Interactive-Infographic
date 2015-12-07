package com.kylehodgetts.interactiveinfographic.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
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

public class GenderStatisticsFragment extends Fragment {

    private PieChartView chart;
    private PieChartData data;
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
    public void setArguments(Bundle args) {
        super.setArguments(args);
        prevDataEntry = (DataEntry) args.getSerializable("prevDataEntry");
        dataEntry = (DataEntry) args.getSerializable("dataEntry");
        maleDataEntry = (DataEntry) args.getSerializable("maleDataEntry");
        femaleDataEntry = (DataEntry) args.getSerializable("femaleDataEntry");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        chart.invalidate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gender_stats, container, false);
        chart = (PieChartView) rootView.findViewById(R.id.pie_chart);
        upArrow = (ImageView) rootView.findViewById(R.id.up_arrow);
        previousText = (TextView) rootView.findViewById(R.id.previous_text);
        downArrow = (ImageView) rootView.findViewById(R.id.down_arrow);
        currentText = (TextView) rootView.findViewById(R.id.current_text);
        femaleStat = (TextView) rootView.findViewById(R.id.female_stat);
        maleStat = (TextView) rootView.findViewById(R.id.male_stat);
        setComparison();
        generatePieData();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setComparison() {
        if(dataEntry.getValue() > prevDataEntry.getValue()) {
            downArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_active, null));
        }
        else {
            upArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_active, null));
        }
        previousText.setText(Float.toString(prevDataEntry.getValue()));
        currentText.setText(Float.toString(dataEntry.getValue()));
    }

    private void generatePieData() {
        ArrayList<SliceValue> sliceValues = new ArrayList<>();
        sliceValues.add(new SliceValue(maleDataEntry.getValue(), ChartUtils.COLOR_BLUE));
        sliceValues.add(new SliceValue(femaleDataEntry.getValue(), ChartUtils.COLOR_VIOLET));
        data = new PieChartData(sliceValues);
        chart.setPieChartData(data);
        maleStat.setText(Float.toString(maleDataEntry.getValue()));
        femaleStat.setText(Float.toString(femaleDataEntry.getValue()));
    }
}
