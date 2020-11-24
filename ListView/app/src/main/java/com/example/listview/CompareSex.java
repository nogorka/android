package com.example.listview;

import java.util.Comparator;

public class CompareSex implements Comparator<User> {

    public int compare(User a, User b) {

        return a.getSex().compareTo(b.getSex());
    }
}