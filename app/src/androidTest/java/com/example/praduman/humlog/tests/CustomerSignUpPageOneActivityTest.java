package com.example.praduman.humlog.tests;

import android.test.suitebuilder.annotation.SmallTest;

import com.example.praduman.humlog.CustomerSignUpPageOneActivity;
import com.example.praduman.humlog.LogInActivity;

import junit.framework.TestCase;

/**
 * Created by Praduman on 05/08/2015.
 */
public class CustomerSignUpPageOneActivityTest extends TestCase {

    CustomerSignUpPageOneActivity customerActivity;
    @Override
    protected void setUp () throws Exception{
        super.setUp();
        customerActivity = new CustomerSignUpPageOneActivity();
    }

    @SmallTest
    public void testCheckStringOne(){
        boolean result = customerActivity.checkString("Astring");
        assertEquals(true , result);
    }

    @SmallTest
    public void testCheckStringTwo(){
        boolean result = customerActivity.checkString("String with space");
        assertEquals(false , result);
    }

    @SmallTest
    public void testCheckEmailOne(){
        boolean result = customerActivity.checkEmail("Email@withoutDot");
        assertEquals(false , result);
    }

    @SmallTest
    public void testCheckEmailTwo (){
        boolean result = customerActivity.checkEmail("");
        assertEquals(false ,result);
    }

    @SmallTest
    public void testCheckEmailThree(){
        boolean result = customerActivity.checkEmail("email@email.com");
        assertEquals(true , result);
    }


    @SmallTest
    public void testCheckPasswordOne(){
        boolean result = customerActivity.checkPassowrd("samePassword" , "SaMePasSwoRd");
        assertEquals(true , result);
    }

    @SmallTest
    public void testCheckPasswordTwo (){
        boolean result = customerActivity.checkPassowrd("different" , "passwords");
        assertEquals(false ,result);
    }

    @SmallTest
    public void testCheckPasswordThree(){
        boolean result = customerActivity.checkPassowrd("p1" , "p1");
        assertEquals(true , result);
    }




    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }
}
