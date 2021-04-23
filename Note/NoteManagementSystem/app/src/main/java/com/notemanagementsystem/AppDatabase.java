package com.notemanagementsystem;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.notemanagementsystem.dao.CategoryDAO;
import com.notemanagementsystem.dao.NoteDAO;
import com.notemanagementsystem.dao.PriorityDAO;
import com.notemanagementsystem.dao.StatusDAO;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Converters;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;
import com.notemanagementsystem.entity.Status;
import com.notemanagementsystem.entity.User;

@Database(entities = {User.class, Note.class, Priority.class, Category.class, Status.class},
        version = 1, exportSchema = false)

@TypeConverters({Converters.class})
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
    public abstract NoteDAO noteDAO();
    public abstract PriorityDAO priorityDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract StatusDAO statusDAO();

}
