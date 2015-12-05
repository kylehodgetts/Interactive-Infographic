package com.kylehodgetts.interactiveinfographic.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;

import java.util.ArrayList;

import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

public class ComboChartFragment extends android.support.v4.app.Fragment {

    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;
    private DataBank dataBank;

    public ComboChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);

        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        renderData();
        chart.startDataAnimation();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataBank = DataBank.getDataBank(context);
    }

    private void renderData() {
        data = new ComboLineColumnChartData(renderColumnData(), renderLineData());
        Axis axisX = new Axis().setHasLines(true);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Axis X");
        axisY.setName("Axis Y");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setComboLineColumnChartData(data);

    }

    private ColumnChartData renderColumnData() {
        int numberOfColumns = dataBank.getEducationEntries().size();
        ArrayList<Column> columns = new ArrayList<>();
        for(int i = 0; i < numberOfColumns; i++) {
            ArrayList<SubcolumnValue> subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(dataBank.getEducationEntries().get(i).getValue()));
            columns.add(new Column(subcolumnValues));
        }
        return new ColumnChartData(columns);
    }

    private LineChartData renderLineData() {
        // Only one line
        ArrayList<Line> lines = new ArrayList();
        ArrayList<PointValue> points = new ArrayList();
        for(int i = 0; i < dataBank.getEmploymentEntries().size(); i++) {
            points.add(new PointValue(i, dataBank.getEmploymentEntries().get(i).getValue()));
        }
        Line line = new Line();
        line.setValues(points);
        line.setColor(R.color.colorPrimary);
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
            Toast.makeText(getActivity(), "Selected column: " + value, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
            Toast.makeText(getActivity(), "Selected line point: " + value, Toast.LENGTH_SHORT).show();
        }

    }
}
