package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notemanagementsystem.entity.User;

import java.util.List;

@Dao
public abstract class UserDAO  extends AbstractDao<User>{

    @Query("SELECT * FROM user WHERE email=(:email) AND password=(:password)")
    public abstract User checkUser(String email, String password);

    @Query("SELECT * FROM user WHERE email=(:email)")
    public abstract User checkExistUser(String email);

    @Query("SELECT * FROM user WHERE id=(:id)")
    public abstract User getUserById(int id);

    @Query("Select count(*)>0 from user where id!=(:id) and email=(:email)" )
    public abstract Boolean checkEmailExist(String email,int id);


}
