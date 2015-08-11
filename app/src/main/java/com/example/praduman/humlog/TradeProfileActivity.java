package com.example.praduman.humlog;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class TradeProfileActivity extends ActionBarActivity {

    private HumLogController humLogController;
    private String username;
    private Spinner tradeSpinner;
    private Spinner citySpinner;
    private EditText aboutEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_profile);
     //   setWindow();
        setSpinners();
        humLogController = (HumLogController) getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        username = getIntent().getStringExtra("username");
        setEditText();
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
        tradeSpinner = (Spinner) findViewById(R.id.tradeProfileSelectTradeSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.trades, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        tradeSpinner.setAdapter(arrayAdapter);

        citySpinner = (Spinner) findViewById(R.id.tradeProfileSelectCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter2);
    }

    private void setEditText(){
        aboutEditText = (EditText) findViewById(R.id.tradeProfiledMultilineTextView);

        if(humLogController.checkTradeProfile(username)){
            aboutEditText.setText(humLogController.getAboutText(username));
        }
    }

    private void setButtonAction(){
        saveButton = (Button) findViewById(R.id.tradeProfileSaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trade = tradeSpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();
                String details = aboutEditText.getText().toString();
                if(trade.equalsIgnoreCase("Select Trade")|| city.equalsIgnoreCase("Select city")){
                    showError("Select both trade and city");
                }
                else{
                    if(details.trim().length() == 0){
                       showError("Please write something about ad");
                    }else{
                        String message = makeTradeProfile(username, trade, city, details);
                        if(message.equalsIgnoreCase("success")){
                            Toast.makeText(TradeProfileActivity.this, "Your profile has been posted", Toast.LENGTH_LONG).show();
                            startHomeActivity();
                        }else{
                            showError(message);
                        }
                    }
                }
            }
        });
    }

    private String makeTradeProfile(String username , String trade , String city ,String about){
        String message = humLogController.makeTradeProfile(username, trade, city , about);
        if(message.equalsIgnoreCase("success")){
            return "success";
        }else {
            return message;
        }
    }
    private void startHomeActivity(){
        this.finish();
    }

    private void showError(String message){

        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(TradeProfileActivity.this);
        errorBuilder.setMessage(message)
                .setTitle("Error").setPositiveButton("OK", null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }
}
