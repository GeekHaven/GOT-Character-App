package com.example.gotcharacterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;

public class MainActivity extends AppCompatActivity {
    List<String> generalList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mTopToolbar;
        mTopToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mTopToolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String text_tobe_added ="Default";
        generalList.add("Default1");
        generalList.add("Default2");
        generalList.add("Default3");
        generalList.add("Default4");
        //Using recycler view
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,generalList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}