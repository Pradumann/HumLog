/**
 * This class is the home page of the application.
 * This will be launched when ever the user is logged In.
 *
 * @author: Praduman Raparia
 * @version: 1.0
 */


package com.example.praduman.humlog;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;


public class HomeActivity extends ActionBarActivity {

    private HumLogController humLogController;
    private Intent logInActivityIntent;
    private Intent postAdActivityIntent;
    private Spinner citySpinner;
    private Spinner tradesSpinner;
    private TextView firstNameTextView;
    private String username;
    private String userType;
    private String firstName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        username = getIntent().getStringExtra("username");
        setDetails();
        setSpinners();
        setFirstNameText();
        setNavigationList();
    }

    private void setDetails(){
        humLogController.setDetails(username);
        userType = humLogController.getUserType();
        firstName = humLogController.getFirstName();
    }

    private void setFirstNameText(){
        firstNameTextView = (TextView) findViewById(R.id.userbarFirstNameText);
        firstNameTextView.setText(firstName);
    }
    /**
     * This method will set the spinners of the home page.
     * One spinner will be city spinner and other is trade spinner.
     */
    private void setSpinners(){

        citySpinner = (Spinner) findViewById(R.id.selectionbarHomeCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter);

        tradesSpinner = (Spinner) findViewById(R.id.selectionbarHomeTradesSpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this , R.array.trades, android.R.layout.simple_list_item_1);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tradesSpinner.setAdapter(arrayAdapter2);

    }

    private void setNavigationList(){
        if(userType.equalsIgnoreCase("customer")){
            createCustomerNavList();
        }else{
            createTradesmanNavList();
        }


    }

    /**
     * The method will create a navigation list for the
     * navigation slider of the home page.
     */
    private void createTradesmanNavList(){
        String [] navMenu = {"" , "Home"  ,"Edit Profile" , "Edit Trades" , "Ratings" , "Log Out"};
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_view, navMenu);
        ListView navList = (ListView)findViewById(R.id.navList);
        navList.setAdapter(navAdapter);
        setTradesmanListViewAction();
    }

    private void createCustomerNavList(){
        String [] navMenu = {"" , "Home"  ,"Edit Profile" , "Post Ad" , "Ratings" , "Log Out"};
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_view, navMenu);
        ListView navList = (ListView)findViewById(R.id.navList);
        navList.setAdapter(navAdapter);
        setCustomerListViewAction();
    }

    /**
     * This method have action listeners for all of the
     * items in the navigation list
     */
    private void setTradesmanListViewAction(){
        ListView navList = (ListView) findViewById(R.id.navList);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String stringClicked = textView.getText().toString();
                if (stringClicked.equalsIgnoreCase("Log Out")) {
                    humLogController.logOut();
                    startLogInActivity();
                } else if (stringClicked.equalsIgnoreCase("Edit Profile")) {
                    // do something
                } else if (stringClicked.equalsIgnoreCase("Edit Trades")) {
                    // do something
                } else if (stringClicked.equalsIgnoreCase("Ratings")) {
                    // do something
                } else {
                    // close list
                }
            }
        });
    }

    /**
     * This method have the action listeners for all the
     * items in customer navigation list
     */
    private void setCustomerListViewAction(){

        ListView navList = (ListView) findViewById(R.id.navList);
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String stringClicked = textView.getText().toString();
                if(stringClicked.equalsIgnoreCase("Log Out")){
                    humLogController.logOut();
                    startLogInActivity();
                }
                else if (stringClicked.equalsIgnoreCase("Edit Profile")){
                    // do something
                }
                else if(stringClicked.equalsIgnoreCase("Post Ad")){
                    startPostAdActivity();
                }
                else if (stringClicked.equalsIgnoreCase("Ratings")){
                    // do something
                }
                else{
                    // close list
                }
            }
        });
    }


    /**
     * This method will start the log In activity.
     */
    private void startLogInActivity(){
        logInActivityIntent = new Intent(this , LogInActivity.class);
        logInActivityIntent.putExtra("controllerObject" , humLogController);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInActivityIntent);
    }

    private void startPostAdActivity(){
        postAdActivityIntent = new Intent(this, PostAdActivity.class);
        postAdActivityIntent.putExtra("username" , username);
        postAdActivityIntent.putExtra("controllerObject" , humLogController);
        startActivity(postAdActivityIntent);
    }
}
