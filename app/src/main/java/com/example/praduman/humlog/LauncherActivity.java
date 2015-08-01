package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;


public class LauncherActivity extends ActionBarActivity {

    private Intent logInActivityIntent;
    private Intent homeActivityIntent;
    HumLogController humLogController;
    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initializeParse();
        humLogController = new HumLogController();
        currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            startLogInActivity();
        }
        else {
            startHomeActivity();
        }
    }

    /**
     * Initializing parse objects
     */
    private void initializeParse(){
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "LrAF8blaE0GR19ffsr78rHKEH50QBcnZFDSuj9BW", "2UBZlOgM78UNj7AcxcArmuOlxy5y3UstpJP1h9lb");
    }

    private void startLogInActivity(){
        logInActivityIntent = new Intent(this, LogInActivity.class);
        logInActivityIntent.putExtra("controllerObject", humLogController);
        setLogInFlags();
        startActivity(logInActivityIntent);
    }

    private void startHomeActivity(){
        homeActivityIntent = new Intent(this , HomeActivity.class);
        homeActivityIntent.putExtra("controllerObject", humLogController);
        setHomeFlags();
        startActivity(homeActivityIntent);
    }

    /**
     * This method will set the flags for the intent.
     */
    private void setLogInFlags(){
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    private void setHomeFlags(){
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

}
