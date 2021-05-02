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
public class Note  extends AbstractEntity<Note>{
    @ColumnInfo(name = "planDate")
    public Date planDate;

    @ColumnInfo(name = "categoryId")
    public int categoryId;

    @ColumnInfo(name = "priorityId")
    public int priorityId;

    @ColumnInfo(name = "status")
    public int statusId;


    public Note(String name, Date planDate, Date createDate, int categoryId, int priorityId, int statusId, int userId) {
        super(name, createDate, userId);

        this.planDate = planDate;
        this.categoryId = categoryId;
        this.priorityId = priorityId;
        this.statusId = statusId;

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

}
