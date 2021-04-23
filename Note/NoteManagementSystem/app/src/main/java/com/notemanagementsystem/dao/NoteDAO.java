package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.User;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("Select count(*) from note where userId=(:userId) and status = (:nameStatus)")
    int getStatusByNote( String nameStatus,int userId);

    @Query("SELECT * FROM note WHERE userId =(:userId)")
    List<Note> getAllNoteById(int userId);
}
