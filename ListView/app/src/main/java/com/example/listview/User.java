package com.example.listview;

public class User {
    String name, phoneNumber;
    Sex sex;

    public User(String name, String phoneNumber, Sex sex) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}