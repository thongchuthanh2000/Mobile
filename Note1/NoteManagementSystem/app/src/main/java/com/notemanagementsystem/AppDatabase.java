package com.notemanagementsystem;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.notemanagementsystem.dao.NoteDAO;
import com.notemanagementsystem.dao.UserDAO;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.User;

@Database(entities = {User.class, Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

//    static Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE `note` (`id` INTEGER, " + "`name` TEXT, PRIMARY KEY(`id`))");
//        }
//    };

    private static final String dbName = "db_name";
    private static AppDatabase appDatabase;

    public static synchronized AppDatabase getAppDatabase(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, dbName)
                    .allowMainThreadQueries()
                    //.addMigrations(MIGRATION_1_2)
                    .build();
        }
        return appDatabase;
    }

    public abstract UserDAO userDAO();

    public abstract NoteDAO noteDAO();



}
