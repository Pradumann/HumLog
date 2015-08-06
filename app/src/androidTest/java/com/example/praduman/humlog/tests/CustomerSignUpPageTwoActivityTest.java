package com.example.praduman.humlog.tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.praduman.humlog.CustomerSignUpPageTwoActivity;
import com.example.praduman.humlog.LogInActivity;

import junit.framework.TestCase;

/**
 * Created by Praduman on 05/08/2015.
 */
public class CustomerSignUpPageTwoActivityTest extends TestCase {

    private CustomerSignUpPageTwoActivity customerTwoActivity;
    @Override
    protected void setUp () throws Exception{
        super.setUp();
        customerTwoActivity = new CustomerSignUpPageTwoActivity();
    }



    @SmallTest
    public void testCheckCityOne(){

        boolean result = customerTwoActivity.checkCity("select city");
        assertEquals(true, result);
    }

    @SmallTest
    public void testCheckCityTwo(){

        boolean result = customerTwoActivity.checkCity(" city");
        assertEquals(false, result);
    }

    @SmallTest
    public void testCheckPostCodeOne(){

        boolean result = customerTwoActivity.checkPostCode("B29 7DL");
        assertEquals(true, result);
    }

    @SmallTest
    public void testCheckPostCodeTwo(){

        boolean result = customerTwoActivity.checkPostCode("bbb29l");
        assertEquals(false, result);
    }

    @SmallTest
    public void testCheckPostCodeThree(){

        boolean result = customerTwoActivity.checkPostCode("B 2 9 7 D L");
        assertEquals(false, result);
    }



    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
