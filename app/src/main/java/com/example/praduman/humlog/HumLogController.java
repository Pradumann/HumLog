package com.example.praduman.humlog;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogController implements Serializable {
    private transient String username;
    private transient String password;
    public transient String userType;
    private transient String firstName;
    private transient String lastName;
    private transient String mobileNumber;
    private transient String houseNumber;
    private transient String street;
    private transient String locality;
    private transient String city;
    private transient String postCode;
    private transient String score;
    private transient String jobsDone;
    private transient String ratings;
    private transient HumLogModel humLogModel;


    public void createNewUserAndLogIn(){
        humLogModel.createNewUser(getUsername(), getPassword(), getUserType());
        humLogModel.logIn (getUsername() , getPassword());
        createTable();
    }

    /**
     * This method will LogIn the user
     * @param username
     * @param password
     */
    public String checkUser (String username, String password ){
        String userMessage = checkUser(username);
        if (userMessage.equalsIgnoreCase("correct")){
            this.username = username;
            String passwordMessage = checkPassword(password , username);
            if(passwordMessage.equalsIgnoreCase("correct")){
                return "success";
            }
            else if (passwordMessage.equalsIgnoreCase("incorrect")){
                return "Incorrect password";
            }
            else{
                return passwordMessage;
            }
        }
        else if (userMessage.equalsIgnoreCase("no results found for query")){
            return "User do not exist";
        }
        else {
            return userMessage;
        }
    }

    /**
     * This method will LogOut the user
     */
    public void logOut(){
        ParseUser.logOut();
    }

    public void logIn(String username , String password){
        humLogModel.logIn(username, password);
    }

    public void setUserEssentials(String eMail,String password, String userType, String fName , String lName
                                  ,String mNumber , String hNumber , String street, String locality
    , String city , String postCode){
        setUsername(eMail); setPassword(password); setUserType(userType);
        setFirstName(fName); setLastName(lName); setMobileNumber(mNumber);
        setHouseNumber(hNumber); setStreet(street); setLocality(locality);
        setCity(city); setPostCode(postCode);
    }

    /**
     * This method will create table either customer or tradesman.
     */
    private void createTable(){
        if(getUserType().equalsIgnoreCase("customer")){
            humLogModel.createCustomerTable(getUsername() , getFirstName(),
                    getLastName(), getMobileNumber() , getHouseNumber(),
                    getStreet() , getLocality() , getCity(),
                    getPostCode());
        }
        else{
            humLogModel.createTradesmanTable(getUsername() , getFirstName(),
                    getLastName(), getMobileNumber() , getHouseNumber(),
                    getStreet() , getLocality() , getCity(),
                    getPostCode());
        }
    }

    public String checkUser(String username){
      return  humLogModel.checkUser(username);
    }

    private String checkPassword(String password , String username){
       return  humLogModel.checkPassword(password, username);
    }

    /**
     * This method will set the details for the home activity
     * @param username
     */
    public void setDetails(String username){
        this.username = username;
        setUserType(humLogModel.getUserType(username));
        setFirstName(humLogModel.getFirstName(username, userType));
        setLastName(humLogModel.getLastName(username, userType));
    }

    public String postAd(String username , String trade ,String city , String ad){
        String message = humLogModel.postAd(username , trade ,city , ad);
        if(message.equalsIgnoreCase("success")){
            return "success";
        }else {
            return message;
        }
    }

    public void setOldData(String username , String userType){
        setPassword(humLogModel.getPassword(username));
        setHouseNumber(humLogModel.getHouseNumber(username, userType));
        setMobileNumber(humLogModel.getMobileNumber(username, userType));
        setStreet(humLogModel.getStreet(username , userType));
        setLocality(humLogModel.getLocality(username, userType));
        setPostCode(humLogModel.getPostCode(username, userType));
        setCity(humLogModel.getCity(username, userType));
    }

    public String setNewData(){
        if(userType.equalsIgnoreCase("customer")){
            String returnMessage = humLogModel.setNewDataInCustomerTable(username , firstName , lastName,
                    mobileNumber , houseNumber , street , locality , city , postCode);
            if(returnMessage.equalsIgnoreCase("success")){
                String passwordMessage = humLogModel.setNewPassword(username , password);
                if(passwordMessage.equalsIgnoreCase("success")){
                    return "success";
                }else {
                    return passwordMessage;
                }
            }else {
                return returnMessage;
            }
        }else {

            String returnMessage = humLogModel.setNewDataInTradesman(username , firstName , lastName
            , mobileNumber , houseNumber , street , locality , city , postCode);
            if(returnMessage.equalsIgnoreCase("success")){
                String passwordMessage = humLogModel.setNewPassword(username , password);
                if(passwordMessage.equalsIgnoreCase("success")){
                    return "success";
                }else {
                    return passwordMessage;
                }
            }else {
                return returnMessage;
            }
        }

    }

    public List<String> getTradeList(String username){

        return humLogModel.getTradeList(username);
    }

    public List<String> getCityList(String username){

        return humLogModel.getCityList(username);
    }

    public List<String> getAdList(String username){

        return humLogModel.getAdList(username);
    }

    public void deleteAdvertisement(String username , int position){
        humLogModel.deleteAdvertisement(username, position);
    }

    public boolean checkTradeProfile(String username){
        return humLogModel.checkProfiles(username);
    }

    public boolean checkAdPosted(String username){
        return humLogModel.checkAds(username);
    }

    public String getAboutText(String username){
        return humLogModel.getAboutText(username);
    }
    public String makeTradeProfile(String username , String trade ,String city , String about){
        if(humLogModel.checkProfiles(username)){
            return humLogModel.updateTradeProfile(username , trade , city , about);
        }else {
            return humLogModel.makeTradeProfile(username, trade, city, about);
        }
    }

    public void setProfileRatings(String username){
       setRatings(humLogModel.getRatings(username));
       setJobsDone(humLogModel.getJobsDone(username));
        setScore(humLogModel.getScore(username));
    }

    public List<String> getAdSearchResultUsernameList(String city , String trade){
        return humLogModel.getAdUsernameList(city, trade);
    }

    public List<String> getAdvertisementFirstNameList(List<String> usernameList ){
        List<String> firstNameList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            firstNameList.add(i, humLogModel.getFirstName(usernameList.get(i), "customer"));
        }
        return firstNameList;
    }

    public List<String> getAdvertisementLastNameList(List<String> usernameList ){

        List<String> lastNameList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            lastNameList.add(i, humLogModel.getLastName(usernameList.get(i), "customer"));
        }

        return lastNameList;
    }

    public List<String> getAdvertisementStreetList(List<String> usernameList){

        List<String> streetList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            streetList.add(i , humLogModel.getStreet(usernameList.get(i), "customer"));
        }

        return streetList;
    }
    public List<String> getAdvertisementLocalityList(List<String> usernameList){

        List<String> localityList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            localityList.add(i , humLogModel.getLocality(usernameList.get(i), "customer"));
        }

        return localityList;
    }

    public List<String> getAdvertisementCityList(List<String> usernameList){
        List<String> cityList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            cityList.add(i , humLogModel.getCity(usernameList.get(i), "customer"));
        }

        return cityList;
    }

    public List<String> getAdvertisementMobileNumberList(List<String> usernameList ){

        List<String> mobileNumberList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            mobileNumberList.add(i , humLogModel.getMobileNumber(usernameList.get(i), "customer"));
        }

        return mobileNumberList;
    }

    public List<String> getAdvertisementPostCodeList(List<String> usernameList ){

        List<String> postCodeList = new ArrayList<String>();

          for(int i =0; i<usernameList.size(); i++){
            postCodeList.add(i , humLogModel.getPostCode(usernameList.get(i), "customer"));
        }

        return postCodeList;
    }

    public List<String> getAdvertisementDetailsList( String city , String trade){

        return humLogModel.getAdvertisementDetailsList(city, trade);
    }
/*************Here start the methods of trade lists***/
    public List<String> getTradeUsernameList(String city , String trade){
        return humLogModel.getTradeUsernameList(city, trade);
    }

    public List<String> getTradeFirstNameList(List<String> usernameList ){
        List<String> firstNameList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            firstNameList.add(i , humLogModel.getFirstName(usernameList.get(i), "tradesman"));
        }
        return firstNameList;
    }

    public List<String> getTradeLastNameList(List<String> usernameList ){

        List<String> lastNameList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            lastNameList.add(i , humLogModel.getLastName(usernameList.get(i), "tradesman"));
        }

        return lastNameList;
    }

    public List<String> getTradeStreetList(List<String> usernameList){

        List<String> streetList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            streetList.add(i , humLogModel.getStreet(usernameList.get(i), "tradesman"));
        }

        return streetList;
    }
    public List<String> getTradeLocalityList(List<String> usernameList){

        List<String> localityList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            localityList.add(i , humLogModel.getLocality(usernameList.get(i), "tradesman"));
        }

        return localityList;
    }

    public List<String> getTradeCityList(List<String> usernameList){
        List<String> cityList = new ArrayList<String>();
        for (int i=0; i<usernameList.size(); i++){
            cityList.add(humLogModel.getCity(usernameList.get(i), "tradesman"));
        }
        return cityList;
    }

    public List<String> getTradeRatingList(List<String> usernameList){
        List<String> ratingList = new ArrayList<String>();

        for (int i=0; i<usernameList.size(); i++){
            int rating = humLogModel.getRatingInt(usernameList.get(i));
            ratingList.add( i , String.valueOf(rating));
        }
        return ratingList;
    }


    public List<String> getTradeMobileNumberList(List<String> usernameList ){

        List<String> mobileNumberList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            mobileNumberList.add(i , humLogModel.getMobileNumber(usernameList.get(i), "tradesman"));
        }

        return mobileNumberList;
    }

    public List<String> getTradePostCodeList(List<String> usernameList ){

        List<String> postCodeList = new ArrayList<String>();

        for(int i =0; i<usernameList.size(); i++){
            postCodeList.add(i , humLogModel.getPostCode(usernameList.get(i), "tradesman"));
        }

        return postCodeList;
    }

    public List<String> getTradeDetailsList( String city , String trade){

        return humLogModel.getTradeDetailsList(city, trade);
    }

    public void setRelations(String username , String otherUsername , String city , String trade , String userType){

        if(userType.equalsIgnoreCase("customer")){
            humLogModel.setCustomerRelation(username , otherUsername , city , trade);
        }else {
            humLogModel.setTradesmanRelation(username, otherUsername, city, trade);
        }
    }

    public List<String> getCustomerRelationTableList(String username , String userType){

        if(userType.equalsIgnoreCase("customer")) {
            return humLogModel.getCustomerRelationList(username);
        }else {
            return humLogModel.getCustomerRelationListForTradesman(username);
        }

    }

    public List<String> getTradesmanRelationTableList(String username , String userType){

        if(userType.equalsIgnoreCase("customer")) {
            return humLogModel.getTradesmanRelationList(username);
        }else {
            return humLogModel.getTradesmanRelationListForTradesman(username);
        }
    }

    public void updateScoreAndRatingAndJobs(float rating , String username){
        int previousRating = humLogModel.getRatingInt(username);
        int previousJobs = Integer.parseInt(humLogModel.getJobsDone(username));
        int previousScore = Integer.parseInt(humLogModel.getScore(username));

        int previousAverage = previousRating * previousJobs;
        int totalJobs = previousJobs+1;
        int newRating = (int) Math.round((previousAverage+rating)/totalJobs);
        int newScore;

        if(previousJobs>=5 && previousJobs <8){
            newScore = previousScore + 1;
        } else if(previousJobs >=8){
            newScore = previousScore - previousRating + newRating + 1;
        }else {
            newScore = previousScore;
        }
        humLogModel.updateScoreCard(username , newRating , totalJobs , newScore);

    }

    public void deleteCustomerRelationInterest(String username , String otherUsername , int position){
        humLogModel.deleteCustomerRelation(username, otherUsername, position);
    }

    public void deleteTradesmanRelationInterest(String username , String otherUsername , int position){
        humLogModel.deleteTradesmanRelation(username, otherUsername, position);
    }

    public void deleteMyAccount(String username , String userType){
        if(userType.equalsIgnoreCase("customer")){
            humLogModel.deleteUser(username);
            humLogModel.deleteAllAdvertisements(username);
            humLogModel.deleteCustomer(username);
            humLogModel.deleteAllCustomerRelationByCustomer(username);
            humLogModel.deleteAllTradesmanRelationByCustomer(username);
        }else {

            humLogModel.deleteUser(username);
            humLogModel.deleteTradeProfile(username);
            humLogModel.deleteTradesman(username);
            humLogModel.deleteAllCustomerRelationByTradesman(username);
            humLogModel.deleteAllTradesmanRelationByTradesman(username);
        }
    }

    /**
     * setters and getter.
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setUserType(String userType){
        this.userType = userType;
    }
    public void setFirstName(String fName){
        this.firstName = fName;
    }
    public void setLastName(String lName){
        this.lastName = lName;
    }
    public void setMobileNumber(String mNumber){
        this.mobileNumber = mNumber;
    }
    public void setHouseNumber(String hNo){
        this.houseNumber = hNo;
    }
    public void setStreet(String street){
        this.street = street;
    }
    public void setLocality(String locality){
        this.locality = locality;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    public void setModelObject(){
        this.humLogModel = new HumLogModel();
    }

    public void setScore(String score){
        this.score = score;
    }

    public void setJobsDone(String jobsDone){
        this.jobsDone = jobsDone;
    }

    public void setRatings(String ratings){
        this.ratings = ratings;
    }


    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getUserType(){
        return userType;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getMobileNumber(){
        return mobileNumber;
    }
    public String getHouseNumber(){
        return houseNumber;
    }
    public String getStreet(){
        return street;
    }
    public String getLocality(){
        return locality;
    }
    public String getCity(){
        return city;
    }
    public String getPostCode(){
        return postCode;
    }

    public String getScore(){
        return score;
    }

    public String getJobsDone(){
        return jobsDone;
    }

    public String getRatings(){
        return ratings;
    }
}
