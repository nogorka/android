package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    UserListAdapter adapter;
    ListView listView;

    Button btnName;
    Button btnSex;
    Button btnPhone;

    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        //InputStream stream = getResources().openRawResource(R.raw.users);

        Gson gson = new Gson();
        try {
            InputStream stream = getAssets().open("users.json");
            User[] users_arr = gson.fromJson(new InputStreamReader(stream), User[].class);

            users = new ArrayList<>(Arrays.asList(users_arr));

            adapter = new UserListAdapter(this, users);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.name_sort: {
                Collections.sort(users, new CompareName());
                break;
            }
            case R.id.phone_sort: {
                Collections.sort(users, new ComparePhoneNumber());
                break;
            }
            case R.id.sex_sort: {
                Collections.sort(users, new CompareSex());
                break;
            }

        }

        adapter = new UserListAdapter(this, users);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }
}