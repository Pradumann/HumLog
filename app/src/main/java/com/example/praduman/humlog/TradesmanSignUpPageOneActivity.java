package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TradesmanSignUpPageOneActivity extends ActionBarActivity {

    private Button proceedButton;
    private Intent tradesmanSignUpPageTwoIntent;
    private HumLogController humLogController;
    private HumLogModel humLogModel;
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
        setContentView(R.layout.activity_tradesman_sign_up_page_one);
        humLogModel = (HumLogModel) getIntent().getSerializableExtra("modelObject");
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject(humLogModel);
        setEditTexts();
        setIntentAndButton();
    }
    private void setEditTexts(){
        firstNameEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneFirstNameField);
        lastNameEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneLastNameField);
        eMailEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneEmailField);
        passwordEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOnePasswordField);
        repeatPasswordEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneRepeatPasswordField);
    }

    private void setIntentAndButton(){
        tradesmanSignUpPageTwoIntent = new Intent (this , TradesmanSignUpPageTwoActivity.class);
        tradesmanSignUpPageTwoIntent.putExtra("modelObject" , humLogModel);
        tradesmanSignUpPageTwoIntent.putExtra("controllerObject", humLogController);
        proceedButton = (Button) findViewById(R.id.tradesmanSignUpPageOneProceedButton);
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

                                if (checkPassowrd(password, repeatPassword)) {
                                    updateIntent();
                                    startActivity(tradesmanSignUpPageTwoIntent);
                                  /**  if(humLogController.checkUser(eMail).equalsIgnoreCase("incorrect")){

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Username already exist ", Toast.LENGTH_LONG).show();
                                    }*/

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
        userType = "tradesman";

        if(firstName.trim().length()==0 || lastName.trim().length() ==0|| eMail.trim().length()==0
                || password.trim().length()==0 || repeatPassword.trim().length() ==0){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkString(String string){
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    public boolean checkEmail(String eMail){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher match = pattern.matcher(eMail);

        if(match.matches()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkPassowrd(String password , String repeatPassword){
        if(password.equalsIgnoreCase(repeatPassword)){
            return true;
        }
        else {
            return false;
        }
    }
    private void updateIntent(){
        tradesmanSignUpPageTwoIntent.putExtra("FirstName" , firstName);
        tradesmanSignUpPageTwoIntent.putExtra("LastName" , lastName);
        tradesmanSignUpPageTwoIntent.putExtra("Email" , eMail);
        tradesmanSignUpPageTwoIntent.putExtra("Password" , password);
        tradesmanSignUpPageTwoIntent.putExtra("Type" , userType);
    }
}
