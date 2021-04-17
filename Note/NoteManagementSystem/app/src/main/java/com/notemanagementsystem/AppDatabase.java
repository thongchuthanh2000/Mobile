package com.notemanagementsystem;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.User;

@Database(entities = {User.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String dbName = "db_name";
    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getAppDatabase(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, dbName)
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }

    public abstract UserDAO userDAO();

}
