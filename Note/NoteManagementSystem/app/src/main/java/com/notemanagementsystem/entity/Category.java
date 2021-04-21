package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "catName")
    private String name;

    @ColumnInfo(name = "createdDate")
    private Date createDate;

    public Category(String name, Date createDate) {
        this.name = name;
        this.createDate = createDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
