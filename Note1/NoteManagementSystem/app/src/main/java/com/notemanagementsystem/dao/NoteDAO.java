package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.notemanagementsystem.entity.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insertNote(Note note);

    @Query("select * from note")
    List<Note> getListNote();
}
