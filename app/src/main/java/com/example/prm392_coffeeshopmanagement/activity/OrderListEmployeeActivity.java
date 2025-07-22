package com.example.prm392_coffeeshopmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.adapter.OrderEmployeeAdapter;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.viewmodel.OrderViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.UserViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class OrderListEmployeeActivity extends BaseActivity {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_list_employee;
    }

    RecyclerView recyclerView;
    List<Order> orders = new ArrayList<>();
    private OrderViewModel orderViewModel;
    private UserViewModel userViewModel;
    OrderEmployeeAdapter orderAdapter;
    TextInputEditText searchBox;
    ShimmerFrameLayout skeletonLayout;
    private int employeeId;
    private Button buttonAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initData();
        addEvents();
        getOrderRequest();
        skeletonLayout.startShimmer();
    }

    private void initData() {
        skeletonLayout = findViewById(R.id.skeletonLayout);
        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        searchBox = findViewById(R.id.searchEditText);
        buttonAddOrder = findViewById(R.id.buttonAddOrder);

        orderViewModel = new OrderViewModel(getApplication());
        userViewModel = new UserViewModel(getApplication());

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            employeeId = userViewModel.getUserByUserName(username).getUserId();
        } else {
            android.widget.Toast.makeText(this, "Vui lòng đăng nhập!", android.widget.Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addEvents() {
        searchOrder();
        setupAddOrderButton();
    }

    private void setupAddOrderButton() {
        if (buttonAddOrder != null) {
            buttonAddOrder.setOnClickListener(v -> {
                Intent intent = new Intent(OrderListEmployeeActivity.this, AddOrderActivity.class);
                startActivity(intent);
            });
        }
    }

    private void getOrderRequest() {
        orderViewModel.getOrdersCreateByEmployee(employeeId).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orderList) {
                orders = new ArrayList<>(orderList);
                renderOrders();
            }
        });
    }

    private void renderOrders() {
        orderAdapter = new OrderEmployeeAdapter(orders, this);
        orderAdapter.setOnItemClickListener(order -> {
            Intent intent = new Intent(OrderListEmployeeActivity.this, OrderDetailsActivity.class);
            intent.putExtra("ORDER_ID", order.getOrderId());
            startActivity(intent);
        });
        recyclerView.setAdapter(orderAdapter);
        skeletonLayout.stopShimmer();
        skeletonLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void searchOrder() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                orderAdapter.setOrderList(orders);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                orderAdapter.filter(searchText);
                orderAdapter.notifyDataSetChanged();
            }
        });
    }
}