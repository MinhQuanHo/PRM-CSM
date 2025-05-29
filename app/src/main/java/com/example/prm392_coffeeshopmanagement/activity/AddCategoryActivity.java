package com.example.prm392_coffeeshopmanagement.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_coffeeshopmanagement.R;
import com.example.prm392_coffeeshopmanagement.entity.Category;
import com.example.prm392_coffeeshopmanagement.entity.Product;
import com.example.prm392_coffeeshopmanagement.viewmodel.CategoryViewModel;
import com.example.prm392_coffeeshopmanagement.viewmodel.ProductViewModel;

import java.util.Date;
import java.util.List;

public class AddCategoryActivity extends BaseActivity {
    private EditText edtCategoryName;
    private Button btnSaveCategory;
    private CategoryViewModel categoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        edtCategoryName = findViewById(R.id.edtCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);

        categoryViewModel = new CategoryViewModel(getApplication());
        btnSaveCategory.setOnClickListener(v -> onSaveCategoryClicked());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_category;
    }
    private void onSaveCategoryClicked() {
        if (!validateRequiredFields()) {
            return;
        }

        try {
            String name = edtCategoryName.getText().toString().trim();
            Category newCategory = new Category();
            newCategory.setCategoryName(name);
            categoryViewModel.addCategory(newCategory);

            Toast.makeText(this, "Category added successfully", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateRequiredFields() {
        boolean isValid = true;
        if (edtCategoryName.getText().toString().trim().isEmpty()) {
            edtCategoryName.setError("Category name is required");
            return false;
        }
        List<Category> categories = categoryViewModel.getCategoryListByName(edtCategoryName.getText().toString().trim()).getValue();
        if (categories != null && !categories.isEmpty()) {
            edtCategoryName.setError("Category name already exists");
            return false;
        }
        return isValid;
    }
}