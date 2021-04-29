package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
/**
 * Table containing note data
 */
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

    @ColumnInfo(name = "categoryId")
    public int categoryId;

    @ColumnInfo(name = "priorityId")
    public int priorityId;

    @ColumnInfo(name = "status")
    public int statusId;

    @ColumnInfo(name = "userId")
    public int userId;

    public Note(String name, Date planDate, Date createDate, int categoryId, int priorityId, int statusId, int userId) {
        this.name = name;
        this.createDate = createDate;
        this.planDate = planDate;
        this.categoryId = categoryId;
        this.priorityId = priorityId;
        this.statusId = statusId;
        this.userId = userId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
