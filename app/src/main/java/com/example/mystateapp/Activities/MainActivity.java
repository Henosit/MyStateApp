package com.example.mystateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mystateapp.Adapters.StateAdapter;
import com.example.mystateapp.Models.State;
import com.example.mystateapp.R;
import com.example.mystateapp.Services.DataService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private StateAdapter adapter;
    private ArrayList<State> dataSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the first RecyclerView and its adapter
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataSet = DataService.getArrState(); //an array that shrinks/grows in real time
//        borderAdapter = new BorderAdapter(dataSet);
        adapter = new StateAdapter(dataSet, this);
        recyclerView.setAdapter(adapter);
    }
}