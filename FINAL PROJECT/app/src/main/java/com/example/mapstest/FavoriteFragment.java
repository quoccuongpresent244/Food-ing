package com.example.mapstest;

import android.animation.Animator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class FavoriteFragment extends Fragment {
    ImageView pagerImage[];
    FrameLayout text[];
    int currentState = 0;
    int currentText = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        pagerImage = new ImageView[4];
        pagerImage[0] = (ImageView) view.findViewById(R.id.pagerImage1);
        pagerImage[1] = (ImageView) view.findViewById(R.id.pagerImage2);
        pagerImage[2] = (ImageView) view.findViewById(R.id.pagerImage3);
        pagerImage[3] = (ImageView) view.findViewById(R.id.pagerImage4);

        text = new FrameLayout[4];
        text[0] = (FrameLayout) view.findViewById(R.id.guide1);
        text[1] = (FrameLayout) view.findViewById(R.id.guide2);
        text[2] = (FrameLayout) view.findViewById(R.id.guide3);
        text[3] = (FrameLayout) view.findViewById(R.id.guide4);

        setHidden();


        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImage();
            }
        });
        return view;
    }

    private void setHidden() {
        for(int i = 0; i < 4; i++){
            text[i].setVisibility(View.VISIBLE);
            pagerImage[i].setVisibility(View.VISIBLE);
            if(i != 0){
                text[i].setAlpha(0.0f);
                pagerImage[i].setAlpha(0.0f);
            }
        }

    }

    private void changeImage() {
        pagerImage[currentState].animate().alpha(0.0f)
                .setListener(new MyAnimatorListener(0));
        text[currentText].animate().alpha(0.0f)
                .setListener(new MyAnimatorListener(1));
        currentState = (currentState + 1)%4;
        currentText = (currentText + 1)%4;
    }

    private class MyAnimatorListener implements Animator.AnimatorListener {
        private int TYPE;
        public MyAnimatorListener(int TYPE){
            this.TYPE = TYPE;
        }
        @Override
        public void onAnimationStart(Animator animation) {
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            if(TYPE == 0){
                pagerImage[currentState].animate().alpha(1.0f);
            }
            if(TYPE == 1){

                text[currentText].animate().alpha(1.0f);
            }
        }
        @Override public void onAnimationCancel(Animator animation) {}
        @Override public void onAnimationRepeat(Animator animation) {}
    }
}