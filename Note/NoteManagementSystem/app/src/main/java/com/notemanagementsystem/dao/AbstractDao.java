package com.notemanagementsystem.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.notemanagementsystem.entity.Category;

import org.jetbrains.annotations.NotNull;

public interface AbstractDao<T> {
    @Insert
    void insert(@NotNull T item);

    @Delete
    void delete(@NotNull T item);

    @Update
    void update(@NotNull T item);
}
