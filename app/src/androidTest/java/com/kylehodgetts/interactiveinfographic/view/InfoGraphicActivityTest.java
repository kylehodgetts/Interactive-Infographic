package com.kylehodgetts.interactiveinfographic.view;

import android.test.ActivityInstrumentationTestCase2;

import com.kylehodgetts.interactiveinfographic.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by kylehodgetts on 11/12/2015.
 */
public class InfoGraphicActivityTest extends ActivityInstrumentationTestCase2<InfoGraphicActivity> {

    private InfoGraphicActivity infoGraphicActivity;
    private ComboChartFragment comboChartFragment;
    private GenderStatisticsFragment genderStatisticsFragment;
    private InvestmentStatisticsFragment investmentStatisticsFragment;

    public InfoGraphicActivityTest() {
        super(InfoGraphicActivity.class);
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        // Wait for download tasks to finish and attach fragments
        Thread.sleep(2000);
        infoGraphicActivity = getActivity();
        comboChartFragment = (ComboChartFragment) infoGraphicActivity
                                                  .getFragmentManager()
                                                  .findFragmentById(R.id.container);

        genderStatisticsFragment = (GenderStatisticsFragment) infoGraphicActivity
                                                              .getFragmentManager()
                                                              .findFragmentById(R.id.gender_statistics);

        investmentStatisticsFragment = (InvestmentStatisticsFragment) infoGraphicActivity
                                                                      .getFragmentManager()
                                                                      .findFragmentById(R.id.education_statistics);
    }

    @Test
    public void testPreConditions() throws Exception {
        assertNotNull(infoGraphicActivity);
        assertNotNull(comboChartFragment);
        assertNotNull(genderStatisticsFragment);
        assertNotNull(investmentStatisticsFragment);
    }
}