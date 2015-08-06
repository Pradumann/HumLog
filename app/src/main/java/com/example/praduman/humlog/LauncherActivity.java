package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseUser;

import java.io.Serializable;


public class LauncherActivity extends ActionBarActivity implements Serializable{

    private Intent logInActivityIntent;
    private Intent homeActivityIntent;
    private HumLogController humLogController;
    private HumLogModel humLogModel;
    private ParseUser currentUser;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initializeParse();
        currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            humLogModel = new HumLogModel();
            humLogController = new HumLogController();
            startLogInActivity();
        }
        else {
            username = currentUser.getUsername();
            humLogModel = new HumLogModel();
            humLogController = new HumLogController();
            startHomeActivity();
        }
    }

    /**
     * Initializing parse datastore.
     */
    private void initializeParse(){
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "LrAF8blaE0GR19ffsr78rHKEH50QBcnZFDSuj9BW", "2UBZlOgM78UNj7AcxcArmuOlxy5y3UstpJP1h9lb");
    }

    /**
     * This method will fill the intent for LogIn activity, set flag
     * and start the LogInActivity.
     */
    private void startLogInActivity(){
        logInActivityIntent = new Intent(this, LogInActivity.class);
        logInActivityIntent.putExtra("modelObject" , humLogModel);
        logInActivityIntent.putExtra("controllerObject", humLogController);
        setLogInFlags();
        startActivity(logInActivityIntent);
    }

    /**
     * This method will fill the intent for Home activity, set flags
     * and start the Home activity.
     */
    private void startHomeActivity(){
        homeActivityIntent = new Intent(this , HomeActivity.class);
        homeActivityIntent.putExtra("modelObject" , humLogModel);
        homeActivityIntent.putExtra("controllerObject", humLogController);
        homeActivityIntent.putExtra("username" , username);
        setHomeFlags();
        startActivity(homeActivityIntent);
    }

    /**
     * This method will set the flags for log in activity intent intent.
     */
    private void setLogInFlags(){
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    /**
     * This method will set flags for Home activity intent.
     */
    private void setHomeFlags(){
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
}
