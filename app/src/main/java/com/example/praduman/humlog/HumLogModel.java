package com.example.praduman.humlog;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.LogInCallback;
import com.parse.LogInCallback;


/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogModel extends Application {

    private ParseObject customer;
    private ParseObject tradesman;

    public HumLogModel(){
        customer = new ParseObject("Customer");
        tradesman = new ParseObject("Tradesman");
    }
    @Override
    public void onCreate(){

    }

    public void createNewUser(String username, String password , String userType){

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.put("usertype" , userType);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }

    public void createCustomerTable(String username , String fName , String lName , String mobileNumber,
                            String hNo, String street , String locality , String city,
                            String postCode){
        customer.put("Username", username);
        customer.put("FirstName", fName);
        customer.put("LastName", lName);
        customer.put("MobileNumber", mobileNumber);
        customer.put("HouseNo", hNo);
        customer.put("Street", street);
        customer.put("Locality", locality);
        customer.put("City", city);
        customer.put("PostCode" , postCode);
        customer.saveInBackground();

    }

    public void createTradesmanTable(String username , String fName , String lName , String mobileNumber,
                                    String hNo, String street , String locality , String city,
                                    String postCode){
        tradesman.put("Username", username);
        tradesman.put("FirstName", fName);
        tradesman.put("LastName", lName);
        tradesman.put("MobileNumber", mobileNumber);
        tradesman.put("HouseNo", hNo);
        tradesman.put("Street", street);
        tradesman.put("Locality", locality);
        tradesman.put("City", city);
        tradesman.put("PostCode", postCode);
        tradesman.saveInBackground();
    }

    public void logIn(String username , String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(e == null){

                }
                else{
                    // show an error
                }
            }
        });
    }
}
