package com.kylehodgetts.interactiveinfographic;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.test.ApplicationTestCase;

import com.kylehodgetts.interactiveinfographic.model.DataEntry;
import com.kylehodgetts.interactiveinfographic.view.ComboChartFragment;
import com.kylehodgetts.interactiveinfographic.view.GenderStatisticsFragment;
import com.kylehodgetts.interactiveinfographic.view.InfoGraphicActivity;
import com.kylehodgetts.interactiveinfographic.view.InvestmentStatisticsFragment;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Tests that the host activity for all fragments operates correctly
 * Tests that all fragments hosted are populated with data passed to them by the host activity
 * Tests that all fragments are attached
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class InfoGraphicActivityTest extends ApplicationTestCase<Application> {

    private InfoGraphicActivity infoGraphicActivity;
    private ComboChartFragment comboChartFragment;
    private GenderStatisticsFragment genderStatisticsFragment;
    private InvestmentStatisticsFragment investmentStatisticsFragment;
    private final static DataEntry dataEntry = new DataEntry("a", "b", "c", 1, 2);


    public InfoGraphicActivityTest() {
        super(Application.class);
    }

    @Before
    public void setUp() {
        infoGraphicActivity = Robolectric.setupActivity(InfoGraphicActivity.class);
    }

    @Test
    public void testMainActivity() {
        Assert.assertNotNull(infoGraphicActivity);
    }

    @Test
    public void testComboChartDataGenerated() {
        comboChartFragment = new ComboChartFragment();
        startFragment(comboChartFragment);
        assertTrue(comboChartFragment.isAdded());
        assertNotNull(comboChartFragment);
        assertNotNull(comboChartFragment.getData());
        assertNotNull(comboChartFragment.getChart());
    }

    @Test
    public void testGenderStatisticsDataGenerated() {
        genderStatisticsFragment = new GenderStatisticsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataEntry", dataEntry);
        bundle.putSerializable("prevDataEntry", dataEntry);
        bundle.putSerializable("maleDataEntry", dataEntry);
        bundle.putSerializable("femaleDataEntry", dataEntry);
        genderStatisticsFragment.setArguments(bundle);
        startFragment(genderStatisticsFragment);
        assertTrue(genderStatisticsFragment.isAdded());
        assertNotNull(genderStatisticsFragment);
        assertNotNull(genderStatisticsFragment.getData());
        assertNotNull(genderStatisticsFragment.getChart());
    }

    @Test
    public void testInvestmentStatisticsDataGenerated() {
        investmentStatisticsFragment = new InvestmentStatisticsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataEntry", dataEntry);
        bundle.putSerializable("prevDataEntry", dataEntry);
        investmentStatisticsFragment.setArguments(bundle);
        startFragment(investmentStatisticsFragment);
        assertTrue(investmentStatisticsFragment.isAdded());
        assertNotNull(investmentStatisticsFragment.getDataEntry());
        assertNotNull(investmentStatisticsFragment.getPrevDataEntry());
    }

    private void startFragment( Fragment fragment ) {
        FragmentManager fragmentManager = infoGraphicActivity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment, null );
        fragmentTransaction.commit();
    }
}
