package com.example.praduman.humlog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class CustomerSignUpPageTwoActivity extends ActionBarActivity {

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
        setContentView(R.layout.activity_customer_sign_up_page_two);

        humLogController = (HumLogController)getIntent().getSerializableExtra("controllerObject");
        signUpButton = (Button) findViewById(R.id.customerSignUpPageTwoSignUpNowButton);
        setSpinner();
        setEditTexts();
        setActionListeners();
    }
    private void setSpinner(){
        citySpinner = (Spinner) findViewById(R.id.customerSignUpPageTwoCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter);
    }

    private void setEditTexts(){
        mobileNumberEditText = (EditText) findViewById(R.id.customerSignUpPageTwoMobileNumberField);
        houseNumberEditText = (EditText) findViewById(R.id.customerSignUpPageTwoHouseNumberField);
        streetEditText = (EditText) findViewById(R.id.customerSignUpPageTwoStreetField);
        localityEditText = (EditText) findViewById(R.id.customerSignUpPageTwoLocalityField);
        postCodeEditText = (EditText) findViewById(R.id.customerSignUpPageTwoPostCodeField);
    }

    private void setActionListeners(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserEssentials();
                createNewUser();
            }
        });
    }

    private void setUserEssentials(){
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
    }

    private void createNewUser(){

        humLogController.setUserEssentials(eMail , password , userType);
        humLogController.createNewUser();
    }
}
