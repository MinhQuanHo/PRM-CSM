package com.example.prm392_coffeeshopmanagement.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Entity(tableName = "order_detail", foreignKeys = {
        @ForeignKey(entity = Order.class, parentColumns = "order_id", childColumns = "order_id"),
        @ForeignKey(entity = Product.class, parentColumns = "product_id", childColumns = "product_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_detail_id")
     int orderDetailId;

    @ColumnInfo(name = "order_id")
    @NonNull
     int orderId;

    @ColumnInfo(name = "product_id")
    @NonNull
     int productId;

    @ColumnInfo(name = "price")
    @NonNull
     double price;

    @ColumnInfo(name = "quantity")
    @NonNull
     int quantity;
}

