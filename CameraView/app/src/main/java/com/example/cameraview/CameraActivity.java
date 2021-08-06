package com.example.cameraview;

import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CameraActivity extends Activity
    implements CameraBridgeViewBase.CvCameraViewListener2
{
    private static final String TAG = "Main Activity";
    private Mat mRgba;
    private Mat mGrey;
    private CameraBridgeViewBase mOpenCvCameraView;
    private AgeGenderRecognition ageGenderRecognition;
    private EmotionRecognition emotionRecognition;
    private BaseLoaderCallback mBaseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {

            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG, "OpenCv is loaded");
                    mOpenCvCameraView.setCameraIndex(cameraIndex);
                    mOpenCvCameraView.setMaxFrameSize(960,1280);
                    mOpenCvCameraView.enableView();
                }
                default:{
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };
    private ImageView flipCamera;
    private ImageView gallery;
    private int cameraIndex;
    private ImageCapture imageCapture;
    private ImageView emotionIcon;
    private Drawable D;
    private int[] resourceBg;
    private FrameLayout emotionBackground;
    boolean isCaptured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_camera);

        imageCapture = ImageCapture.getInstance();
        cameraIndex = 1;
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.frameSurface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        emotionIcon = findViewById(R.id.emotionIcon);
        emotionBackground = findViewById(R.id.emotionBackground);
        D =  emotionIcon.getDrawable();

        flipCamera = findViewById(R.id.flipCamera);
        gallery = findViewById(R.id.gallery);

        resourceBg = new int[15];
        for(int i = 0; i < 15; i ++){
            String rs = "ic_bg" + String.valueOf(i+1);
            resourceBg[i] = drawableRecourse(rs);
            Log.d("res", rs);
            Log.d("res", String.valueOf(resourceBg[i]));
        }


        flipCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapCamera();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CameraActivity.this, Gallery.class));
            }
        });

        try {
            int inputSize = 48;
            /*ageGenderRecognition =
                    new AgeGenderRecognition(
                            getAssets(),
                            CameraActivity.this,
                            "age_model.tflite",
                            inputSize
                    );
                    */
            emotionRecognition =
                    new EmotionRecognition(
                            getAssets(),
                            CameraActivity.this,
                            "emotion_model.tflite",
                            inputSize
                    );
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void swapCamera() {
        cameraIndex = cameraIndex^1;
        mOpenCvCameraView.disableView();
        mOpenCvCameraView.setCameraIndex(cameraIndex);
        mOpenCvCameraView.enableView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG, "OpenCv is loaded");
            mBaseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            Log.d(TAG, "OpenCv failed to load");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mBaseLoaderCallback);
        }
        isCaptured = false;
        if (D instanceof AnimatedVectorDrawable){
            AnimatedVectorDrawable avd = (AnimatedVectorDrawable) D;
            avd.reset();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
        }
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mGrey = new Mat(height, width, CvType.CV_8UC1);
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
    }

    private int timeKeepers = 0;

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGrey = inputFrame.gray();
        if(cameraIndex == 1) {
            Core.flip(mRgba, mRgba, -1);
            Core.flip(mGrey, mGrey, -1);
        }
        Mat inputCopy = new Mat();
        Core.rotate(mRgba, inputCopy,Core.ROTATE_90_CLOCKWISE);
        mRgba = emotionRecognition.recognizeImage(mRgba);
        ImageCapture imageCapture = ImageCapture.getInstance();



        if(emotionRecognition.getResult() == 1){
            timeKeepers = (timeKeepers + 1);
        }
        else {
            timeKeepers = 0;
        }
        if(timeKeepers == 14){
            imageCapture.addImage(inputCopy);

            //Transform bitmap -> byte[]
            Bitmap bitmapLibrary = imageCapture.bmp;
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmapLibrary.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] image = byteArray.toByteArray();

            MainActivity2.database.INSERT_HISTORY(image);

            isCaptured = true;
            CameraActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    if (D instanceof AnimatedVectorDrawableCompat) {
                        AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) D;
                        avd.start();
                    }
                    else if (D instanceof AnimatedVectorDrawable){
                        AnimatedVectorDrawable avd = (AnimatedVectorDrawable) D;
                        avd.start();
                    }
                }
            });
        }
        CameraActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!isCaptured){
                    emotionBackground.setBackgroundResource(resourceBg[timeKeepers]);
                }else{
                    emotionBackground.setBackgroundResource(resourceBg[14]);
                }
            }
        });
        return mRgba;
    }

    protected int drawableRecourse(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());
    }


}















