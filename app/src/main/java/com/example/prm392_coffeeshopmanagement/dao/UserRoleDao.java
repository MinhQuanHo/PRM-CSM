package com.example.prm392_coffeeshopmanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.prm392_coffeeshopmanagement.entity.UserRole;

@Dao
public interface UserRoleDao {
    @Insert
    void insertUserRole(UserRole userRole);
}
