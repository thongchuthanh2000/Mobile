package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Table containing Category data
 */
@Entity(tableName = "category")
public class Category extends AbstractEntity<Category>{
    public Category(String name, Date createDate, int userId) {
        super(name, createDate, userId);
    }
}
