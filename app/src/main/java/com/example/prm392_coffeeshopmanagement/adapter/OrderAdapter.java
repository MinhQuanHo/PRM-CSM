package com.example.prm392_coffeeshopmanagement.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.entity.Category;
import com.example.prm392_coffeeshopmanagement.entity.OrderDetail;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.utils.FormartCurrency;
import com.example.prm392_coffeeshopmanagement.viewmodel.CategoryViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.ProductViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    List<OrderDetail> orderDetails;
    ProductViewModel productViewModel;
    CategoryViewModel categoryViewModel;

    public OrderAdapter(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);

        // Initialize userViewModel using ViewModelProvider
        productViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.itemView.getContext()).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider((ViewModelStoreOwner) holder.itemView.getContext()).get(CategoryViewModel.class);

        Product product = productViewModel.getProductById(orderDetail.getProductId());
        Category category = categoryViewModel.getCategoryById(product.getCategoryId());

        holder.name.setText(product.getProductName());
        holder.price.setText(FormartCurrency.formatVNCurrency(product.getProductPrice()));
        holder.quantitySize.setText("x" + orderDetail.getQuantity());
        Picasso.get().load(product.getProductImage()).into(holder.image);
        holder.toppings.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, toppings, quantitySize;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.textViewName);
            image = (ImageView) view.findViewById(R.id.imageProduct);
            price = (TextView) view.findViewById(R.id.textViewPrice);
            quantitySize = (TextView) view.findViewById(R.id.textViewQuantitySize);
            toppings = (TextView) view.findViewById(R.id.textViewToppings);
        }
    }
}
