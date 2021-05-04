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
public abstract class StatusDAO extends AbstractDao<Status>{



    @Query("SELECT * FROM status WHERE id =(:id)")
    public abstract Status getStatusById(int id);

    @Query("SELECT * FROM status WHERE status.name =(:statusName)")
    public abstract Status getStatusByName(String statusName);
}
