/**
 * This class is the home page of the application.
 * This will be launched when ever the user is logged In.
 *
 * @author: Praduman Raparia
 * @version: 1.0
 */


package com.example.praduman.humlog;


import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class HomeActivity extends ActionBarActivity {

    private HumLogController humLogController;
    private Spinner citySpinner;
    private Spinner tradesSpinner;
    private TextView firstNameTextView;
    private String username;
    private String userType;
    private String firstName;
    private String lastName;
    private Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        setDetails();
        setSpinners();
        setFirstNameText();
        setNavigationList();
        setButtonAction();
    }

    private void setDetails(){
        username = getIntent().getStringExtra("username");
        humLogController.setDetails(username);
        userType = humLogController.getUserType();
        firstName = humLogController.getFirstName();
        lastName = humLogController.getLastName();
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
        }else {
            createTradesmanNavList();
        }
    }

    /**
     * The method will create a navigation list for the
     * navigation slider of the home page.
     */
    private void createTradesmanNavList(){
        String [] navMenu = {"" , "My Interests"  ,"My Ratings" , "My Trade Details" , "Edit Profile" , "Log Out"};
        ArrayAdapter<String> navAdapter = new ArrayAdapter<String>(this, R.layout.nav_list_view, navMenu);
        ListView navList = (ListView)findViewById(R.id.navList);
        navList.setAdapter(navAdapter);
        setTradesmanListViewAction();
    }

    private void createCustomerNavList(){
        String [] navMenu = {"" , "My Interests"  ,"My Ad's" , "Post Ad" , "Edit Profile" , "Log Out"};
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
                    startEditProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("My Trade Details")) {
                    startTradeProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("My Ratings")) {
                    startMyRatingActivity();
                } else if (stringClicked.equalsIgnoreCase("My Interests")) {
                    startMyInterestActivityForTradesman();
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
                if (stringClicked.equalsIgnoreCase("Log Out")) {
                    humLogController.logOut();
                    startLogInActivity();
                } else if (stringClicked.equalsIgnoreCase("Edit Profile")) {
                    startEditProfileActivity();
                } else if (stringClicked.equalsIgnoreCase("Post Ad")) {
                    startPostAdActivity();
                } else if (stringClicked.equalsIgnoreCase("My Ad's")) {
                    startMYAdActivity();
                } else if (stringClicked.equalsIgnoreCase("My Interests")) {
                    startMyInterestActivityForCustomer();
                }
            }
        });
    }

    private void setButtonAction(){
        searchButton = (Button) findViewById(R.id.selectionbarHomeSearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userType.equalsIgnoreCase("customer")){
                    // search in the trade profiles
                }else {
                    // search in advertisement
                }
            }
        });

    }


    /**
     * This method will start the log In activity.
     */
    private void startLogInActivity(){
        Intent logInActivityIntent = new Intent(this , LogInActivity.class);
        logInActivityIntent.putExtra("controllerObject" , humLogController);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        logInActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logInActivityIntent);
    }

    private void startPostAdActivity(){
        Intent postAdActivityIntent = new Intent(this, PostAdActivity.class);
        postAdActivityIntent.putExtra("username", username);
        postAdActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(postAdActivityIntent);
    }

    private void startEditProfileActivity(){
        Intent EditProfileActivityIntent = new Intent(this , EditProfileActivity.class);
        EditProfileActivityIntent.putExtra("username" , username);
        EditProfileActivityIntent.putExtra("userType" , userType);
        EditProfileActivityIntent.putExtra("firstName" , firstName);
        EditProfileActivityIntent.putExtra("lastName", lastName);
        EditProfileActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(EditProfileActivityIntent);
    }

    private void startMYAdActivity(){
        Intent myAdActivityIntent = new Intent(this , MyAdActivity.class);
        myAdActivityIntent.putExtra("username" , username);
        myAdActivityIntent.putExtra("controllerObject" , humLogController);
        startActivity(myAdActivityIntent);
    }

    private void startMyInterestActivityForCustomer(){

    }

    private void startTradeProfileActivity(){

        Intent tradeProfileActivityIntent = new Intent(this , TradeProfileActivity.class);
        tradeProfileActivityIntent.putExtra("username", username);
        tradeProfileActivityIntent.putExtra("controllerObject", humLogController);
        startActivity(tradeProfileActivityIntent);
    }

    private void startMyRatingActivity(){
        Intent myRatingActivityIntent = new Intent(this , MyRatingActivity.class);
        myRatingActivityIntent.putExtra("username" , username);
        myRatingActivityIntent.putExtra("controllerObject" , humLogController);
        startActivity(myRatingActivityIntent);
    }

    private void startMyInterestActivityForTradesman(){


    }
}
