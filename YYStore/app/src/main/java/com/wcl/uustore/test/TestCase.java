package com.wcl.uustore.test;

import android.test.InstrumentationTestCase;

/**
 * Created by DoctorY on 2016/4/7.
 */
public class TestCase extends InstrumentationTestCase {

    public void test() throws Exception {
        final int expected = 1;
        final int reality = 2;
        assertEquals(expected, reality);
    }

}
