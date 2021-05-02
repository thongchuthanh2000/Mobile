package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.notemanagementsystem.entity.Category;
import com.notemanagementsystem.entity.Note;
import com.notemanagementsystem.entity.Priority;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@Dao
public interface CategoryDAO extends AbstractDao<Category> {

    @Query("SELECT * FROM category WHERE userId =(:userId)")
    List<Category> getAllCategoryById(int userId);

    @Query("SELECT * FROM category WHERE id =(:id)")
    Category getCategoryById(int id);

    @Query("SELECT * FROM category WHERE catName =(:cateName)")
    Category getCategoryByName(String cateName);
}
