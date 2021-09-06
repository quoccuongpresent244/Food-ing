package com.example.mapstest;

import java.util.ArrayList;

public class ListData {
    public ArrayList<PlaceInfo> placeInfoList;
    private Adapter adapter;

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

    public Adapter getAdapter() {
        adapter = new Adapter(this.placeInfoList);
        return adapter;
    }

    public void resetData(){
        placeInfoList = new ArrayList<>();
    }
}
