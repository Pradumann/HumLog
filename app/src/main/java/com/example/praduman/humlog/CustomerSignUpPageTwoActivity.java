/**
 * This class will sign up the new user
 * who is a customer and navigate the
 * user to his/her home activity.
 *
 * @author Praduman Raparia
 * @version 1.0
 * @date 2/8/2015
 */

package com.example.praduman.humlog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CustomerSignUpPageTwoActivity extends ActionBarActivity {

    Spinner citySpinner;
    private HumLogController humLogController;
    private String firstName;
    private String lastName;
    private String password;
    private String eMail;
    private String mobileNumber;
    private String houseNumber;
    private String street;
    private String locality;
    private String city;
    private String postCode;
    private String userType;
    private EditText mobileNumberEditText;
    private EditText houseNumberEditText;
    private EditText streetEditText;
    private EditText localityEditText;
    private EditText postCodeEditText;
    private Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up_page_two);
        humLogController = (HumLogController)getIntent().getSerializableExtra("controllerObject");
        humLogController.setModelObject();
        signUpButton = (Button) findViewById(R.id.customerSignUpPageTwoSignUpNowButton);
        setSpinner();
        setEditTexts();
        setActionListeners();
    }

    /**
     * This method will set the spinner for city selection
     * field of the sign up age two.
     */
    private void setSpinner(){
        citySpinner = (Spinner) findViewById(R.id.customerSignUpPageTwoCitySpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this , R.array.cities, android.R.layout.simple_list_item_1);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        citySpinner.setAdapter(arrayAdapter);
    }

    /**
     * This method will set the Edit texts
     * for the fields.
     */
    private void setEditTexts(){
        mobileNumberEditText = (EditText) findViewById(R.id.customerSignUpPageTwoMobileNumberField);
        houseNumberEditText = (EditText) findViewById(R.id.customerSignUpPageTwoHouseNumberField);
        streetEditText = (EditText) findViewById(R.id.customerSignUpPageTwoStreetField);
        localityEditText = (EditText) findViewById(R.id.customerSignUpPageTwoLocalityField);
        postCodeEditText = (EditText) findViewById(R.id.customerSignUpPageTwoPostCodeField);
    }

    /**
     * This method will set the action listeners
     * for the sign up button and
     * eventually navigate the user to his/her
     * home page.
     */
    private void setActionListeners(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()) {
                    if (setAndCheckFields()) {
                        if (checkString(street)) {
                            if (checkString(locality)) {
                                if (!checkCity(city)) {
                                    if (checkPostCode(postCode)) {
                                        createNewUserAndLogIn();
                                    } else {
                                        showError("Invalid Post Code. All letters should be capital");
                                    }

                                } else {
                                    showError("Select city !!!");
                                }

                            } else {
                                showError("Invalid character. Fill locality properly");
                            }
                        } else {
                            showError("Invalid characters. Fill street field properly");
                        }

                    } else {
                        showError("Fill all fields");
                    }
                }else {
                    showError("Internet connection is not available");
                }

            }
        });
    }

    /**
     * This method will set up all the initial data
     * for the user before signing up and check
     * whether they are filled properly.
     * @return boolean (whether fields are filled propely)
     */
    private boolean setAndCheckFields(){
        firstName = getIntent().getStringExtra("FirstName");
        lastName = getIntent().getStringExtra("LastName");
        eMail = getIntent().getStringExtra("Email");
        password = getIntent().getStringExtra("Password");
        userType = getIntent().getStringExtra("Type");
        mobileNumber = mobileNumberEditText.getText().toString();
        houseNumber = houseNumberEditText.getText().toString();
        street = streetEditText.getText().toString();
        locality = localityEditText.getText().toString();
        city = citySpinner.getSelectedItem().toString();
        postCode = postCodeEditText.getText().toString();

        if(mobileNumber.trim().length()==0 || houseNumber.trim().length() ==0|| street.trim().length()==0
                || locality.trim().length()==0 || city.trim().length() ==0 || postCode.trim().length()==0){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method will check whether passed string
     * have characters only or not.
     * @param string
     * @return boolean (whether string have only characetrs)
     */
    private boolean checkString(String string){
        Pattern pattern = Pattern.compile("[a-zA-Z\\s]+");
        Matcher match = pattern.matcher(string);

        if(match.matches()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method will check if
     * any of the city is selected or not.
     * @param city
     * @return boolean (city selected or not)
     */
    public boolean checkCity(String city){
       return city.equalsIgnoreCase("Select city");
    }

    /**
     * This method will check if post code is proper or not
     * according to the patter provided by UK government.
     * @param postCode
     * @return boolean (postcode is proper or not)
     */
    public boolean checkPostCode(String postCode){
        String regex = "^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postCode);
        return  matcher.matches();
    }

    /**
     * This method will create a new user and navigate the
     * user to home page.
     */
    private void createNewUserAndLogIn(){

        humLogController.setUserEssentials(eMail , password , userType , firstName , lastName
                , mobileNumber , houseNumber , street , locality ,city , postCode);
        humLogController.createNewUserAndLogIn();
         startHomeActivity();
    }

    /**
     * This method will navigate user
     * to home page.
     */
    private void startHomeActivity(){
        Intent homeActivityIntent = new Intent(this , HomeActivity.class);
        homeActivityIntent.putExtra("controllerObject" , humLogController);
        homeActivityIntent.putExtra("username" , eMail);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeActivityIntent);
    }

    /**
     * This method show the dialogue box with a message given.
     * The code is taken from stackoverflow with following link
     * http://stackoverflow.com/questions/2115758/how-to-display-alert-dialog-in-android
     * @param message
     */
    private void showError(String message){
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(CustomerSignUpPageTwoActivity.this);
        errorBuilder.setMessage(message)
                .setTitle("Error").setPositiveButton("OK", null);
        AlertDialog dialog = errorBuilder.create();
        dialog.show();
    }

    /**
     * This method will check if internet is available or not
     * This method is taken from stack overflow
     * http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     * @return boolean (whether internet connection is available or not)
     */
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
}
