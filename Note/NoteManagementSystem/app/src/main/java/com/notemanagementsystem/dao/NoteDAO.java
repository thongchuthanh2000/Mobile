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
public abstract class NoteDAO extends AbstractDao<Note> {

    @Query("Select count(*) from note where userId=(:userId) and statusId = (:nameStatus)")
    public abstract Integer getStatusByNote( String nameStatus,int userId);

    @Query("SELECT * FROM note WHERE userId =(:userId)")
    public abstract List<Note> getAllById(int userId);
}
