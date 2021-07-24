package com.example.cameraview;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class EmotionRecognition {
    private Interpreter interpreter;

    private int INPUT_SIZE;
    private float IMAGE_STD = 255.0f;
    private float IMAGE_MEAN = 0;
    private GpuDelegate gpuDelegate = null;

    private int height = 0;
    private int width = 0;
    private int result = 0;

    private CascadeClassifier cascadeClassifier;

    public int getResult() {
        return result;
    }

    public EmotionRecognition(AssetManager assetManager, Context context, String path, int inputSize)
            throws IOException
    {
        INPUT_SIZE = inputSize;
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate = new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(6);

        interpreter = new Interpreter(loadModelFile(assetManager, path), options);

        Log.d("AgeGenderRecognition", "CNN model is loaded");

        try{
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);

            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int byteRead;

            while((byteRead = is.read(buffer)) != -1){
                os.write(buffer,0,byteRead);
            }
            is.close();
            os.close();

            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            Log.d("AgeGenderRecognition", "CascadeClassifie model is loaded");

        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public Mat recognizeImage(Mat input){

        boolean allSmile = true;
        Core.flip(input.t(), input, 1);
        Mat grayScaleImage = new Mat();
        Imgproc.cvtColor(input, grayScaleImage, Imgproc.COLOR_RGBA2GRAY);

        height = grayScaleImage.height();
        width = grayScaleImage.width();

        int absoluteFaceSize = (int) (0.1 * height);
        MatOfRect faces = new MatOfRect();

        if(cascadeClassifier != null){
            cascadeClassifier.detectMultiScale(grayScaleImage,faces, 1.1, 2,
                    2, new Size(absoluteFaceSize, absoluteFaceSize), new Size());
        }

        Rect[] faceArray = faces.toArray();
        if (faceArray.length == 0){
            Core.flip(input.t(), input, 0);
            return input;
        }

        for (int i = 0; i < faceArray.length; i++){
            /*
            Imgproc.rectangle(input, faceArray[i].tl(), faceArray[i].br(),
                    new Scalar(0,255,255,0), 2);
             */

            Rect roi = new Rect((int) faceArray[i].tl().x, (int)faceArray[i].tl().y,
                    (int) faceArray[i].br().x -(int) faceArray[i].tl().x,
                    (int) faceArray[i].br().y - (int) faceArray[i].tl().y);
            Mat crop_rgba = new Mat(input, roi);
            Bitmap bitmap = null;
            bitmap = Bitmap.createBitmap(crop_rgba.cols(), crop_rgba.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(crop_rgba, bitmap);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 48,48,false);

            ByteBuffer byteBuffer = Bitmap2Buffer(scaledBitmap);

            float[][] emotion = new float[1][1];
            interpreter.run(byteBuffer, emotion);
            float emotion_float = (float) Array.get(Array.get(emotion,0),0);

            String emotion_string = get_emotion_string(emotion_float);
            if (!emotion_string.equals("Happy")){
                allSmile = false;
            }
            /*
            Imgproc.putText(input,emotion_string,faceArray[i].tl(),
                    1,1.5,
                    new Scalar(225,0,0,225), 2);
             */
        }
        Core.flip(input.t(), input, 0);
        if (allSmile){
            result = 1;
        }
        else{
            result = 0;
        }

        return input;
    }

    private String get_emotion_string(float emotion_float) {
        String res = "";
        if (emotion_float >= 0 && emotion_float < 0.5){
            res = "Surprise";
        }
        else if(emotion_float >= 0.5 && emotion_float < 1.5){
            res = "Fear";
        }
        else if (emotion_float >= 1.5 && emotion_float < 2.5){
            res = "Angry";
        }
        else if (emotion_float >= 2.5 && emotion_float < 3.5){
            res = "Neural";
        }
        else if (emotion_float >= 3.5 && emotion_float < 4.5){
            res = "Sad";
        }
        else if (emotion_float >= 4.5 && emotion_float < 5.2){
            res = "Disgust";
        }
        else {
            res = "Happy";
        }
        return res;
    }

    private ByteBuffer Bitmap2Buffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;

        int imageSize = 48;
        byteBuffer = ByteBuffer.allocateDirect(4*1*imageSize*imageSize*3);
        byteBuffer.order(ByteOrder.nativeOrder());

        int[] intValues = new int[imageSize*imageSize];
        scaledBitmap.getPixels(intValues,0, scaledBitmap.getWidth(),
                0,0,scaledBitmap.getWidth(), scaledBitmap.getHeight()
        );

        int pixel = 0;
        for(int i = 0; i < imageSize; i ++){
            for (int j = 0; j < imageSize; j++){
                final int val=intValues[pixel++];

                byteBuffer.putFloat((((val>>16)&0xFF))/255.0f);
                byteBuffer.putFloat((((val>>8)&0xFF))/255.0f);
                byteBuffer.putFloat(((val&0xFF))/255.0f);
            }
        }

        return byteBuffer;
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String path) throws IOException {
        AssetFileDescriptor assetFileDescriptor = assetManager.openFd(path);

        FileInputStream inputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = assetFileDescriptor.getStartOffset();
        long declareLength = assetFileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength);
    }


}