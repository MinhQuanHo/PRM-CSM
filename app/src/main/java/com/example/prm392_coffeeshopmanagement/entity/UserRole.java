package com.example.prm392_coffeeshopmanagement.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity(tableName = "user_role",
        primaryKeys = {"user_id", "role_id"},
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "user_id"),
                @ForeignKey(entity = Role.class, parentColumns = "role_id", childColumns = "role_id")
        })

public class UserRole {
    @ColumnInfo(name = "user_id")
    private  int userId;

    @ColumnInfo(name = "role_id")
    private int roleId;
}