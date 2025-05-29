package com.example.prm392_coffeeshopmanagement.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.utils.EditUserProfile;
import com.example.prm392_coffeeshopmanagement.utils.UserWithRole;
import com.example.prm392_coffeeshopmanagement.repository.UserRepository;

import lombok.NonNull;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public UserWithRole login(String username, String password) {
        return userRepository.login(username, password);
    }

    public UserWithRole getUserWithRoleById(int userId) {
        UserWithRole userWithRole = userRepository.getUserWithRoleById(userId);
        return userRepository.getUserWithRoleById(userId);
    }

    public EditUserProfile getUserWithRole(String username) {
        return userRepository.getUserWithRole(username);
    }

    public void updateUserProfile(String fullName, String phone, String avatar, int userId) {
        userRepository.updateUserProfile(fullName, phone, avatar, userId);
    }

    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    public void changePassword(String username, String newPassword) {
        userRepository.updatePassword(username, newPassword);
    }
}
