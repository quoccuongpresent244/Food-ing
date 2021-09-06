package com.example.mapstest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;

public class ListData {
    public ArrayList<PlaceInfo> placeInfoList;
    private Adapter adapter;
    public Bitmap bitmapDefault;


    private ListData(){
        placeInfoList = new ArrayList<>();
    }

    private static ListData instance = null;

    public static ListData getInstance(){
        if(instance == null){
            instance = new ListData();
        }
        return instance;
    }

    public Adapter getAdapter(Context context) {

        adapter = new Adapter(this.placeInfoList, context);
        return adapter;
    }
}
