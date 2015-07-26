package com.example.praduman.humlog;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * Created by Praduman on 23/07/2015.
 */
public class HumLogModel extends Application {

    @Override
    public void onCreate(){

    }

    public void createNewUser(String username, String password){

        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

            }
        });
    }
}
