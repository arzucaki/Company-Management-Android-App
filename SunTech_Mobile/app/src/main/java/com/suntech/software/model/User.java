package com.suntech.software.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ramazan on 11/8/2016.
 */
public class User {
    private int User_id;
    private String UserName;
    private String Password;
    private String Email_Adres;

    public String getEmail_Adres() {
        return Email_Adres;
    }

    public void setEmail_Adres(String email_Adres) {
        Email_Adres = email_Adres;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }


    public User(JSONObject jsonObject) {
        try {
            setUser_id(jsonObject.getInt("User_id"));
            setUserName(jsonObject.getString("UserName"));
            setPassword(jsonObject.getString("Password"));
            setEmail_Adres(jsonObject.getString("Email_Adres"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
