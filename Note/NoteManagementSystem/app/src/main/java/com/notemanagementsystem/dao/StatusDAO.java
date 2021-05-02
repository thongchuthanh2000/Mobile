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
public interface StatusDAO extends AbstractDao<Status>{

    @Query("SELECT * FROM status WHERE userId =(:userId)")
    List<Status> getAllStatusById(int userId);

    @Query("SELECT * FROM status WHERE id =(:id)")
    Status getStatusById(int id);

    @Query("SELECT * FROM status WHERE statusName =(:statusName)")
    Status getStatusByName(String statusName);
}
