package com.example.prm392_coffeeshopmanagement.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.OrderDetailDao;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;

import java.util.List;

public class OrderDetailRepository {
    private OrderDetailDao orderDetailDao;
    private Context context;

    public OrderDetailRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        orderDetailDao = db.orderDetailDao();
        context = application.getApplicationContext();
    }

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) {
        return orderDetailDao.getOrderDetailsByOrderId(orderId);
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                orderDetailDao.insert(orderDetail);
                return null;
            }
        }.execute();
    }
}