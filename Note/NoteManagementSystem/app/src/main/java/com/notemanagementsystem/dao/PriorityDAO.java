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
public interface PriorityDAO extends AbstractDao<Priority> {
    @Query("SELECT * FROM priority")
    List<Priority> getAllPriority();

    @Query("SELECT * FROM priority WHERE userId =(:userId)")
    List<Priority> getAllPriorityById(int userId);

    @Query("SELECT * FROM priority WHERE id =(:id)")
    Priority getPriorityById(int id);

    @Query("SELECT * FROM priority WHERE name =(:name)")
    Priority getPriorityByName(String name);
}
