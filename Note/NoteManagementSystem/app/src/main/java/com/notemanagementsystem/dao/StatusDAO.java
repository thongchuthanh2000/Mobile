package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Status;

import java.util.List;

@Dao
public interface StatusDAO {

    @Query("SELECT * FROM status")
    List<Status> getAllStatus();

    @Insert
    public void insert(Status status);

    @Update
    public void update(Status status);

    @Delete
    void delete(Status status);

    @Query("SELECT * FROM status WHERE userId =(:userId)")
    List<Status> getAllStatusById(int userId);
}
