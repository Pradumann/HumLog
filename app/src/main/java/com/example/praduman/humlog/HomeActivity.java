package com.example.praduman.humlog;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import com.parse.Parse;
import java.io.Serializable;


public class HomeActivity extends ActionBarActivity {

    private HumLogController humLogController;
    private Intent logInActivityIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeParse();
        //To do , check if user is logged or not
        // To do , fill up the list view in layout,to this after laout formation else ready for consis.
        setContentView(R.layout.activity_home);
        humLogController = new HumLogController();
        /*****************************************************compulsory data to set initially*****************/

        //To do , have to change it and set a flag


        logInActivityIntent = new Intent(this , LogInActivity.class);
        logInActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(logInActivityIntent);
    }

    private void initializeParse(){
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "LrAF8blaE0GR19ffsr78rHKEH50QBcnZFDSuj9BW", "2UBZlOgM78UNj7AcxcArmuOlxy5y3UstpJP1h9lb");
    }
}
