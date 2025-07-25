package com.example.prm392_coffeeshopmanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Query("SELECT COUNT(*) FROM order_detail WHERE product_id = :productId")
    int countOrderDetailsByProductId(int productId);

    @Query("SELECT * FROM order_detail WHERE order_id = :orderId")
    List<OrderDetail> getOrderDetailsByOrderId(int orderId);

    @Insert
    void insert(OrderDetail orderDetail);
}