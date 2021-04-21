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

    @Query("SELECT * FROM user WHERE email=(:email) AND password=(:password)")
    User checkUser(String email, String password);

    @Query("SELECT * FROM user WHERE email=(:email)")
    User checkExistUser(String email);

    @Query("SELECT * FROM user WHERE id=(:id)")
    User getUserById(int id);

    @Query("UPDATE user SET password=(:password) WHERE id=(:id)")
    void updatePassword(int id, String password);

    @Insert
    public void insert(User user);

    @Delete
    void delete(User user);

}
