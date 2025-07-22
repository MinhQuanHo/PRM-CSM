package com.example.prm392_coffeeshopmanagement.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.prm392_coffeeshopmanagement.adapter.OrderAdapter;
import com.example.prm392_coffeeshopmanagement.entity.Order;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.utils.FormartCurrency;
import com.example.prm392_coffeeshopmanagement.viewmodel.OrderDetailViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.OrderViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderDetailsActivity extends BaseActivity {
    RecyclerView recyclerView;
    ImageView imageAvatar;
    TextView textViewOrderId, textViewOrderDate, textViewPaymentMethod, textViewUserName, textViewOrderStatus;
    TextView textViewEmail, textViewAddress, textViewPhoneNumber, textViewTotalPrice, textViewTotalPayment;
    OrderViewModel orderViewModel;
    OrderDetailViewModel orderDetailViewModel;
    OrderAdapter orderAdapter;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_details;
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
        renderOrder();
    }

    private void initData() {
        recyclerView = findViewById(R.id.recyclerViewOrderDetail);
        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        textViewOrderId = findViewById(R.id.textViewOrderId);
        textViewOrderDate = findViewById(R.id.textViewOrderDate);
        textViewPaymentMethod = findViewById(R.id.textViewPaymentMethod);
        textViewUserName = findViewById(R.id.textViewUserName);

        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        textViewTotalPayment = findViewById(R.id.textViewTotalPayment);
        textViewOrderStatus = findViewById(R.id.textViewOrderStatus);

        orderViewModel = new OrderViewModel(getApplication());
        orderDetailViewModel = new OrderDetailViewModel(getApplication());
    }

    private void renderOrder() {
        int orderId = getIntent().getIntExtra("ORDER_ID", -1);
        if (orderId == -1) {
            Toast.makeText(this, "Order not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Order order = orderViewModel.getOrderById(orderId);
        if (order == null) return;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(order.getCreateAt());

        // Set data for order
        textViewOrderId.setText(String.valueOf(order.getOrderId()));
        textViewOrderDate.setText(formattedDate);
        textViewPaymentMethod.setText(order.getPaymentStatus());
        textViewUserName.setText(order.getCustomer());
        textViewPhoneNumber.setText("0123456789");
        textViewTotalPrice.setText(FormartCurrency.formatVNCurrency(order.getTotalPrice()));
        textViewTotalPayment.setText(FormartCurrency.formatVNCurrency(order.getTotalPrice()));
        textViewOrderStatus.setText(order.getStatus());

        List<OrderDetail> orderDetails = orderDetailViewModel
                .getOrderDetailsByOrderId(order.getOrderId());
        // Render recycler view
        orderAdapter = new OrderAdapter(orderDetails);
        recyclerView.setAdapter(orderAdapter);
    }

}