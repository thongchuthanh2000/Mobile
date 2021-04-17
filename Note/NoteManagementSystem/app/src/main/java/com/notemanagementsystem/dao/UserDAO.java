package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.notemanagementsystem.entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE uid=(:uid) AND password=(:password)")
    User login(String uid, String password);

    @Insert
    public void insert(User user);

    @Delete
    void delete(User user);

}
