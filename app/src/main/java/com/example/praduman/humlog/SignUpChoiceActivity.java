package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;


public class SignUpChoiceActivity extends ActionBarActivity {

    private Button customerSignUpButton;
    private Intent customerChoiceIntent;
    private Button tradesmanSignUpButton;
    private Intent tradesmanChoiceIntent;
    private HumLogController humLogController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_choice);
        humLogController = (HumLogController)getIntent().getSerializableExtra("controllerObject");
        setIntentAndButton();
    }

    /**
     * This method will set the intent for the
     * customer choice, tradesman choice intent
     * and the buttons.
     */
    private void setIntentAndButton(){

        customerChoiceIntent = new Intent (this , CustomerSignUpPageOneActivity.class);
        customerChoiceIntent.putExtra("controllerObject" , humLogController );

        tradesmanChoiceIntent = new Intent(this, TradesmanSignUpPageOneActivity.class);
        tradesmanChoiceIntent.putExtra("controllerObject", humLogController);

        customerSignUpButton = (Button) findViewById(R.id.signUpChoiceCustomerSignUpButton);
        tradesmanSignUpButton = (Button) findViewById(R.id.signUpChoiceTradesmanSignUpButton);
        setActionListener();
    }

    /**
     * This method will set the action listeners for the buttons.
     */
    private void setActionListener(){
        customerSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(customerChoiceIntent);
            }
        });

        tradesmanSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(tradesmanChoiceIntent);
            }
        });
    }

}
