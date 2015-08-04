package com.example.praduman.humlog.tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.praduman.humlog.LogInActivity;

import junit.framework.TestCase;

/**
 * Created by Praduman on 04/08/2015.
 */
public class LogInActivityTest extends TestCase {

    private LogInActivity logInActivity;
    @Override
    protected void setUp () throws Exception{
        super.setUp();
        logInActivity = new LogInActivity();
    }

    @SmallTest
    public void testCheckFieldsOne(){

        boolean result = logInActivity.checkFields("UsernameOne" , "PasswordOne");
        assertEquals(true, result);
    }

    @SmallTest
    public void testCheckFieldsTwo(){

        boolean result = logInActivity.checkFields("", "PasswordOne");
        assertEquals(false , result);
    }

    @SmallTest
    public void testCheckFieldsThree(){

        boolean result = logInActivity.checkFields("", "");
        assertEquals(false , result);
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
