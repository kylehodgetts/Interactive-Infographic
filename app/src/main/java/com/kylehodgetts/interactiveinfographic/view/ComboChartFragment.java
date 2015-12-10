package com.kylehodgetts.interactiveinfographic.view;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

import java.util.ArrayList;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

/**
 * @author Kyle Hodgetts
 * @author Svetoslav Mechev
 * @version 1.0
 * Chart Fragment that makes up the <code>InfoGraphicActivity</code>
 */
public class ComboChartFragment extends Fragment {
    OnYearSelectedListener callback;

    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;
    private DataBank dataBank;
    private ArrayList<AxisValue> axisValues;

    public interface OnYearSelectedListener {
        void onPointSelected(int position);
        void onColumnSelected(int position);
    }

    public ComboChartFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBank = DataBank.getDataBank(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);
        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        renderData();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            callback = (OnYearSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void renderData() {
        data = new ComboLineColumnChartData(renderColumnData(), renderLineData());
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setName("Year");
        axisY.setName("Percentage %");
        axisX.setValues(axisValues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setComboLineColumnChartData(data);
        chart.invalidate();
    }

    private ColumnChartData renderColumnData() {
        int numberOfColumns = dataBank.getEducationEntries().size();
        axisValues = new ArrayList<>();
        ArrayList<Column> columns = new ArrayList<>();
        ArrayList education = dataBank.getEducationEntries();
        int dataSize = education.size();
        for(int i = 0; i < numberOfColumns; i++) {
            DataEntry currentDataEntry = dataBank.getEducationEntries().get((dataSize - 1) - i);
            ArrayList<SubcolumnValue> subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(currentDataEntry.getValue(), Color.GREEN));
            axisValues.add(new AxisValue(i).setLabel(Integer.toString(currentDataEntry.getYear())));
            columns.add(new Column(subcolumnValues));
        }
        return new ColumnChartData(columns);
    }

    private LineChartData renderLineData() {
        // Only one line
        ArrayList<Line> lines = new ArrayList();
        ArrayList<PointValue> points = new ArrayList();
        ArrayList employment = dataBank.getEmploymentEntries();
        int dataSize = employment.size();
        for(int i = 0; i < dataBank.getEmploymentEntries().size(); i++) {
            points.add(new PointValue(i, dataBank.getEmploymentEntries().get((dataSize - 1) - i).getValue()));
        }
        Line line = new Line();
        line.setValues(points);
        line.setColor(Color.RED);
        line.setCubic(false);
        line.setHasLabels(true);
        line.setHasLines(true);
        line.setHasPoints(true);
        lines.add(line);

        return new LineChartData(lines);
    }

    private class ValueTouchListener implements ComboLineColumnChartOnValueSelectListener {

        @Override
        public void onValueDeselected() {}

        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
            callback.onColumnSelected(columnIndex);
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
            callback.onPointSelected(pointIndex);
        }
    }
}
