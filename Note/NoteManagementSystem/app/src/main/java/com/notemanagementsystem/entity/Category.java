package com.notemanagementsystem.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


/**
 * Table containing Category data
 */
@Entity(tableName = "category")
public class Category extends AbstractEntity<Category>{
    public Category(String name, Date createDate, int userId) {
        super(name, createDate, userId);
    }


}
