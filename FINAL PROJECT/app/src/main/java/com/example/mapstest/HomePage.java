package com.example.mapstest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class HomePage extends Fragment {
    RecyclerView recyclerView;
    ListData listData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listData = ListData.getInstance();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void setAnimation(int animResource) {
        LayoutAnimationController layoutAnimationController
                = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);
        recyclerView.setLayoutAnimation(layoutAnimationController);

        
        recyclerView.setAdapter(listData.getAdapter(getContext()));
    }
}