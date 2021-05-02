package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

/**
 * Table containing priority data
 */
@Entity(tableName = "priority")
public class Priority extends AbstractEntity<Priority>{

    public Priority(String name, Date createDate, int userId) {
        super(name, createDate, userId);
    }
}
