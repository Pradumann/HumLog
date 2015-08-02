package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TradesmanSignUpPageTwoActivity extends ActionBarActivity {

    Spinner citySpinner;
    private HumLogController humLogController;
    private String firstName;
    private String lastName;
    private String password;
    private String eMail;
    private String mobileNumber;
    private String houseNumber;
    private String street;
    private String locality;
    private String city;
    private String postCode;
    private String userType;
    private EditText mobileNumberEditText;
    private EditText houseNumberEditText;
    private EditText streetEditText;
    private EditText localityEditText;
    private EditText postCodeEditText;
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradesman_sign_up_page_two);

        humLogController = (HumLogController)getIntent().getSerializableExtra("controllerObject");
        signUpButton = (Button) findViewById(R.id.tradesmanSignUpPageTwoSignUpNowButton);
        setSpinner();
        setEditTexts();
        setActionListeners();
    }

    private void setSpinner(){
        citySpinner = (Spinner) findViewById(R.id.tradesmanSignUpPageTwoCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter);
    }
    private void setEditTexts(){
        mobileNumberEditText = (EditText) findViewById(R.id.tradesmanSignUpPageTwoMobileNumberField);
        houseNumberEditText = (EditText) findViewById(R.id.tradesmanSignUpPageTwoHouseNumberField);
        streetEditText = (EditText) findViewById(R.id.tradesmanSignUpPageTwoStreetField);
        localityEditText = (EditText) findViewById(R.id.tradesmanSignUpPageTwoLocalityField);
        postCodeEditText = (EditText) findViewById(R.id.tradesmanSignUpPageTwoPostCodeField);
    }

    private void setActionListeners(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setAndCheckFields()){
                    if(checkString(street)){
                        if(checkString(locality)){
                            if(!checkCity(city)){
                                if(checkPostCode(postCode)){
                                    createNewUserAndLogIn();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Invalid Post Code", Toast.LENGTH_LONG).show();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext() , "Select city !!!" , Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            Toast.makeText(getApplicationContext() , "Invalid characters. Fill locality properly" , Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext() , "Invalid characters. Fill street field properly"
                                , Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(), "Fill all fields" , Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean setAndCheckFields(){
        firstName = getIntent().getStringExtra("FirstName");
        lastName = getIntent().getStringExtra("LastName");
        eMail = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");
        userType = getIntent().getStringExtra("Type");
        mobileNumber = mobileNumberEditText.getText().toString();
        houseNumber = houseNumberEditText.getText().toString();
        street = streetEditText.getText().toString();
        locality = localityEditText.getText().toString();
        city = citySpinner.getSelectedItem().toString();
        postCode = postCodeEditText.getText().toString();

        if(mobileNumber.trim().length()==0 || houseNumber.trim().length() ==0|| street.trim().length()==0
                || locality.trim().length()==0 || city.trim().length() ==0 || postCode.trim().length()==0){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkString(String string){
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean checkCity(String city){
        return city.equalsIgnoreCase("Select city");
    }

    private boolean checkPostCode(String postCode){
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postCode);
        return  matcher.matches();
    }

    private void createNewUserAndLogIn(){

        humLogController.setUserEssentials(eMail , password , userType , firstName , lastName
                , mobileNumber , houseNumber , street , locality ,city , postCode);
        humLogController.createNewUserAndLogIn();
        startHomeActivity();
    }

    /**
     * This method will navigate user
     * to home page.
     */
    private void startHomeActivity(){
        Intent homeActivityIntent = new Intent(this , HomeActivity.class);
        homeActivityIntent.putExtra("controllerObject" , humLogController);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeActivityIntent);
    }

}
