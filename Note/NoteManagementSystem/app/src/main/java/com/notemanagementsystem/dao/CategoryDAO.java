package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Priority;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM category")
    List<Category> getAllCategory();

    @Insert
    public void insert(Category category);

    @Delete
    void delete(Category category);
}