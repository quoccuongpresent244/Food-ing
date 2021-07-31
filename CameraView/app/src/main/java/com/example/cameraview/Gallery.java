package com.example.cameraview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gallery extends AppCompatActivity {

    ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageCapture = ImageCapture.getInstance();
        ImageView imageView = findViewById(R.id.gallery);

        Bitmap bitmap = imageCapture.bmp;
        if(bitmap == null){
            imageView.setImageResource(R.drawable.ic_baseline_flip_camera_ios_24);
        }
        else
            imageView.setImageBitmap(bitmap);

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageToGallery(imageCapture.image);
            }
        });
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.releaseImage();
            }
        });

    }

    private void saveImageToGallery(Mat image) {
        Imgproc.cvtColor(image,image,Imgproc.COLOR_RGBA2BGRA);
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/QLKTeam");

        boolean success = false;
        if(!folder.exists()){
            success = folder.mkdirs();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = sdf.format(new Date());
        String fileName = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath()+ "/QLKTeam/" +
                currentDateTime + ".jpg";
        Imgcodecs.imwrite(fileName, image);
        imageCapture.releaseImage();

        Log.d("Save path", getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath() + "/QLKTeam");
        
        finish();
    }

}