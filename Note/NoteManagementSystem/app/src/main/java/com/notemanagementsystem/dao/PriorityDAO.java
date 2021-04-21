package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.notemanagementsystem.entity.Priority;

import java.util.List;

@Dao
public interface PriorityDAO {
    @Query("SELECT * FROM priority")
    List<Priority> getAllPriority();


    @Insert
    public void insert(Priority priority);

    @Delete
    void delete(Priority priority);
}