package com.example.listview;

import java.util.Comparator;

public class CompareName implements Comparator<User> {

    public int compare(User a, User b){

        return a.getName().compareTo(b.getName());
    }
}