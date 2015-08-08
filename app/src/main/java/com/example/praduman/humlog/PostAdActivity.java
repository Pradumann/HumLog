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
    private Spinner citySpinner;
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
        setSpinners();
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

    private void setSpinners(){
        tradesSpinner = (Spinner) findViewById(R.id.postadSelectTradeSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.trades, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tradesSpinner.setAdapter(arrayAdapter);

        citySpinner = (Spinner) findViewById(R.id.postadSelectCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter2);
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
                String city = citySpinner.getSelectedItem().toString();
                String details = adDetailEditText.getText().toString();
                if(trade.equalsIgnoreCase("Select Trade")|| city.equalsIgnoreCase("Select city")){
                    AlertDialog.Builder errorBuilder = new AlertDialog.Builder(PostAdActivity.this);
                    errorBuilder.setMessage("Select both trade and city")
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
                        String message = postAdvertisement(username , trade , city , details);
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

    private String postAdvertisement(String username , String trade , String city ,String details){
        String message = humLogController.postAd(username, trade, city , details);
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
