package com.example.roomcatalog;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CatalogDAO {

    @Query("SELECT * FROM product")
    List<Product> getAllProducts();

    @Query("SELECT * FROM category")
    List<Category> getAllCategories();

    @Query("SELECT * FROM product WHERE category_id like :id")
    List<Product> getAllProductByCategoryId(int id);

}
