package com.example.praduman.humlog;

import com.parse.Parse;
import com.parse.ParseUser;

import java.io.Serializable;

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
        else if (userMessage.equalsIgnoreCase("incorrect")){
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
        setLastName(humLogModel.getLastName (username , userType));
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
        setPassword(humLogModel.getPassword(username ));
        setHouseNumber(humLogModel.getHouseNumber(username , userType));
        setMobileNumber(humLogModel.getMobileNumber(username , userType));
        setStreet(humLogModel.getStreet(username , userType));
        setLocality(humLogModel.getLocality(username , userType));
        setPostCode(humLogModel.getPostCode(username , userType));
        setCity(humLogModel.getCity(username , userType));
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

}
