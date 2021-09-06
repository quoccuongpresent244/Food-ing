package com.example.mapstest;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

public class HomePage extends Fragment {
    RecyclerView recyclerView;
    ListData listData;
    ConstraintLayout constraintLayout;
    ViewPager2 viewPager;
    public HomePage(ViewPager2 viewPager){
        this.viewPager = viewPager;
    }
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
        constraintLayout = view.findViewById(R.id.goToMapView);
        view.findViewById(R.id.backToMapBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.gotoGuide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        return view;
    }



    public void setAnimation(int animResource) {
        if(listData!=null){
            Log.d("Khanh", "setAnimation: ");
            if (!listData.checkList()) {
                recyclerView.setVisibility(View.VISIBLE);
                constraintLayout.setVisibility(View.GONE);
                LayoutAnimationController layoutAnimationController
                        = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation);
                recyclerView.setLayoutAnimation(layoutAnimationController);
                recyclerView.setAdapter(listData.getAdapter(getContext()));
            }
        }

    }
}