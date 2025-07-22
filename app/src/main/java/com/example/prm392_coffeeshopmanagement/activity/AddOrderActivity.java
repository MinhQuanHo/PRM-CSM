package com.example.prm392_coffeeshopmanagement.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.adapter.ProductSelectionAdapter;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.utils.FormartCurrency;
import com.example.prm392_coffeeshopmanagement.viewmodel.OrderDetailViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.OrderViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.ProductViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class AddOrderActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private EditText editTextCustomerName, editTextPhoneNumber;
    private Button buttonSaveOrder;
    private ProductViewModel productViewModel;
    private OrderViewModel orderViewModel;
    private OrderDetailViewModel orderDetailViewModel;
    private UserViewModel userViewModel;
    private ProductSelectionAdapter productAdapter;
    private List<Product> products;
    private List<OrderDetail> selectedOrderDetails;
    private int employeeId;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_order;
    }

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
        setupRecyclerView();
        setupSaveButton();
    }

    private void initData() {
        recyclerView = findViewById(R.id.recyclerViewProducts);
        editTextCustomerName = findViewById(R.id.editTextCustomerName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonSaveOrder = findViewById(R.id.buttonSaveOrder);

        productViewModel = new ProductViewModel(getApplication());
        orderViewModel = new OrderViewModel(getApplication());
        orderDetailViewModel = new OrderDetailViewModel(getApplication());
        userViewModel = new UserViewModel(getApplication());

        selectedOrderDetails = new ArrayList<>();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            employeeId = userViewModel.getUserByUserName(username).getUserId();
        } else {
            Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            finish();
        }

        productViewModel.getProducts().observe(this, products -> {
            this.products = products;
            productAdapter = new ProductSelectionAdapter(products, selectedOrderDetails);
            recyclerView.setAdapter(productAdapter);
        });
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void setupSaveButton() {
        buttonSaveOrder.setOnClickListener(v -> {
            String customerName = editTextCustomerName.getText().toString().trim();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();

            // Kiểm tra các trường không được trống
            if (customerName.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên khách hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedOrderDetails.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một sản phẩm", Toast.LENGTH_SHORT).show();
                return;
            }

            float totalPrice = 0;
            int totalQuantity = 0;
            for (OrderDetail detail : selectedOrderDetails) {
                totalPrice += detail.getPrice() * detail.getQuantity();
                totalQuantity += detail.getQuantity();
            }

            Order order = new Order();
            order.setUserId(employeeId);
            order.setCustomer(customerName);
            order.setTotalQuantity(totalQuantity);
            order.setTotalPrice(totalPrice);
            order.setStatus("Pending");
            order.setPaymentStatus("Chưa thanh toán");
            order.setCreateAt(new Date());

            orderViewModel.insertOrder(order, newOrderId -> {
                if (newOrderId != null && newOrderId > 0) { // Kiểm tra null và giá trị hợp lệ
                    int orderId = newOrderId.intValue(); // Sử dụng intValue() để chuyển từ Long sang int
                    for (OrderDetail detail : selectedOrderDetails) {
                        detail.setOrderId(orderId);
                        orderDetailViewModel.insertOrderDetail(detail);
                    }
                    Toast.makeText(AddOrderActivity.this, "Tạo đơn hàng thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddOrderActivity.this, OrderListEmployeeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddOrderActivity.this, "Lỗi khi tạo đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}