package com.example.prm392_coffeeshopmanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.repository.OrderRepository;
import com.example.prm392_coffeeshopmanagement.utils.DailyOrderStats;

import java.util.List;
import java.util.function.Consumer;

public class OrderViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;
    private LiveData<List<Order>> orders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        orderRepository = new OrderRepository(application);
    }

    public LiveData<List<Order>> getOrdersCreateByEmployee(int employeeId) {
        return orderRepository.getOrdersCreateByEmployee(employeeId);
    }

    public Order getOrderById(int orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public LiveData<List<DailyOrderStats>> getDailyStatsForCurrentWeek() {
        return orderRepository.getDailyStatsForCurrentWeek();
    }

    public void insertOrder(Order order, Consumer<Long> callback) {
        orderRepository.insertOrder(order, callback);
    }
}