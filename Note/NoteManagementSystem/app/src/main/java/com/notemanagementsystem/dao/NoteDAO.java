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

    @Query("Select count(*) from note where userId=(:userId) and statusId = (:statusId)")
    public abstract Integer getStatusByNote( int statusId,int userId);

    @Query("Select count(*) from note where userId=(:userId)")
    public abstract Integer getCountByNote(int userId);

}
