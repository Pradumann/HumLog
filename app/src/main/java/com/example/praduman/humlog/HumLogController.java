package com.example.praduman.humlog;

import com.parse.Parse;
import com.parse.ParseUser;

import java.io.Serializable;

/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogController implements Serializable {
    private transient String username = "";
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


    public void createModelObject(){

    }

    public String getStatus(){
        if(username.equals("")){
            return "online";
        }
        else{
            return "offline";
        }
    }

    public void createNewUserAndLogIn(){
        this.humLogModel = new HumLogModel();
       this.humLogModel.createNewUser(getUsername(), getPassword(), getUserType());
        createTable(); // i.e. set data
    }

    /**
     * This method will LogIn the user
     * @param username
     * @param password
     */
    public String logIn (String username, String password ){
        this.humLogModel = new HumLogModel();
        if (checkUser(username).equalsIgnoreCase("correct")){
            this.username = username;
            if(checkPassword(password , username).equalsIgnoreCase("correct")){
                this.password = password;
                // set rest of data
                return "success";
            }
            else{
                return "Incorrect password";
            }
        }
        else {
            return "User do not exist";
        }
    }

    /**
     * This method will LogOut the user
     */
    public void logOut(){
        this.username = "";
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

    private String checkUser(String username){
      return  humLogModel.checkUser(username);
    }

    private String checkPassword(String password , String username){
       return  humLogModel.checkPassword(password, username);
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

    private String getUsername(){
        return username;
    }
    private String getPassword(){
        return password;
    }
    private String getUserType(){
        return userType;
    }
    private String getFirstName(){
        return firstName;
    }
    private String getLastName(){
        return lastName;
    }
    private String getMobileNumber(){
        return mobileNumber;
    }
    private String getHouseNumber(){
        return houseNumber;
    }
    private String getStreet(){
        return street;
    }
    private String getLocality(){
        return locality;
    }
    private String getCity(){
        return city;
    }
    private String getPostCode(){
        return postCode;
    }

}
