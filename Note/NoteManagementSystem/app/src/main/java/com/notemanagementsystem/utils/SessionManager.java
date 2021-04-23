package com.notemanagementsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static String email = "";
    public SessionManager(Context context) {
        // TODO Auto-generated constructor stub
        sharedPreferences=context.getSharedPreferences("appkey",0);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void setLogin(boolean login){
        editor.putBoolean("key_login",login);
        editor.commit();
    }
    public void setUserName(String username){
        editor.putString("userName",username);
        editor.commit();
    }

//    public void setEmail(String username){
//        editor.putString("email",username);
//        editor.commit();
//    }


    public boolean getLogin(){
        return sharedPreferences.getBoolean("key_login",false);
    }
    public String getUserName(){
        return sharedPreferences.getString("userName","");
    }
//    public String getEmail(){
//        return sharedPreferences.getString("email","");
//    }

    public void setUserId(int userId) {
        editor.putInt("key_userId",userId);
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("key_userId",-1);
    }
}
