package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
/**
 * Table containing status data
 */
@Entity(tableName = "status")
public class Status {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "statusName")
    private String name;

    @ColumnInfo(name = "createdDate")
    private Date createDate;

    @ColumnInfo(name = "userId")
    public int userId;

    public Status(String name, Date createDate, int userId) {
        this.name = name;
        this.createDate = createDate;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
