package com.kylehodgetts.interactiveinfographic.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataBank;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

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
import lecho.lib.hellocharts.view.ComboLineColumnChartView;

/**
 * @author Kyle Hodgetts
 * @author Svetoslav Mechev
 * @version 1.0
 * Chart Fragment that makes up the <code>InfoGraphicActivity</code>
 */
public class ComboChartFragment extends Fragment {

    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;
    private DataBank dataBank;

    public ComboChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);
        dataBank = DataBank.getDataBank(getActivity().getApplicationContext());
        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        renderData();
        return rootView;
    }

    private void renderData() {
        data = new ComboLineColumnChartData(renderColumnData(), renderLineData());
        Axis axisX = new Axis().setHasLines(true);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Year");
        axisY.setName("Value");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setComboLineColumnChartData(data);

    }

    private ColumnChartData renderColumnData() {
        int numberOfColumns = dataBank.getEducationEntries().size();
        ArrayList<Column> columns = new ArrayList<>();
        ArrayList education = dataBank.getEducationEntries();
        int dataSize = education.size();
        for(int i = 0; i < numberOfColumns; i++) {
            ArrayList<SubcolumnValue> subcolumnValues = new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(dataBank.getEducationEntries().get((dataSize - 1) - i).getValue()));
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
            DataEntry dataEntry = dataBank.getEducationEntries()
                    .get((dataBank.getEducationEntries().size() - 1) - columnIndex);
            Toast.makeText(getActivity(), dataEntry.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {
            DataEntry dataEntry = dataBank.getEmploymentEntries()
                    .get((dataBank.getEmploymentEntries().size() - 1) - pointIndex);
            Toast.makeText(getActivity(), dataEntry.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
