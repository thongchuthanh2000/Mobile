package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notemanagementsystem.entity.Note;
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

    @Update
    void update(Priority priority);

    @Query("SELECT * FROM priority WHERE userId =(:userId)")
    List<Priority> getAllPriorityById(int userId);
}
