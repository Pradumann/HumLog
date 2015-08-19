package com.example.praduman.humlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;


public class LogInActivity extends ActionBarActivity {

    private Button signUpButton;
    private Button logInButton;
    private Intent signUpChoiceIntent;
    private Intent homeActivityIntent;
    private String username;
    private String password;
    private EditText usernameTextField;
    private EditText passwordTextField;
    private HumLogController humLogController;
    private String error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        setIntentAndButton();
    }

    /**
     * This method will set the intents and buttons
     */
    private void setIntentAndButton(){
        signUpChoiceIntent = new Intent (this , SignUpChoiceActivity.class);
        signUpChoiceIntent.putExtra("controllerObject", humLogController);
        homeActivityIntent = new Intent(this , HomeActivity.class);
        homeActivityIntent.putExtra("controllerObject", humLogController);
        logInButton = (Button) findViewById(R.id.logInSignInButton);
        signUpButton= (Button) findViewById(R.id.logInSignUpButton);
        setActionListeners();
    }

    /**
     * This method will set the action listeners for the
     * buttons.
     */
    private void setActionListeners(){
        logInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setAndCheckFields()){
                   if(isNetworkAvailable()) {
                       error = humLogController.checkUser(username, password);
                       if (error.equalsIgnoreCase("success")) {
                           humLogController.logIn(username, password);
                           homeActivityIntent.putExtra("username", username);
                           setFlags();
                           startActivity(homeActivityIntent);
                       } else {
                           showError(error);
                       }
                   }else {
                       showError("Internet Connection not available");
                   }

                }
                else {
                    showError("Fill all fields");
                }
            }
        });

        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpChoiceIntent);
            }
        });
    }

    /**
     * This method will set the variables username and password
     * and check them whether they are filled or not.
     * @return boolean (fields filled or not)
     */
    private boolean setAndCheckFields(){
        usernameTextField = (EditText) findViewById(R.id.logInUsernameField);
        passwordTextField = (EditText) findViewById(R.id.logInPasswordField);
        username = usernameTextField.getText().toString();
        password = passwordTextField.getText().toString();
        return checkFields(username , password);
    }

    /**
     * This method will check if username and password are filled or not.
     * @param username
     * @param password
     * @return boolean (fields filled or not)
     */
    public boolean checkFields(String username , String password){
        if(username.trim().length() == 0 || password.trim().length() == 0){
            return false;
        }
        else{
            return true;
        }
    }

    private void showError(String error){
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(LogInActivity.this);
        errorBuilder.setMessage(error)
                .setTitle("Error").setPositiveButton("OK" , null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }

    private boolean isNetworkAvailable() {

        Context context = this;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if(activeNetwork != null && activeNetwork.isConnected()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method will set the flags for homeActivityIntent.
     */
    private void setFlags(){
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

}
