package com.example.mystateapp.Services;

import android.os.StrictMode;
import android.util.Log;

import com.example.mystateapp.Models.State;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DataService {

    public static ArrayList<State> getArrState() {

        ArrayList<State> arrState = new ArrayList<>();

        String sURL = "https://restcountries.com/v2/all?fields=name,nativeName,flag,borders";

        URL url = null;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            url = new URL(sURL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection request = null;
        try {
            request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement root = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonArray rootObj = root.getAsJsonArray();

            for ( JsonElement je : rootObj){

                JsonObject obj  = je.getAsJsonObject();
                JsonElement entryName = obj.get("name");
                JsonElement entryNative = obj.get("nativeName");
                JsonElement entryBorders = obj.get("borders");
                JsonElement entryFlag = obj.get("flag");

                String name = entryName.toString().replace("\"" , "");
                String nativeName = entryNative.toString().replace("\"" , "");
                String flag = entryFlag.toString().replace("\"" , "");

                ArrayList<String> arrBorders = new ArrayList<>();
                Log.d("result",name + " " + nativeName);
                if (entryBorders != null) {
                    JsonArray entryBorderArray = entryBorders.getAsJsonArray();

                    for (JsonElement j : entryBorderArray) {
                        String s = j.toString().replace("\"", "");
                        arrBorders.add(s);
                    }
                } else {
                    arrBorders = null;
                }
                arrState.add(new State(name, arrBorders, nativeName, flag));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arrState;
    }
}

