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
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogModel extends Application{

    private   ParseObject customer;
    private   ParseObject tradesman;
    private   ParseObject user;
    private   ParseObject advertisement;
    private   ParseObject tradeProfile;
    private   ParseObject tradesmanRelation;
    private   ParseObject customerRelation;

    public HumLogModel(){
        customer = new ParseObject("Customer");
        tradesman = new ParseObject("Tradesman");
        user = new ParseObject("User");
        advertisement = new ParseObject("Advertisement");
        tradeProfile = new ParseObject("TradeProfile");
        customerRelation = new ParseObject("CustomerRelation");
        tradesmanRelation = new ParseObject("TradesmanRelation");
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

    public String postAd(String username , String trade ,String city , String ad){
        try{
            advertisement.put("Username" , username);
            advertisement.put("Trade" , trade);
            advertisement.put("City" , city);
            advertisement.put("Ad" , ad);
            advertisement.saveInBackground();
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String makeTradeProfile(String username , String trade , String city , String about ){

        try{
            tradeProfile.put("Username" , username);
            tradeProfile.put("Trade" , trade);
            tradeProfile.put("City" , city);
            tradeProfile.put("About" , about);
            tradeProfile.put("Score" , 50);
            tradeProfile.put("Ratings" , 0);
            tradeProfile.put("Jobs" , 0);
            tradeProfile.saveInBackground();
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String checkUser( String username) {

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

    public String getLastName(String username , String userType){
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
    }


    public String getPassword(String username ){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("username", username);
        try{
            ParseObject object = query.getFirst();
            return object.getString("password");
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String getHouseNumber(String username , String userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("HouseNo");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("HouseNo");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String getMobileNumber(String username , String userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("MobileNumber");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("MobileNumber");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String getStreet(String username , String userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("Street");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("Street");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String getLocality(String username , String  userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("Locality");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("Locality");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String getPostCode(String username , String userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("PostCode");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("PostCode");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String getCity(String username , String userType){

        if(userType.equalsIgnoreCase("customer")){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("City");
            }catch (Exception e){
                return e.getMessage();
            }

        }
        else{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            try{
                ParseObject object = query.getFirst();
                return object.getString("City");
            }catch (Exception e){
                return e.getMessage();
            }
        }
    }

    public String setNewDataInCustomerTable(String username , String fName , String lName,
                                            String mobileNumber , String hNo
    , String street , String locality , String city , String postCode){
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            object.put("FirstName", fName);
            object.put("LastName", lName);
            object.put("MobileNumber", mobileNumber);
            object.put("HouseNo", hNo);
            object.put("Street", street);
            object.put("Locality", locality);
            object.put("City", city);
            object.put("PostCode", postCode);
            object.saveInBackground();
            return "success";

        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String setNewDataInTradesman(String username , String fName , String lName ,
                                        String mobileNumber , String hNo , String street ,
                                        String locality , String city , String postCode){

        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Tradesman");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            object.put("FirstName", fName);
            object.put("LastName", lName);
            object.put("MobileNumber", mobileNumber);
            object.put("HouseNo", hNo);
            object.put("Street", street);
            object.put("Locality", locality);
            object.put("City", city);
            object.put("PostCode", postCode);
            object.saveInBackground();
            return "success";

        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String setNewPassword(String username , String password){

        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
            query.whereEqualTo("username", username);
            ParseObject object = query.getFirst();
            object.put("password", password);
            object.saveInBackground();

            ParseQuery<ParseUser> userQuery = ParseQuery.getQuery("_User");
            userQuery.whereEqualTo("username", username);
            ParseUser user = userQuery.getFirst();
            user.put("password" , password);
            user.saveInBackground();
            return "success";

        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<String> getTradeList(String username){
        List<String> tradeList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("Username", username);
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                tradeList.add(i , objectList.get(i).getString("Trade"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting trade list from advertisement");
        }
        return tradeList;
    }

    public List<String> getCityList(String username){
        List<String> cityList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("Username", username);
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                cityList.add(i , objectList.get(i).getString("City"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting city list from advertisement");
        }
        return cityList;
    }

    public List<String> getAdList(String username){
        List<String> adList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("Username", username);
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                adList.add(i , objectList.get(i).getString("Ad"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting ad list from advertisement");
        }
        return adList;
    }

    public void deleteAdvertisement(String username , int position){

        List<String> adList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("Username", username);
            List<ParseObject> objectList = query.find();
            ParseObject object = objectList.get(position);
            object.delete();
            object.saveInBackground();
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in deleting from advertisement");
        }
    }

    public boolean checkProfiles(String username){

        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            return true;

        }catch (Exception e){
            return false;
        }
    }

    public boolean checkAds(String username){

        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            return true;

        }catch (Exception e){
            return false;
        }
    }


    public String getAboutText(String username){
       try {
           ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
           query.whereEqualTo("Username", username);
           ParseObject object = query.getFirst();
           return object.getString("About");
       }catch (Exception e){
            Log.d("HumLogModel" , "There is some problem in returning about data of tradesman ");
           return e.getMessage();
       }

    }
    public String updateTradeProfile(String username , String trade , String city , String about){

        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            object.put("Trade", trade);
            object.put("City", city);
            object.put("About", about);
            object.saveInBackground();
            return "success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String getRatings(String username){
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            return object.getInt("Ratings")+"";
        }catch (Exception e){
           return e.getMessage();
        }
    }

    public String getJobsDone(String username){

        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            return object.getInt("Jobs")+"";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String getScore(String username){

        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username", username);
            ParseObject object = query.getFirst();
            return object.getInt("Score")+"";
        }catch (Exception e){
            return e.getMessage();
        }
    }



    public List<String> getAdvertisementDetailsList(String city , String trade){
        List<String> detailList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("City", city);
            query.whereEqualTo("Trade" , trade);
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                detailList.add(i , objectList.get(i).getString("Ad"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting ad list from advertisement");
        }
        return detailList;
    }

    public List<String> getAdUsernameList(String city , String trade){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Advertisement");
            query.whereEqualTo("City", city);
            query.whereEqualTo("Trade" , trade);
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                usernameList.add(i , objectList.get(i).getString("Username"));

            }
            return usernameList;
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting ad list from advertisement");
            return usernameList;
        }
    }

    public List<String> getTradeUsernameList (String city , String trade){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("City" , city);
            query.whereEqualTo("Trade", trade);
            query.orderByAscending("Score");
            List<ParseObject> objectList = query.find();

            for(int i=0; i<objectList.size(); i++){
                usernameList.add(i , objectList.get(i).getString("Username"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error while finding out trade posts: "+e.getMessage());
        }

        return usernameList;
    }


    public List<String> getTradeDetailsList(String city , String trade){
        List<String> detailList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("City", city);
            query.whereEqualTo("Trade", trade);
            query.orderByAscending("Score");
            List<ParseObject> objectList = query.find();

            for(int i =0; i<objectList.size(); i++){

                detailList.add(i , objectList.get(i).getString("About"));
            }
        }catch (Exception e){
            Log.d("HumLogModel" , "Error in getting ad list from advertisement");
        }
        return detailList;
    }

    public void setCustomerRelation(String username , String otherUsername , String city , String trade){

        customerRelation.put("Username" , username);
        customerRelation.put("otherUsername" , otherUsername);
        customerRelation.put("City" , city);
        customerRelation.put("Trade" , trade);
        customerRelation.saveInBackground();
    }
    public void setTradesmanRelation(String username , String otherUsername , String city , String trade){

        tradesmanRelation.put("Username" , username);
        tradesmanRelation.put("otherUsername" , otherUsername);
        tradesmanRelation.put("City" , city);
        tradesmanRelation.put("Trade" , trade);
        tradesmanRelation.saveInBackground();
    }


    public int getRatingInt(String username){
        int rating;
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username" , username);
            ParseObject object = query.getFirst();
            rating = object.getInt("Ratings");
        }catch (Exception e){
            rating =0;
        }
        return rating;
    }

    public List<String> getCustomerRelationList(String username){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("CustomerRelation");
            query.whereEqualTo("Username" , username);
            List<ParseObject> objectList = query.find();
            for(int i=0; i<objectList.size(); i++){
                usernameList.add(i , objectList.get(i).getString("otherUsername"));
            }
            return usernameList;
        }catch (Exception e){
            return usernameList;
        }
    }
    public List<String> getCustomerRelationListForTradesman(String username){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("CustomerRelation");
            query.whereEqualTo("otherUsername" , username);
            List<ParseObject> objectList = query.find();
            for(int i=0; i<objectList.size(); i++){
                usernameList.add(i , objectList.get(i).getString("Username"));
            }
            return usernameList;
        }catch (Exception e){
            return usernameList;
        }
    }

    public List<String> getTradesmanRelationList(String username){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradesmanRelation");
            query.whereEqualTo("otherUsername" , username);
            List<ParseObject> objectList = query.find();
            for(int i=0; i<objectList.size(); i++){
                usernameList.add(i , objectList.get(i).getString("Username"));
            }
            return usernameList;
        }catch (Exception e){
            return usernameList;
        }
    }

    public List<String> getTradesmanRelationListForTradesman(String username){
        List<String> usernameList = new ArrayList<String>();
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradesmanRelation");
            query.whereEqualTo("Username" , username);
            List<ParseObject> objectList = query.find();
            for(int i=0; i<objectList.size(); i++){
                usernameList.add(i , objectList.get(i).getString("otherUsername"));
            }
            return usernameList;
        }catch (Exception e){
            return usernameList;
        }
    }

    public void updateScoreCard(String username , int rating , int jobs , int score){
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradeProfile");
            query.whereEqualTo("Username" , username);
            ParseObject object = query.getFirst();
            object.put("Ratings" , rating);
            object.put("Jobs" , jobs);
            object.put("Score" , score);
            object.saveInBackground();
            Log.d("Score card updated" , " " + rating  + " " + jobs + " " + score);
        }catch (Exception e){
            Log.d("Updating score card" , e.getMessage());
        }
    }

    public void deleteCustomerRelation(String username , String otherUsername , int position){
        try{
            ParseQuery<ParseObject> query = ParseQuery.getQuery("CustomerRelation");
            query.whereEqualTo("Username" , username);
            query.whereEqualTo("otherUsername" , otherUsername);
            List<ParseObject> objectList = query.find();
            ParseObject object = objectList.get(position);
            object.delete();
            object.saveInBackground();
        }catch (Exception e){
            Log.d("Deleting Customer R" , e.getMessage());
        }
    }

    public void deleteTradesmanRelation(String username , String otherUsername , int position){
        try{

            ParseQuery<ParseObject> query = ParseQuery.getQuery("TradesmanRelation");
            query.whereEqualTo("Username" , username);
            query.whereEqualTo("otherUsername" , otherUsername);
            List<ParseObject> objectList = query.find();
            ParseObject object = objectList.get(position);
            object.delete();
            object.saveInBackground();
        }catch (Exception e){
            Log.d("Deleting Tradesman R" , e.getMessage());
        }
    }


}
