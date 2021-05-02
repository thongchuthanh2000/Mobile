package com.notemanagementsystem;

import android.app.Application;

import com.notemanagementsystem.utils.SessionManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SessionManager.init(getApplicationContext());
    }
}
