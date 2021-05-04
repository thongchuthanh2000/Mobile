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
public abstract class PriorityDAO extends AbstractDao<Priority> {


    @Query("SELECT * FROM priority WHERE id =(:id)")
    public abstract Priority getPriorityById(int id);

    @Query("SELECT * FROM priority WHERE name =(:name)")
    public abstract Priority getPriorityByName(String name);
}
