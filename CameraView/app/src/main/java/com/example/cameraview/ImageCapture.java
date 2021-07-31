package com.example.cameraview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class ImageCapture {
    private ArrayList<Mat> imageArray;
    public Mat image;
    public Bitmap bmp;
    private final int capacity;
    private ImageCapture(){
        imageArray = new ArrayList<>();
        image = null;
        capacity = 5;
    }
    private static ImageCapture single_instance = null;
    public static ImageCapture getInstance()
    {
        if (single_instance == null)
            single_instance = new ImageCapture();
        return single_instance;
    }

    public void addImage(Mat imageInput){
        if(image == null) {
            image = new Mat();
            image = imageInput;

            try {
                bmp = Bitmap.createBitmap(image.cols(), image.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(image, bmp);
            }
            catch (CvException e){
                Log.d("Exception",e.getMessage());
            }
        }

    }

    public void releaseImage(){
        image = null;
        bmp = null;
    }

    public ArrayList<Mat> getImageArray() {
        return imageArray;
    }

    public int getNumber(){
        return imageArray.size();
    }
}
