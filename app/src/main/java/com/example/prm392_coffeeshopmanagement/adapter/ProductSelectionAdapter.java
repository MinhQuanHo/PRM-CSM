package com.example.prm392_coffeeshopmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.utils.FormartCurrency;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.ProductViewHolder> {
    private List<Product> products;
    private List<OrderDetail> selectedOrderDetails;

    public ProductSelectionAdapter(List<Product> products, List<OrderDetail> selectedOrderDetails) {
        this.products = products;
        this.selectedOrderDetails = selectedOrderDetails;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_order, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvProductName.setText(product.getProductName());
        holder.tvProductPrice.setText(FormartCurrency.formatVNCurrency(product.getProductPrice()));
        Picasso.get().load(product.getProductImage()).into(holder.ivProductImage);

        // Lấy số lượng hiện tại từ OrderDetail hoặc đặt mặc định là 0
        OrderDetail existingDetail = selectedOrderDetails.stream()
                .filter(detail -> detail.getProductId() == product.getProductId())
                .findFirst()
                .orElse(null);
        int initialQuantity = (existingDetail != null) ? existingDetail.getQuantity() : 0;
        holder.tvQuantity.setText(String.valueOf(initialQuantity));

        // Xử lý nút giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            if (currentQuantity > 0) {
                currentQuantity--;
                holder.tvQuantity.setText(String.valueOf(currentQuantity));
                updateOrderDetail(holder, product, currentQuantity);
            }
        });

        // Xử lý nút tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.tvQuantity.getText().toString());
            currentQuantity++;
            holder.tvQuantity.setText(String.valueOf(currentQuantity));
            updateOrderDetail(holder, product, currentQuantity);
        });
    }

    private void updateOrderDetail(ProductViewHolder holder, Product product, int quantity) {
        OrderDetail existingDetail = selectedOrderDetails.stream()
                .filter(detail -> detail.getProductId() == product.getProductId())
                .findFirst()
                .orElse(null);

        if (quantity > 0) {
            if (existingDetail == null) {
                OrderDetail newDetail = new OrderDetail(0, 0, product.getProductId(), product.getProductPrice(), quantity);
                selectedOrderDetails.add(newDetail);
            } else {
                existingDetail.setQuantity(quantity);
            }
        } else if (existingDetail != null) {
            selectedOrderDetails.remove(existingDetail);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductPrice, tvQuantity;
        Button btnDecrease, btnIncrease;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
        }
    }
}