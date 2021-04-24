package com.notemanagementsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/*
 *SessionManager
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */
public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public static String email = "";

    public SessionManager(Context context) {

        sharedPreferences=context.getSharedPreferences("app_key",0);
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

    public boolean getLogin(){
        return sharedPreferences.getBoolean("key_login",false);
    }

    public String getUserName(){
        return sharedPreferences.getString("userName","");
    }

    public void setUserId(int userId) {
        editor.putInt("key_userId",userId);
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("key_userId",-1);
    }
}
