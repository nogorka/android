package com.example.roomcatalog;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Query("SELECT * FROM products")
    List<Product> getProducts();

    @Query("SELECT * FROM categories")
    List<Category> getCategories();

//    @Query("SELECT * FROM product WHERE category_id like :id")
//    List<Product> getAllProductByCategoryId(int id);
}
