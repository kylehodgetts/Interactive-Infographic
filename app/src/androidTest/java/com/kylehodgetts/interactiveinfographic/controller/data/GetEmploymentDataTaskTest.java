package com.kylehodgetts.interactiveinfographic.controller.data;

import android.test.InstrumentationTestCase;

import com.kylehodgetts.interactiveinfographic.model.EmploymentEntry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Kyle Hodgetts
 * @version 1.0
 * Tests that the Employment data task fetcher runs as expected
 */
public class GetEmploymentDataTaskTest extends InstrumentationTestCase {
    private static final String URL =   "http://api.worldbank.org/countries/gbr" +
                                        "/indicators/SL.UEM.1524.ZS?" +
                                        "&date=1991:2013&format=json";
    private boolean called;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        called = false;
    }

    @AfterClass
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public final void testSuccessfulFetch() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetEmploymentDataTask(){
                    @Override
                    protected Void doInBackground(String... params) {
                        called = true;
                        return null;
                    }

                    @Override
                    protected void onProgressUpdate(EmploymentEntry... employmentEntry) {
                        signal.countDown();
                        assertNotNull(employmentEntry[0]);
                    }
                }.execute(URL);
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        assertTrue(called);
    }
}
