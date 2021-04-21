package com.notemanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

    public boolean getLogin(){
        return sharedPreferences.getBoolean("key_login",false);
    }

    public void setUserId(int userId) {
        editor.putInt("key_userId",userId);
        editor.commit();
    }

    public int getUserId() {
        return sharedPreferences.getInt("key_userId",-1);
    }
}
