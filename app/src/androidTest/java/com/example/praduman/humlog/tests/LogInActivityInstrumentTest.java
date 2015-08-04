package com.example.praduman.humlog.tests;

import android.app.Activity;
import android.test.InstrumentationTestCase;
import android.widget.EditText;
import com.example.praduman.humlog.LogInActivity;
import com.example.praduman.humlog.R;

/**
 * Created by Praduman on 04/08/2015.
 */
public class LogInActivityInstrumentTest extends InstrumentationTestCase{

    LogInActivity logInActivity;

    @Override
    protected void setUp() throws Exception{
        super.setUp();
        logInActivity = new LogInActivity();
    }

    public void testUsernameTextViewNullTest(){
        EditText text = (EditText) logInActivity.findViewById(R.id.logInUsernameField);
        assertNotNull(text);
    }


    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
