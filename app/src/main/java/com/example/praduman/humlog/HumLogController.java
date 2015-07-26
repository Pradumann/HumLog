package com.example.praduman.humlog;

import java.io.Serializable;

/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogController implements Serializable{
    private transient String username;
    private transient String password;
    public transient String userType;


    public void createNewUser(){
        HumLogModel humLogModel = new HumLogModel();
        humLogModel.createNewUser(getUsername() , getPassword());
    }

    public void setUserEssentials(String username,String password, String userType){
        setUsername(username); setPassword(password); setUserType(userType);
    }

    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setUserType(String userType){
        this.userType = userType;
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
}
