package com.example.roomcatalog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "product",
        foreignKeys = @ForeignKey(
                entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id"))
public class Product {
    @PrimaryKey @NonNull
    int id;
    String name;
    @NonNull
    int category_id;


    public Product() {
        this.id = 100;
        this.name = "empty";
        this.category_id = 1;
    }

    public Product(@NonNull int id, String name, int category_id) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category_id=" + category_id +
                '}';
    }
}
