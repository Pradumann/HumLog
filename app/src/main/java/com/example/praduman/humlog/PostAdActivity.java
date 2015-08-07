package com.example.praduman.humlog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class PostAdActivity extends Activity {

    private Spinner tradesSpinner;
    private HumLogController humLogController;
    private Intent homeActivityIntent;
    private String username;
    private EditText adDetailEditText;
    private Button postAdButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);
        setWindow();
        setSpinner();
        setEditText();
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        username = getIntent().getStringExtra("username");
        setButtonAction();
    }

    private void setWindow(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
    }

    private void setSpinner(){
        tradesSpinner = (Spinner) findViewById(R.id.postadSelectTradeSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.trades, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tradesSpinner.setAdapter(arrayAdapter);
    }

    private void setEditText(){
        adDetailEditText = (EditText) findViewById(R.id.postadMultilineTextView);
    }

    private void setButtonAction(){
        postAdButton = (Button) findViewById(R.id.postadPostAdButton);
        postAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradesSpinner.getSelectedItem().toString();
                String details = adDetailEditText.getText().toString();
                if(trade.equalsIgnoreCase("Select Trade")){
                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(PostAdActivity.this);
                    errorBuilder.setMessage("Select a trade")
                            .setTitle("Error").setPositiveButton("OK" , null);
                    AlertDialog dialog = errorBuilder.create();
                    dialog.show();
                }
                else{
                    if(details.trim().length() == 0){
                        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(PostAdActivity.this);
                        errorBuilder.setMessage("Please write something about ad")
                                .setTitle("Error").setPositiveButton("OK" , null);
                        AlertDialog dialog = errorBuilder.create();
                        dialog.show();
                    }else{
                        String message = postAdvertisement(username , trade , details);
                        if(message.equalsIgnoreCase("success")){
                            Toast.makeText(PostAdActivity.this , "Your ad have been posted" , Toast.LENGTH_LONG).show();
                            startHomeActivity();
                        }else{
                            AlertDialog.Builder errorBuilder = new AlertDialog.Builder(PostAdActivity.this);
                            errorBuilder.setMessage(message)
                                    .setTitle("Error").setPositiveButton("OK", null);
                            AlertDialog dialog = errorBuilder.create();
                            dialog.show();
                        }
                    }
                }
            }
        });
    }

    private String postAdvertisement(String username , String trade , String details){
        String message = humLogController.postAd(username, trade, details);
        if(message.equalsIgnoreCase("success")){
            return "success";
        }else {
            return message;
        }
    }
    private void startHomeActivity(){
        this.finish();
    }
}
