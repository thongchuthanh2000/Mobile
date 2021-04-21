package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "createdDate")
    public Date createDate;

    @ColumnInfo(name = "planDate")
    public Date planDate;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "priority")
    public String priority;

    @ColumnInfo(name = "status")
    public String status;

    public Note(String name, Date planDate, Date createDate, String category, String priority, String status) {
        this.name = name;
        this.createDate = createDate;
        this.planDate = planDate;
        this.category = category;
        this.priority = priority;
        this.status = status;
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

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
