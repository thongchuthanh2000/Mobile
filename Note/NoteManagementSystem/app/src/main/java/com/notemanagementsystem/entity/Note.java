package com.notemanagementsystem.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey (autoGenerate = true)
    private int id;

    private Category category;
    private Priority priority;
    private Status status;

    

}
