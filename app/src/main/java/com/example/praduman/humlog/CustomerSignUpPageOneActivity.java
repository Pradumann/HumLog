package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerSignUpPageOneActivity extends ActionBarActivity {

    private Button proceedButton;
    private Intent customerSignUpPageTwoIntent;
    private HumLogController humLogController;
    private String firstName;
    private String lastName;
    private String eMail;
    private String password;
    private String repeatPassword;
    private String userType;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText eMailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_page_one);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        setEditTexts();
        setIntentAndButton();
    }

    private void setEditTexts(){
        firstNameEditText = (EditText) findViewById(R.id.customerSignUpPageOneFirstNameField);
        lastNameEditText = (EditText) findViewById(R.id.customerSignUpPageOneLastNameField);
        eMailEditText = (EditText) findViewById(R.id.customerSignUpPageOneEmailField);
        passwordEditText = (EditText) findViewById(R.id.customerSignUpPageOnePasswordField);
        repeatPasswordEditText = (EditText) findViewById(R.id.customerSignUpPageOneRepeatPasswordField);
    }
    private void setIntentAndButton(){
        customerSignUpPageTwoIntent = new Intent (this , CustomerSignUpPageTwoActivity.class);
        customerSignUpPageTwoIntent.putExtra("controllerObject", humLogController);
        proceedButton = (Button) findViewById(R.id.customerSignUpPageOneProceedButton);
        setActionListener();
    }

    private void  setActionListener(){
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setAndCheckFields()) {

                    if (checkString(firstName)) {

                        if (checkString(lastName)) {

                            if (checkEmail(eMail)) {

                                if (checkPassowrd(password , repeatPassword)) {
                                    updateIntent();
                                    startActivity(customerSignUpPageTwoIntent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password do not match. (password is not case sensitive) ", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Email. Fill Email properly ", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid characters. Fill Last name properly ", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid characters. Fill First name properly ", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), " Fill all the fields ", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private boolean setAndCheckFields(){
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        eMail = eMailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        repeatPassword = repeatPasswordEditText.getText().toString();
        userType = "customer";

        if(firstName.trim().length()==0 || lastName.trim().length() ==0|| eMail.trim().length()==0
                || password.trim().length()==0 || repeatPassword.trim().length() ==0){
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

    private boolean checkEmail(String eMail){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher match = pattern.matcher(eMail);

        if(match.matches()){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkPassowrd(String password , String repeatPassword){
        if(password.equalsIgnoreCase(repeatPassword)){
            return true;
        }
        else {
            return false;
        }
    }
    private void updateIntent(){
        customerSignUpPageTwoIntent.putExtra("FirstName" , firstName);
        customerSignUpPageTwoIntent.putExtra("LastName" , lastName);
        customerSignUpPageTwoIntent.putExtra("Email" , eMail);
        customerSignUpPageTwoIntent.putExtra("Password" , password);
        customerSignUpPageTwoIntent.putExtra("Type" , userType);
    }

}
