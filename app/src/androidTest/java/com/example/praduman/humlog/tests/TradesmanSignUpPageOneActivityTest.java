package com.example.praduman.humlog.tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.praduman.humlog.CustomerSignUpPageOneActivity;
import com.example.praduman.humlog.TradesmanSignUpPageOneActivity;

import junit.framework.TestCase;

/**
 * Created by Praduman on 05/08/2015.
 */
public class TradesmanSignUpPageOneActivityTest extends TestCase{

    TradesmanSignUpPageOneActivity tradesmanActivity;
    @Override
    protected void setUp () throws Exception{
        super.setUp();
        tradesmanActivity = new TradesmanSignUpPageOneActivity();
    }

    @SmallTest
    public void testCheckStringOne(){
        boolean result = tradesmanActivity.checkString("Astring");
        assertEquals(true , result);
    }

    @SmallTest
    public void testCheckStringTwo(){
        boolean result = tradesmanActivity.checkString("String with space");
        assertEquals(false , result);
    }

    @SmallTest
    public void testCheckEmailOne(){
        boolean result = tradesmanActivity.checkEmail("Email@withoutDot");
        assertEquals(false , result);
    }

    @SmallTest
    public void testCheckEmailTwo (){
        boolean result = tradesmanActivity.checkEmail("");
        assertEquals(false ,result);
    }

    @SmallTest
    public void testCheckEmailThree(){
        boolean result = tradesmanActivity.checkEmail("email@email.com");
        assertEquals(true , result);
    }


    @SmallTest
    public void testCheckPasswordOne(){
        boolean result = tradesmanActivity.checkPassowrd("samePassword" , "SaMePasSwoRd");
        assertEquals(true , result);
    }

    @SmallTest
    public void testCheckPasswordTwo (){
        boolean result = tradesmanActivity.checkPassowrd("different" , "passwords");
        assertEquals(false ,result);
    }

    @SmallTest
    public void testCheckPasswordThree(){
        boolean result = tradesmanActivity.checkPassowrd("p1" , "p1");
        assertEquals(true , result);
    }




    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
