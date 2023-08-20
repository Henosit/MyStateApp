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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    private ArrayList<State> dataSet;

    public StateAdapter(ArrayList<State> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // always the same
        // inflating the layout makes it "exist"
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // describes how we present each arr element in the recycleView
        TextView textViewName = holder.textName;
        TextView textViewNativeName = holder.textNativeName;
        TextView textViewBorders = holder.textBorders;
        SVGImageView svgImageViewFlag = holder.svgImageFlag;

        textViewName.setText("Name: " + dataSet.get(position).getName());
        textViewNativeName.setText("Native Name: " + dataSet.get(position).getNativeName());
        List<String> borders = dataSet.get(position).getBorders();
        if (borders != null) {
            textViewBorders.setText("Borders: " + borders);
        }

        // Load and display SVG flag image
        String svgURL = dataSet.get(position).getFlag();
        try {
            URL url = new URL(svgURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            SVG svgFlag = SVG.getFromInputStream(inputStream);
            svgImageViewFlag.setSVG(svgFlag);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SVGParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() { // items count in recycle view in real time
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // imports all existing variables in our xml for a single row

        TextView textName, textNativeName, textBorders;
        SVGImageView svgImageFlag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            textNativeName = itemView.findViewById(R.id.textViewNativeName);
            textBorders = itemView.findViewById(R.id.textViewBorders);
            svgImageFlag = itemView.findViewById(R.id.svgImageViewFlag);
        }
    }

}
