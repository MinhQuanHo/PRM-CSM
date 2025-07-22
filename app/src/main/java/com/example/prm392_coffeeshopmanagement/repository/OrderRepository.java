package com.example.prm392_coffeeshopmanagement.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.OrderDao;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.utils.DailyOrderStats;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class OrderRepository {
    private OrderDao orderDao;
    private Context context;

    public OrderRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        orderDao = db.orderDao();
        context = application.getApplicationContext();
    }

    public LiveData<List<Order>> getOrdersCreateByEmployee(int employeeId) {
        return orderDao.getOrdersCreateByEmployee(employeeId);
    }

    public Order getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    public LiveData<List<DailyOrderStats>> getDailyStatsForCurrentWeek() {
        Date endDate = new Date();
        Date startDate = new Date(endDate.getTime() - 7 * 24 * 60 * 60 * 1000L);
        return orderDao.getDailyOrderStatsByDateRange(startDate, endDate);
    }

    public void insertOrder(Order order, Consumer<Long> callback) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return AppDatabase.getInstance(context).orderDao().insert(order);
            }

            @Override
            protected void onPostExecute(Long orderId) {
                callback.accept(orderId);
            }
        }.execute();
    }
}