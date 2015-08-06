package com.example.praduman.humlog.tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.praduman.humlog.CustomerSignUpPageTwoActivity;
import com.example.praduman.humlog.TradesmanSignUpPageTwoActivity;

import junit.framework.TestCase;

/**
 * Created by Praduman on 05/08/2015.
 */
public class TradesmanSignUpPageTwoActivityTest extends TestCase {

    private TradesmanSignUpPageTwoActivity tradesmanTwoActivity;
    @Override
    protected void setUp () throws Exception{
        super.setUp();
        tradesmanTwoActivity = new TradesmanSignUpPageTwoActivity();
    }



    @SmallTest
    public void testCheckCityOne(){

        boolean result = tradesmanTwoActivity.checkCity("select city");
        assertEquals(true, result);
    }

    @SmallTest
    public void testCheckCityTwo(){

        boolean result = tradesmanTwoActivity.checkCity(" city");
        assertEquals(false, result);
    }

    @SmallTest
    public void testCheckPostCodeOne(){

        boolean result = tradesmanTwoActivity.checkPostCode("B29 7DL");
        assertEquals(true, result);
    }

    @SmallTest
    public void testCheckPostCodeTwo(){

        boolean result = tradesmanTwoActivity.checkPostCode("bbb29l");
        assertEquals(false, result);
    }

    @SmallTest
    public void testCheckPostCodeThree(){

        boolean result = tradesmanTwoActivity.checkPostCode("B 2 9 7 D L");
        assertEquals(false, result);
    }



    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
