package com.notemanagementsystem.entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;


public class AbstractEntity<T> {


    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private Date createDate;

    @ColumnInfo
    private int userId;

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    @ColumnInfo
    private int isDeleted;

    public AbstractEntity(String name, Date createDate, int userId) {
        this.name = name;
        this.createDate = createDate;
        this.userId = userId;
    }

    public AbstractEntity(AbstractEntity<Priority> ec) {
        this.name = ec.name;
        this.createDate = ec.createDate;
        this.userId = ec.userId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AbstractEntity(){

    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity<?> that = (AbstractEntity<?>) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
