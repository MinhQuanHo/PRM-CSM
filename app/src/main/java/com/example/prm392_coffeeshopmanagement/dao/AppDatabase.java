package com.example.prm392_coffeeshopmanagement.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.prm392_coffeeshopmanagement.entity.Category;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.entity.Role;
import com.example.prm392_coffeeshopmanagement.entity.Schedule;
import com.example.prm392_coffeeshopmanagement.entity.User;
import com.example.prm392_coffeeshopmanagement.entity.UserRole;
import com.example.prm392_coffeeshopmanagement.utils.Converters;

@Database(entities = {User.class, Role.class, UserRole.class, Product.class, Order.class,
        Category.class, OrderDetail.class, Schedule.class}, version = 6, exportSchema = false)
@TypeConverters(Converters.class)
public  abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;

    public abstract UserDao userDao();
    public abstract RoleDao roleDao();
    public abstract UserRoleDao userRoleDao();
    public abstract ProductDao productDao();
    public abstract OrderDao orderDao();
    public abstract CategoryDao categoryDao();
    public abstract OrderDetailDao orderDetailDao();
    public abstract ScheduleDao scheduleDao();
    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "prm392_coffee_shop_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
