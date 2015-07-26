package com.example.praduman.humlog;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class LogInActivity extends ActionBarActivity {

    private Button signUpButton;
    private Button logInButton;
    private Intent signUpChoiceIntent;
    private HumLogController humLogController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        setIntentAndButton();
    }

    private void setIntentAndButton(){
        signUpChoiceIntent = new Intent (this , SignUpChoiceActivity.class);
        signUpChoiceIntent.putExtra("controllerObject", humLogController);
        logInButton = (Button) findViewById(R.id.logInSignInButton);
        signUpButton= (Button) findViewById(R.id.logInSignUpButton);
        setActionListeners();
    }

    private void setActionListeners(){
        logInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "hope this will work" , Toast.LENGTH_LONG).show();
            }
        });

        signUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(signUpChoiceIntent);
            }
        });
    }

}
