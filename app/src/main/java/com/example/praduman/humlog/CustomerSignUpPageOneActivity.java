/**
 * This class sets the initial data of the
 * user (which is a customer).
 *
 * @author Praduman Raparia
 * @version 1.0
 * @date 2/8/2015
 */


package com.example.praduman.humlog;

import android.app.AlertDialog;
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
        humLogController.setModelObject();
        setEditTexts();
        setIntentAndButton();
    }

    /**
     * This method will set the Edit texts.
     */
    private void setEditTexts(){
        firstNameEditText = (EditText) findViewById(R.id.customerSignUpPageOneFirstNameField);
        lastNameEditText = (EditText) findViewById(R.id.customerSignUpPageOneLastNameField);
        eMailEditText = (EditText) findViewById(R.id.customerSignUpPageOneEmailField);
        passwordEditText = (EditText) findViewById(R.id.customerSignUpPageOnePasswordField);
        repeatPasswordEditText = (EditText) findViewById(R.id.customerSignUpPageOneRepeatPasswordField);
    }

    /**
     * This method will set the intent for customer signup
     * page two intent and proceed button.
     */
    private void setIntentAndButton(){
        customerSignUpPageTwoIntent = new Intent (this , CustomerSignUpPageTwoActivity.class);
        customerSignUpPageTwoIntent.putExtra("controllerObject", humLogController);
        proceedButton = (Button) findViewById(R.id.customerSignUpPageOneProceedButton);
        setActionListener();
    }

    /**
     * This method will set the action listener for the button
     * which will navigate user to customer sign up page two.
     */
    private void  setActionListener(){
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setAndCheckFields()) {

                    if (checkString(firstName)) {

                        if (checkString(lastName)) {

                            if (checkEmail(eMail)) {
                                if (checkUser(eMail)) {
                                    if (checkPassowrd(password, repeatPassword)) {
                                        updateIntent();
                                        startActivity(customerSignUpPageTwoIntent);
                                    } else {
                                        showError("Passwords do not match");
                                    }
                                } else {
                                    showError("Username already exist");
                                }
                            } else {
                                showError("Invalid Email. Fill Email properly");
                            }

                        } else {
                            showError("Invalid characters. Fill Last name properly");
                        }

                    } else {
                        showError("Invalid characters. Fill First name properly");
                    }
                } else {
                    showError("Fill all the fields");
                }


            }
        });
    }

    /**
     * This method will set the fields and check they are filled or not.
     * @return boolean (whether the fields are filled or not)
     */
    private boolean setAndCheckFields(){
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        eMail = eMailEditText.getText().toString();
        password = passwordEditText.getText().toString().toLowerCase();
        repeatPassword = repeatPasswordEditText.getText().toString().toLowerCase();
        userType = "customer";

        if(firstName.trim().length()==0 || lastName.trim().length() ==0|| eMail.trim().length()==0
                || password.trim().length()==0 || repeatPassword.trim().length() ==0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * The method will check if the given string
     * contain only characters or not.
     * @param string
     * @return boolean (whether string have characters)
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
     * This method will check if the email is in proper patter or not.
     * @param eMail
     * @return boolean (whether email is proper or not)
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
     * This method will check both passwords are equal or not.
     * @param password
     * @param repeatPassword
     * @return boolean (whether passwords are same or not)
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
     * This method will put the user data with the intent.
     */
    private void updateIntent(){
        customerSignUpPageTwoIntent.putExtra("FirstName" , firstName);
        customerSignUpPageTwoIntent.putExtra("LastName" , lastName);
        customerSignUpPageTwoIntent.putExtra("Email" , eMail);
        customerSignUpPageTwoIntent.putExtra("Password" , password);
        customerSignUpPageTwoIntent.putExtra("Type" , userType);
    }

    /**
     * This method will show a error with a message provided.
     * The code is taken from stackoverflow with the following link
     * http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android.
     * @param message
     */
    private void showError(String message){
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(CustomerSignUpPageOneActivity.this);
        errorBuilder.setMessage(message)
                .setTitle("Error").setPositiveButton("OK", null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }
}
