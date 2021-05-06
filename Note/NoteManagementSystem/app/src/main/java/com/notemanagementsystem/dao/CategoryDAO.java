package com.notemanagementsystem.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.notemanagementsystem.entity.Category;

import java.util.List;

@Dao
public abstract class CategoryDAO extends  AbstractDao<Category> {
}
