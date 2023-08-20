package com.example.mystateapp.Adapters;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGImageView;
import com.caverock.androidsvg.SVGParseException;
import com.example.mystateapp.Activities.MainActivity;

import com.example.mystateapp.Models.State;
import com.example.mystateapp.R;
import com.example.mystateapp.Services.DataService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    private ArrayList<State> dataSet;
    private MainActivity mainActivity;

    public StateAdapter(ArrayList<State> dataSet, MainActivity mainActivity) {
        this.dataSet = dataSet;
        this.mainActivity = mainActivity;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // describes how we present each arr element in the recycleView
        TextView textViewName = holder.textName;
        TextView textViewNativeName = holder.textNativeName;
        SVGImageView svgImageViewFlag = holder.svgImageFlag;

        textViewName.setText("Name: " + dataSet.get(position).getName());
        textViewNativeName.setText("Native Name: " + dataSet.get(position).getNativeName());
        List<String> borders = dataSet.get(position).getBorders();

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
        svgImageViewFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bordersString;
                List<String> borders = dataSet.get(position).getBorders();
                if (borders != null && !borders.isEmpty()) {
                    // Convert the list of borders to a single string
                    bordersString = TextUtils.join(", ", borders);
                }
                else {
                    bordersString = "None";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                View dialogView = LayoutInflater.from(mainActivity).inflate(R.layout.popup_layout, null);
                TextView textViewBordersTitle = dialogView.findViewById(R.id.textViewBordersTitle);
                TextView textViewBorders = dialogView.findViewById(R.id.textViewBorders);

                textViewBorders.setText(bordersString);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() { // items count in recycle view in real time
        return dataSet.size();
    }

    public void setDataSet(ArrayList<State> filteredList) {
        dataSet = filteredList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // imports all existing variables in our xml for a single row

        TextView textName, textNativeName;
        SVGImageView svgImageFlag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textViewName);
            textNativeName = itemView.findViewById(R.id.textViewNativeName);
            svgImageFlag = itemView.findViewById(R.id.svgImageViewFlag);
        }
    }

}
