package com.example.mapstest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;

public class MainActivity extends FragmentActivity{

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private AnimatedVectorDrawable menuAnimate;
    private AnimatedVectorDrawable menuAnimateRev;
    private boolean statusMenu = true;
    private boolean statusScroll = true;
    private ImageView[] button;
    private int[] imageResOff;
    private int[] imageResOn;
    private String[] title;
    private ImageView menu;
    private TextView titleMenu;
    private int currentPage = 0;
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingObject();
        setOnClick();
        setBitmapDefault();
    }

    private void setBitmapDefault() {
        ListData.getInstance().bitmapDefault = BitmapFactory.decodeResource(getResources(), R.drawable.bitmapdefault);
    }


    private void bindingObject(){
        menu = findViewById(R.id.menu_button);
        titleMenu = findViewById(R.id.title_menu);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        button = new ImageView[3];
        button[0] = findViewById(R.id.button1);
        button[1] = findViewById(R.id.button2);
        button[2] = findViewById(R.id.button3);
        imageResOff = new int[3];
        imageResOff[0] = R.drawable.home_off;
        imageResOff[1] = R.drawable.bookmark_off;
        imageResOff[2] = R.drawable.discovery_off;
        imageResOn = new int[3];
        imageResOn[0] = R.drawable.home_on;
        imageResOn[1] = R.drawable.bookmark_on;
        imageResOn[2] = R.drawable.discovery_on;

        title = new String[] {"Home", "Favorite","Discovery",};

        for (ImageView btn : button){
            btn.setAlpha(0.0f);
        }
        menuAnimate = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu);
        menuAnimateRev = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_rev);

        menu.setImageDrawable(menuAnimate);
    }

    private void setOnClick() {
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusMenu) {
                    menu.setImageDrawable(menuAnimate);
                    menuAnimate.start();
                    statusMenu = false;
                    setTitleMenu(false);
                    setButton(true);
                }
                else{
                    menu.setImageDrawable(menuAnimateRev);
                    menuAnimateRev.start();
                    statusMenu = true;
                    setTitleMenu(true);
                    setButton(false);
                }
            }
        });

        for (int i = 0; i < 3; i++){
            int finalI = i;
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("position", "1");
                    menu.setImageDrawable(menuAnimateRev);
                    menuAnimateRev.start();
                    statusMenu = true;
                    setButton(false);
                    titleMenu.setText(title[finalI]);
                    setTitleMenu(true);
                    button[currentPage].setImageDrawable(getDrawable(imageResOff[currentPage]));
                    button[finalI].setImageDrawable(getDrawable(imageResOn[finalI]));

                    viewPager.setCurrentItem(finalI, true);
                    currentPage = finalI;
                }
            });
        }
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                menu.setImageDrawable(menuAnimate);
                menuAnimate.start();
                setButton(true);

                titleMenu.animate().scaleX(0.0f)
                        .setListener(new Animator.AnimatorListener() {
                            @Override public void onAnimationStart(Animator animation) {}
                            @Override public void onAnimationCancel(Animator animation) {}
                            @Override public void onAnimationRepeat(Animator animation) {}
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                button[position].performClick();
                            }
                        });
                playAnimation(position);
            }
        });
    }

    private void playAnimation(int position) {
        if(position == 0){
            pagerAdapter = (FragmentStateAdapter) pagerAdapter;
            HomePage homePage = (HomePage) pagerAdapter.createFragment(0);
            homePage.setAnimation(R.anim.layout_animation);
        }

    }



    private class MyAnimatorListener implements Animator.AnimatorListener {
        private int TYPE;
        private View view;
        public MyAnimatorListener(int TYPE, View view){
            this.TYPE = TYPE;
            this.view = view;
        }
        @Override
        public void onAnimationStart(Animator animation) {
            if(TYPE == 0){
                view.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            if(TYPE == 1){
                view.setVisibility(View.GONE);
            }
            if (TYPE == 2){
                view.performClick();
            }
        }
        @Override public void onAnimationCancel(Animator animation) {}
        @Override public void onAnimationRepeat(Animator animation) {}
    }


    private void setButton(boolean status) {
        for (ImageView btn : button){
            if(status){
                btn.animate().alpha(1.0f)
                .setListener(new MyAnimatorListener(0,btn));
            }
            else{
                btn.animate().alpha(0.0f)
                .setListener(new MyAnimatorListener(1,btn));
            }
        }
    }
    private void setTitleMenu(boolean status){
        if (status) {
            titleMenu.animate().scaleX(1.0f)
                    .setListener(new MyAnimatorListener(0, titleMenu));
        }
        else {
            titleMenu.animate().scaleX(0.0f)
                    .setListener(new MyAnimatorListener(1, titleMenu));
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public HomePage homePage1 = new HomePage();
        public FavoriteFragment favoriteFragment = new FavoriteFragment();
        public FavoriteFragment favoriteFragment1 = new FavoriteFragment();

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if(position == 0) return homePage1;
            if(position == 1) return favoriteFragment;
            return favoriteFragment1;
        }

        @Override
        public int getItemCount() {
            return 3;
        }


    }
}