package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Table containing status data
 */
@Entity(tableName = "status")
public class Status extends AbstractEntity<Status>{
    public Status(String name, Date createDate, int userId) {
        super(name, createDate, userId);
    }
}
