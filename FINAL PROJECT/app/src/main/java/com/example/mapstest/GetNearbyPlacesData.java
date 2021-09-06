package com.example.mapstest;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by navneet on 23/7/16.
 */
public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String url;
    ListData listData;
    double currLat;
    double currLong;

    public GetNearbyPlacesData(double lat, double Long){
        currLat = lat;
        currLong = Long;
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
            Log.d("GooglePlacesReadTask", googlePlacesData);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        listData = ListData.getInstance();
        String url = "https://maps.googleapis.com/maps/api/place/photo?";
        String key = "key=AIzaSyCovhdbC1vTq6W7nF3JHdOZ_5GiMOudCSk";
        String maxWidth = "maxwidth=400";
        url = url + maxWidth + "&" + key;
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            String urlTmp = url;
            Log.d("onPostExecute","Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            //Calculate distance
            float[] result = new float[1];
            Location.distanceBetween(currLat, currLong, lat, lng, result);
            String distance = String.format("%.1f", result[0]/1000) + " km";
            Log.d("DISTANCE", distance);

            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            String rating = googlePlace.get("rating");
            String open_now = googlePlace.get("open_now");
            String user_ratings_total = googlePlace.get("user_ratings_total");
            String photo_ref = googlePlace.get("photo_ref");
            String price_level = googlePlace.get("price_level");

            //String phoneNumber = googlePlace.get("phone_number");
            urlTmp = urlTmp + "&photo_reference=" + photo_ref;
            GetImageBitmap getImageBitmap = new GetImageBitmap(i);
            getImageBitmap.execute(urlTmp);

            listData.placeInfoList.add(new PlaceInfo(placeName, vicinity, rating, open_now, user_ratings_total, price_level, distance));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            mMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo((float) 13.5));
        }
    }



}
