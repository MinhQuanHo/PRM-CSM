package com.example.prm392_coffeeshopmanagement.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.RoleDao;
import com.example.prm392_coffeeshopmanagement.dao.UserDao;
import com.example.prm392_coffeeshopmanagement.entity.Role;
import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.utils.StaffWithRole;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StaffRepository {
    private UserDao userDao;
    private RoleDao roleDao;
    private LiveData<List<StaffWithRole>> staffList;
    private ExecutorService executorService;

    public StaffRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        userDao = database.userDao();
        roleDao = database.roleDao();
        staffList = userDao.getStaffList();
        staffList.observeForever(staff -> {
            if (staff == null) {
                Log.e("staffList", "is null");
            } else {
                Log.d("staffList", "size: " + staff.size());
            }
        });
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<StaffWithRole>> getStaffList() {
        return staffList;
    }

    public void insertStaff(User user, int roleId) {
        executorService.execute(() -> {
            long userId = userDao.insertUser(user);
            userDao.insertUserRole((int) userId, roleId);
        });
    }

    public void updateStaff(User user, int roleId) {
        executorService.execute(() -> {
            userDao.updateUser(user);
            userDao.updateUserRole(user.getUserId(), roleId);
        });
    }

    public void deleteStaff(int userId) {
        executorService.execute(() -> {
            userDao.deleteUserRole(userId);
            userDao.deleteUser(userId);
        });
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public StaffWithRole getStaffById(int userId) {
        return userDao.getStaffById(userId);
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public LiveData<List<Role>> getAllRoles() {
        return roleDao.getAllRoles();
    }

    public LiveData<List<Role>> getStaffRoles() {
        return roleDao.getStaffRoles();
    }
}
