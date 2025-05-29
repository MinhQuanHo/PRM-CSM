package com.example.prm392_coffeeshopmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity(tableName = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    private int categoryId;

    @ColumnInfo(name = "category_name")
    @NonNull
    private  String categoryName;
}