package com.example.praduman.humlog;

import android.app.AlertDialog;
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
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        setEditTexts();
        setIntentAndButton();
    }

    /**
     * This method will set the edit texts.
     */
    private void setEditTexts(){
        firstNameEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneFirstNameField);
        lastNameEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneLastNameField);
        eMailEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneEmailField);
        passwordEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOnePasswordField);
        repeatPasswordEditText = (EditText) findViewById(R.id.tradesmanSignUpPageOneRepeatPasswordField);
    }

    /**
     * This method will set the intents and action listener button.
     */
    private void setIntentAndButton(){
        tradesmanSignUpPageTwoIntent = new Intent (this , TradesmanSignUpPageTwoActivity.class);
        tradesmanSignUpPageTwoIntent.putExtra("controllerObject", humLogController);
        proceedButton = (Button) findViewById(R.id.tradesmanSignUpPageOneProceedButton);
        setActionListener();
    }

    /**
     * This method will set the action listener for proceed button.
     */
    private void  setActionListener(){
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setAndCheckFields()) {

                    if (checkString(firstName)) {

                        if (checkString(lastName)) {

                            if (checkEmail(eMail)) {
                                if(checkUser(eMail)) {
                                    if (checkPassowrd(password, repeatPassword)) {
                                        updateIntent();
                                        startActivity(tradesmanSignUpPageTwoIntent);
                                    } else {
                                        showError("Password do not match");
                                    }
                                }else {
                                    showError("Username already exist");
                                }

                            } else {
                                showError("Invalid Email. Fill Email properly ");
                            }

                        } else {
                            showError("Invalid characters. Fill Last name properly ");
                        }

                    } else {
                        showError("Invalid characters. Fill First name properly ");
                    }
                } else {
                    showError(" Fill all the fields ");
                }


            }
        });
    }

    /**
     * This method will check if all the fields are filled or not.
     * @return boolean (whether fields are filled or not)
     */
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

    /**
     * This method will check if string have only characters with spaces or not.
     * @param string
     * @return boolean (whether string have characters or not)
     */
    public boolean checkString(String string){
        Pattern pattern = Pattern.compile("[a-zA-Z\\s]+");
        Matcher match = pattern.matcher(string);

        if(match.matches()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method will check if the string have valid email account or not. The regex for this method
     * is taken from stackoverflow with link http://stackoverflow.com/questions/8204680/java-regex-email.
     * @param eMail
     * @return
     */
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


    /**
     * This method will check if the user with same username already exist or not.
     * If the user exist it will return false and if user do not exist it will return true, so that the statement
     * calling this method can proceed.
     * @param username
     * @return boolean (whether username exist or not)
     */
    private  boolean checkUser(String username){
        if(humLogController.checkUser(username).equalsIgnoreCase("correct")){
            return false;
        }else {
            return true;
        }
    }

    /**
     * This method will check whether the passwords are same or not.
     * @param password
     * @param repeatPassword
     * @return boolean (whether passwords same or not)
     */
    public boolean checkPassowrd(String password , String repeatPassword){
        if(password.equalsIgnoreCase(repeatPassword)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method will update the intent before starting the new activity.
     */
    private void updateIntent(){
        tradesmanSignUpPageTwoIntent.putExtra("FirstName" , firstName);
        tradesmanSignUpPageTwoIntent.putExtra("LastName" , lastName);
        tradesmanSignUpPageTwoIntent.putExtra("Email" , eMail);
        tradesmanSignUpPageTwoIntent.putExtra("Password" , password);
        tradesmanSignUpPageTwoIntent.putExtra("Type" , userType);
    }

    /**
     * This method will show the error dialogue with message passed in it. The method is taken from stackoverflow
     * with the link http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android.
     * @param message
     */
    private void showError(String message){
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(TradesmanSignUpPageOneActivity.this);
        errorBuilder.setMessage(message)
                .setTitle("Error").setPositiveButton("OK", null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }
}
