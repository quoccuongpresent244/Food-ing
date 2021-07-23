package com.example.cameraview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        ImageCapture imageCapture = ImageCapture.getInstance();
        ImageView imageView = findViewById(R.id.gallery);

        Bitmap bitmap = imageCapture.bmp;
        if(bitmap == null){
            imageView.setImageResource(R.drawable.ic_baseline_flip_camera_ios_24);
        }
        else
            imageView.setImageBitmap(bitmap);
    }

}