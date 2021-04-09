package com.example.roomcatalog;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "category")
public class Category {

    @PrimaryKey
    @NonNull
    int id;
    String name;

    @Ignore
    public Category() {
        this.id = 100;
        this.name = "empty";
    }

    public Category(int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
