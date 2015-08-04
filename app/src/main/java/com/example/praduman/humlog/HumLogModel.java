package com.example.praduman.humlog;

import android.app.AlertDialog;
import android.app.Application;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.LogInCallback;
import com.parse.LogInCallback;

import java.io.Serializable;


/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogModel extends Application {

    private   ParseObject customer;
    private   ParseObject tradesman;
    private   ParseObject user;
    private   String userSuccess ;
    private   String passwordSuccess;

    public HumLogModel(){
        customer = new ParseObject("Customer");
        tradesman = new ParseObject("Tradesman");
        user = new ParseObject("User");
    }

    public void createNewUser(String username, String password , String userType){
        user.put("username" , username);
        user.put("password" , password);
        user.put("userType" , userType);
        user.saveInBackground();
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
        customer.put("PostCode", postCode);
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

    public String checkUser(final String username) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        try
        {
            ParseObject object = query.getFirst();
            if (object == null) {
                HumLogModel.this.userSuccess = "incorrect";
            } else {
                HumLogModel.this.userSuccess = "correct";
            }
        }
        catch(Exception e)
        {

        }
         return userSuccess;
    }

    public String checkPassword(final String password , String username){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        try {
            ParseObject object = query.getFirst();
            if (password.equalsIgnoreCase(object.getString("password"))) {
                HumLogModel.this.passwordSuccess = "correct";
            } else {
                HumLogModel.this.passwordSuccess = "incorrect";
            }
        }
        catch (Exception e){

        }

        return passwordSuccess;
    }
}
