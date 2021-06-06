package com.example.implicitintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;
    private EditText mHour;
    private EditText mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareTextEditText = findViewById(R.id.share_edittext);
        mHour = findViewById(R.id.alarm_hour);
        mMinute = findViewById(R.id.alarm_minute);
    }

    public void openWebsite(View view) {
        //get url and parse uri
        String url = mWebsiteEditText.getText().toString();
        Uri webpage = Uri.parse(url);

        //create intent
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        //// Find an activity to hand the intent and start that activity
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void openLocation(View view) {
        String loc = mLocationEditText.getText().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    public void shareText(View view) {
        String text = mShareTextEditText.getText().toString();
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                   .from(this)
                   .setType(mimeType)
                   .setChooserTitle("Share this text with: ")
                   .setText(text)
                   .startChooser();
    }

    public void openAlarm(View view) {
        if(!mHour.getText().toString().isEmpty() && !mHour.getText().toString().isEmpty()) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
            intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(mHour.getText().toString()));
            intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(mMinute.getText().toString()));
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Set alarm");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        }
        else{
            Log.d("ImplicitIntents", "Please enter number");
        }
    }

    public void openAlarm_tp(View view) {

    }
}