package com.example.praduman.humlog;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;


/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogModel extends Application{


    @Override
    public void onCreate(){
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "LrAF8blaE0GR19ffsr78rHKEH50QBcnZFDSuj9BW", "2UBZlOgM78UNj7AcxcArmuOlxy5y3UstpJP1h9lb");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();


    }
}
