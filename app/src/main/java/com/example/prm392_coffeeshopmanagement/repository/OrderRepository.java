package com.example.prm392_coffeeshopmanagement.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.OrderDao;
import com.example.prm392_coffeeshopmanagement.dao.OrderDetailDao;
import com.example.prm392_coffeeshopmanagement.entity.Category;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.utils.DailyOrderStats;
import com.example.prm392_coffeeshopmanagement.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderRepository {
    private OrderDao orderDao;
    private OrderDetailDao orderDetailDao;
    private ExecutorService executorService;

    public OrderRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        orderDao = db.orderDao();
        orderDetailDao = db.orderDetailDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Order>> getOrdersCreateByEmployee(int employeeId) {
        return orderDao.getOrdersCreateByEmployee(employeeId);
    }

    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    public LiveData<List<DailyOrderStats>> getDailyStatsForCurrentWeek() {
        Date startDate = DateUtils.getStartOfWeek();
        Date endDate = DateUtils.getEndOfWeek();
        return orderDao.getDailyOrderStatsByDateRange(startDate, endDate);
    }
}
