package com.example.mystateapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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
    private Toolbar toolbar;


    @SuppressLint("MissingInflatedId")
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
        adapter = new StateAdapter(dataSet, this);
        recyclerView.setAdapter(adapter);

        // Find the Toolbar by its ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the Toolbar as the support action bar
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        // Set a listener to respond to text changes in the SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false; // Handle search submission if needed
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter your dataset based on the newText
                ArrayList<State> filteredList = new ArrayList<>();
                for (State state : dataSet) {
                    if (state.getName().toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(state);
                    }
                }

                // Update your RecyclerView with the filtered data
                adapter.setDataSet(filteredList);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}