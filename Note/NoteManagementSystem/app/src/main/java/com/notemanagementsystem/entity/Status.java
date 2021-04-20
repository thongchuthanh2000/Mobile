package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "status")
public class Status {
    @PrimaryKey(autoGenerate = true)
    public int uid;


    @ColumnInfo(name = "name")
    public String name;


    @ColumnInfo(name = "createdDate")
    public Date createdDate;

    public Status(String name, Date createdDate) {
        this.name = name;
        this.createdDate = createdDate;
    }
    public Status() {
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }



}
