package com.kylehodgetts.interactiveinfographic.view;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kylehodgetts.interactiveinfographic.R;
import com.kylehodgetts.interactiveinfographic.model.DataEntry;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 *
 */
public class InvestmentStatisticsFragment extends Fragment {

    private DataEntry prevDataEntry;
    private DataEntry dataEntry;
    private int year;

    private ImageView upArrow;
    private TextView previousText;
    private ImageView downArrow;
    private TextView currentText;

    private ImageView partyImage;

    public InvestmentStatisticsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_investment_statistics, container, false);

        upArrow = (ImageView) rootView.findViewById(R.id.up_arrow);
        previousText = (TextView) rootView.findViewById(R.id.previous_text);
        downArrow = (ImageView) rootView.findViewById(R.id.down_arrow);
        currentText = (TextView) rootView.findViewById(R.id.current_text);
        partyImage = (ImageView) rootView.findViewById(R.id.party_image);

        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args != null) {
            prevDataEntry = (DataEntry) args.getSerializable("prevDataEntry");
            dataEntry = (DataEntry) args.getSerializable("dataEntry");
            year = dataEntry.getYear();
        }
        setComparison();
        setPartyImage();
    }

    public DataEntry getDataEntry() {
        return dataEntry;
    }

    public DataEntry getPrevDataEntry() {
        return prevDataEntry;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setComparison() {
        if(dataEntry.getValue() >= prevDataEntry.getValue()) {
            upArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_up_active, null));
        } else {
            downArrow.setImageDrawable(getResources().getDrawable(R.drawable.ic_down_active, null));
        }
        previousText.setText(String.format("%.1f (%d)", prevDataEntry.getValue(), prevDataEntry.getYear()));
        currentText.setText(String.format("%.1f (%d)", dataEntry.getValue(), dataEntry.getYear()));
    }

    private void setPartyImage() {
        switch (year) {
            case 1997:
            case 2001:
            case 2005:
                partyImage.setImageDrawable(getResources().getDrawable(R.drawable.labour_logo));
                break;

            default:
                partyImage.setImageDrawable(getResources().getDrawable(R.drawable.conservatives_logo));
                break;
        }
    }
}
