package com.example.mystateapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.example.mystateapp.Models.State;
import com.example.mystateapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BorderAdapter extends RecyclerView.Adapter<BorderAdapter.MyViewHolder> {

    private ArrayList<State> dataSet;
    public BorderAdapter(ArrayList<State> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // always the same
        // inflating the layout makes it "exist"
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.border_cards_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // describes how we present each arr element in the recycleView
        TextView textViewBorders = holder.textBorders;
        List<String> borders = dataSet.get(position).getBorders();
        if (borders != null) {
            textViewBorders.setText(borders+"");
        }
    }

    @Override
    public int getItemCount() { // items count in recycle view in real time
        return dataSet.size();
    }

    public void clearBorders() {
        dataSet.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // imports all existing variables in our xml for a single row

        TextView textBorders;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textBorders = itemView.findViewById(R.id.textViewBorders);
        }
    }

}
