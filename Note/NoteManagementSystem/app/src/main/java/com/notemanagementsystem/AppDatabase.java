package com.notemanagementsystem;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Ignore;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.notemanagementsystem.constant.SystemConstant;
import com.notemanagementsystem.dao.AbstractDao;
import com.notemanagementsystem.dao.CategoryDAO;
import com.notemanagementsystem.dao.NoteDAO;
import com.notemanagementsystem.dao.PriorityDAO;
import com.notemanagementsystem.dao.StatusDAO;
import com.notemanagementsystem.dao.UserDAO;

import com.notemanagementsystem.entity.AbstractEntity;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Converters;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;
import com.notemanagementsystem.entity.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/*
 *AppDatabase
 *@author  Quyet Sinh
 * @version 1.0
 * @since   2021-04-24
 */


@Database(entities = {User.class, Note.class, Priority.class, Category.class, Status.class},
        version = 1, exportSchema = false)

@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

//    public static <T> MyObject<T> createMyObject(Class<T> type) {
//        return new MyObject<T>(type);
//    }

    //Name databases
    private static final String dbName = "db_name";
    private static AppDatabase appDatabase;
    /*Singleton create database
    */
    public static synchronized AppDatabase getAppDatabase(Context context){

        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, dbName)
                    .allowMainThreadQueries()
                    .build();
        }

        return appDatabase;

    }

    public abstract UserDAO userDAO();
    public abstract NoteDAO noteDAO();
    public abstract PriorityDAO priorityDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract StatusDAO statusDAO();



    public   <S extends AbstractDao> S abstractDao(){
        if (SystemConstant.CHOOSE.equals(SystemConstant.PRIORITY))
            return (S) priorityDAO();
        if (SystemConstant.CHOOSE.equals(SystemConstant.STATUS))
            return (S) statusDAO();
        if (SystemConstant.CHOOSE.equals(SystemConstant.CATEGORY))
            return (S) categoryDAO();

        return  null;
    }


}
