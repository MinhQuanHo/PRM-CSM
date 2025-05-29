package com.example.prm392_coffeeshopmanagement.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.repository.UserRepository;

import java.util.List;
import java.util.function.Consumer;

public class EmployeeViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> employees;

    public EmployeeViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getEmployees() {
        employees =  userRepository.getEmployees();
        return employees;
    }

    public void deleteEmployee(int employeeId , Consumer<Boolean> callback) {
        userRepository.deleteEmployee(employeeId , callback);
    }

    public void addEmployee(User employee) {
        userRepository.addEmployee(employee);
    }

    public boolean isUsernameExists(String username) {
        return userRepository.isUsernameExists(username);
    }

    public boolean isEmailExists(String email) {
        return userRepository.isEmailExists(email);
    }

    public boolean isPhoneExists(String phone) {
        return userRepository.isPhoneExists(phone);
    }

    public void updateEmployee(User updatedEmployee) {
        userRepository.updateEmployee(updatedEmployee);
    }
    public User getUserByUserName(String username) {
        return userRepository.getUserByUserName(username);
    }
}
