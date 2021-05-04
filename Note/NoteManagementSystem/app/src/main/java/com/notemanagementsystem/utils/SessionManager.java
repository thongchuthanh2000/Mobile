package com.notemanagementsystem.utils;

import android.content.Context;

/*
 *SessionManager
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */
public class SessionManager {

    private static final String USER_ID ="USER_ID";
    private static final String REMEMBER_ME ="REMEMBER_ME";

    private MySharedPreferences mySharedPreferences;
    private static SessionManager instance;

    public static void init(Context mContext){
        instance= new SessionManager();
        instance.mySharedPreferences = new MySharedPreferences(mContext);
    }

    public static SessionManager getInstance(){
        if(instance==null){
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUserId(Integer userId) {
        SessionManager.getInstance().mySharedPreferences.putIntValue(USER_ID,userId);
    }

    public Integer getUserId() {
        return SessionManager.getInstance().mySharedPreferences.getIntValue(USER_ID);
    }

    public void setRememberMe(Boolean rememberMe) {
        SessionManager.getInstance().mySharedPreferences.putBooleanValue(REMEMBER_ME,rememberMe);
    }

    public Boolean getRememberMe() {
        return SessionManager.getInstance().mySharedPreferences.getBooleanValue(REMEMBER_ME);
    }


}
