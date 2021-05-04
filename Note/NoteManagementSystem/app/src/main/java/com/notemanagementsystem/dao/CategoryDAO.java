package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.notemanagementsystem.entity.Category;

import java.util.List;

@Dao
public abstract class CategoryDAO extends  AbstractDao<Category> {

    @Query("SELECT * FROM category WHERE id =(:id)")
    public abstract Category getCategoryById(int id);

    @Query("SELECT * FROM category WHERE name =(:cateName)")
    public abstract Category getCategoryByName(String cateName);
}
