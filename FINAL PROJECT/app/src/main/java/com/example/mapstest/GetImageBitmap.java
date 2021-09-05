package com.example.mapstest;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class GetImageBitmap extends AsyncTask<String, Integer, Bitmap> {
    public Bitmap bitmap = null;
    public int index;

    public GetImageBitmap(int index){
        this.index = index;
    }

    @Override
    protected Bitmap doInBackground(String...url) {
        try{
            DownloadImage downloadImage = new DownloadImage();
            bitmap = downloadImage.downloadImage(url[0]);

        }
        catch (Exception e){
            Log.d("Exception Image", e.toString());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d("hahahahahaha", "onPostExecute Entered");
        ListData listData = ListData.getInstance();
        listData.placeInfoList.get(index).setPhoto(bitmap);
    }


}
