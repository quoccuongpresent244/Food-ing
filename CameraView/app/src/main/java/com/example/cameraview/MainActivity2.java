package com.example.cameraview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.opencv.android.OpenCVLoader;

import java.util.Set;

public class MainActivity2 extends AppCompatActivity {

    static{
        if (OpenCVLoader.initDebug()){
            Log.d("Main Activity", "OpenCv is loaded");
        }
        else{
            Log.d("Main Activity", "OpenCv failed to load");
        }
    }

    private BottomAppBar appBar;
    private ImageView home, gallery, favorite, setting;
    private FloatingActionButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        assignItem();
        setSupportActionBar(appBar);

        HomeFragment homeFragment = new HomeFragment();
        GalleryFragment galleryFragment = new GalleryFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        SettingFragment settingFragment = new SettingFragment();

        setFragment(galleryFragment);

        home.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {setFragment(homeFragment);}});
        gallery.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {setFragment(galleryFragment);}});
        favorite.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {setFragment(favoriteFragment);}});
        setting.setOnClickListener(new View.OnClickListener() { @Override
            public void onClick(View v) {setFragment(settingFragment);}});
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, CameraActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void assignItem() {
        appBar = findViewById(R.id.appbar);
        home = findViewById(R.id.homeButton);
        favorite = findViewById(R.id.favoriteButton);
        gallery = findViewById(R.id.galleryButton);
        setting = findViewById(R.id.settingButton);
        camera = findViewById(R.id.cameraButton);
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}