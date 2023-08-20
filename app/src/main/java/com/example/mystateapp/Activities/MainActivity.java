package com.example.mystateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;

import com.example.mystateapp.Adapters.BorderAdapter;
import com.example.mystateapp.Adapters.StateAdapter;
import com.example.mystateapp.Models.State;
import com.example.mystateapp.R;
import com.example.mystateapp.Services.DataService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, bordersRecyclerView;
    private LinearLayoutManager layoutManager, bordersLayoutManager;
    private StateAdapter adapter;

    public BorderAdapter getBorderAdapter() {
        return borderAdapter;
    }

    private BorderAdapter borderAdapter;
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
//
//        bordersRecyclerView = (RecyclerView) findViewById(R.id.bordersRecView);
//        bordersLayoutManager = new LinearLayoutManager(this);
//        bordersRecyclerView.setLayoutManager(bordersLayoutManager);
//        bordersRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        bordersRecyclerView.setAdapter(borderAdapter);
    }

    public void changeBordersRecyclerViewVisibility() {
        int visibilityMode = bordersRecyclerView.getVisibility();
        if (visibilityMode == View.GONE) {
            bordersRecyclerView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bordersRecyclerView.setVisibility(View.GONE);
                    borderAdapter.clearBorders(); // Clear the borders data in BorderAdapter
                }
            }, 3000); // 3000 milliseconds = 3 seconds
        } else if (visibilityMode == View.VISIBLE) {
            bordersRecyclerView.setVisibility(View.GONE);
            borderAdapter.clearBorders(); // Clear the borders data in BorderAdapter
        }
    }

}