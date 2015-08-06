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
public class HumLogModel extends Application{

    private   ParseObject customer;
    private   ParseObject tradesman;
    private   ParseObject user;

    public HumLogModel(){
        customer = new ParseObject("Customer");
        tradesman = new ParseObject("Tradesman");
        user = new ParseObject("User");
    }

    public void createNewUser(String username, String password , String userType){
        user.put("username" , username);
        user.put("password", password);
        user.put("userType", userType);
        user.saveInBackground();

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
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
                return  "incorrect";
            } else {
                return  "correct";
            }
        }
        catch(Exception e)
        {
                return e.getMessage();
        }
    }

    public String checkPassword(final String password , String username){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        try {
            ParseObject object = query.getFirst();
            if (password.equalsIgnoreCase(object.getString("password"))) {
                return "correct";
            } else {
                return "incorrect";
            }
        }
        catch (Exception e){
                return e.getMessage();
        }
    }

    public void logIn(String username , String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    public String getUserType(String username){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        try {
            ParseObject object = query.getFirst();
            return object.getString("userType");
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

    /**
     * This method will return the first name on the
     * basis of username and customer type.
     * @param username
     * @param userType
     * @return
     */
    public String getFirstName(String username , String userType){
        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("FirstName");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("FirstName");
            }catch (Exception e){
                return e.getMessage();
            }
        }

    }

   /** public String getLastName(String username , String userType){
        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("LastName");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("LastName");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }*/

}
