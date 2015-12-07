package com.kylehodgetts.interactiveinfographic.view;

import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

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

    private Spinner yearSpinner;
    private SeekBar dataSeekBar;

    private ComboLineColumnChartView chart;
    private ComboLineColumnChartData data;
    private DataBank dataBank;
    private ArrayList<AxisValue> axisValues;

    public ComboChartFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_combo_line_column_chart, container, false);
        dataBank = DataBank.getDataBank(getActivity().getApplicationContext());
        chart = (ComboLineColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        renderData();
        yearSpinner = (Spinner) rootView.findViewById(R.id.yearSpinner);
        dataSeekBar = (SeekBar) rootView.findViewById(R.id.dataSeeker);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                                                                    R.array.years,
                                                                    R.layout.spinner_item);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(arrayAdapter);
        yearSpinner.setOnItemSelectedListener(new YearChangeListener());
        dataSeekBar.setMax(30);
        dataSeekBar.setOnSeekBarChangeListener(new DataChangedListener());

        return rootView;
    }

    private void renderData() {
        data = new ComboLineColumnChartData(renderColumnData(), renderLineData());
        Axis axisX = new Axis();
        Axis axisY = new Axis();
        axisX.setName("Year");
        axisY.setName("Value");
        axisX.setValues(axisValues);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        chart.setComboLineColumnChartData(data);
        chart.invalidate();

    }

    private ColumnChartData renderColumnData() {
        int numberOfColumns = dataBank.getEducationEntries().size();
        axisValues = new ArrayList();
        ArrayList<Column> columns = new ArrayList();
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

        }

        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {

        }
    }

    private class YearChangeListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int year = Integer.parseInt((String) parent.getItemAtPosition(position));
            for(DataEntry dataEntry : dataBank.getEducationEntries()) {
                if(dataEntry.getYear() == year) {
                    dataSeekBar.setProgress((int) dataEntry.getValue());
                    dataSeekBar.invalidate();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class DataChangedListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
            for(int i = 0; i < dataBank.getEducationEntries().size(); i++) {
                if(dataBank.getEducationEntries().get(i).getYear() == year) {
                    dataBank.getEducationEntries().get(i).setValue(progress);
                    ComboChartFragment.this.renderData();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }
}
