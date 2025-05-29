package com.example.prm392_coffeeshopmanagement.repository;

import android.content.Context;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.OrderDetailDao;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderDetailRepository {
    private OrderDetailDao orderDetailDao;
    private ExecutorService executorService;

    public OrderDetailRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        orderDetailDao = db.orderDetailDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }
}
