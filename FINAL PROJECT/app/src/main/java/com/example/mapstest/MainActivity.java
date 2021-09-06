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
    private ImageView[] indicator;

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
        indicator = new ImageView[3];
        indicator[0] = findViewById(R.id.indicator1);
        indicator[1] = findViewById(R.id.indicator3);
        indicator[2] = findViewById(R.id.indicator2);
        for(ImageView im : indicator){
            im.setVisibility(View.VISIBLE);
            im.setScaleX(0.0f);
        }
        indicator[0].setScaleX(1.0f);

        title = new String[] {"Home", "Favorite","Discovery",};

        menuAnimate = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu);
        menuAnimateRev = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_rev);

        menu.setImageDrawable(menuAnimate);
    }

    private void setOnClick() {

        for (int i = 0; i < 3; i++){
            int finalI = i;
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    statusMenu = true;
                    setButton(finalI);
                    setTitleMenu(title[finalI]);
                    animChange(currentPage);
                    animChangeRev(finalI);
                    viewPager.setCurrentItem(finalI, true);
                    currentPage = finalI;
                }
            });
        }
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                playAnimation(position);
                animChange(currentPage);

                setButton(position);
                setTitleMenu(title[position]);
                animChangeRev(position);
                currentPage = position;
            }
        });

    }

    private void animChangeRev(int position) {
        button[position].animate().scaleX(0.0f)
                .setListener(new MyAnimatorListener(0,button[position],position));
    }

    private void animChange(int position) {
        button[position].animate().scaleX(0.0f)
                .setListener(new MyAnimatorListener(1,button[position],position));
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
        private String string;
        private int position;
        public MyAnimatorListener(int TYPE, View view, String string){
            this.TYPE = TYPE;
            this.view = view;
            this.string = string;
        }
        public MyAnimatorListener(int TYPE, View view, int position){
            this.TYPE = TYPE;
            this.view = view;
            this.position = position;
        }
        @Override
        public void onAnimationStart(Animator animation) {
        }
        @Override
        public void onAnimationEnd(Animator animation) {
            if(TYPE == 0){
                ((ImageView)view).setImageDrawable(getDrawable(imageResOn[position]));
                button[position].animate().scaleX(1.0f);
            }
            if(TYPE == 1){
                ((ImageView)view).setImageDrawable(getDrawable(imageResOff[position]));
                button[position].animate().scaleX(1.0f);
            }
            if (TYPE == 2){
                ((TextView) view).setText(string);
                view.animate().scaleX(1.0f);
            }
        }
        @Override public void onAnimationCancel(Animator animation) {}
        @Override public void onAnimationRepeat(Animator animation) {}
    }


    private void setButton(int position) {
        for (int i = 0; i < 3; i++){
            if(i == position){
                indicator[i].animate().scaleX(1.0f);
            }
            else{
                indicator[i].animate().scaleX(0.0f);
            }
        }
    }
    private void setTitleMenu(String title){
        titleMenu.animate().scaleX(0.0f).setListener(new MyAnimatorListener(2,titleMenu, title));
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