package com.example.prm392_coffeeshopmanagement.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_coffeeshopmanagement.dao.AppDatabase;
import com.example.prm392_coffeeshopmanagement.dao.CategoryDao;
import com.example.prm392_coffeeshopmanagement.dao.ProductDao;
import com.example.prm392_coffeeshopmanagement.entity.Category;
import com.example.prm392_coffeeshopmanagement.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ExecutorService executorService;

    public CategoryRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        categoryDao = db.categoryDao();
        productDao = db.productDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getCategorieList() {
        return categoryDao.getCategories();
    }

    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    public void deleteCategory(int categoryId, Consumer<Boolean> callback) {
        executorService.execute(() -> {
            int productCount = productDao.countProductsByCategoryId(categoryId);
            boolean isDeleted = false;
            if (productCount == 0) {
                categoryDao.deleteCategory(categoryId);
                isDeleted = true;
            }
            callback.accept(isDeleted);
        });
    }
    public void addCategory(Category category) {
        executorService.execute(() -> categoryDao.insert(category));
    }
    public void updateCategory(Category category) {
        executorService.execute(() -> categoryDao.update(category));
    }
    public LiveData<List<Category>> getCategoriesListByName(String name) {
        return categoryDao.getCategoriesListByName(name);
    }
}
