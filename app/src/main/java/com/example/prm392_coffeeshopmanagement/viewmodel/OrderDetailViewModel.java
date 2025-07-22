package com.example.prm392_coffeeshopmanagement.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.repository.OrderDetailRepository;

import java.util.List;

public class OrderDetailViewModel extends AndroidViewModel {
    private OrderDetailRepository orderDetailRepository;

    public OrderDetailViewModel(@NonNull Application application) {
        super(application);
        orderDetailRepository = new OrderDetailRepository(application);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        return orderDetailRepository.getOrderDetailByOrderId(orderId);
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.insertOrderDetail(orderDetail);
    }
}