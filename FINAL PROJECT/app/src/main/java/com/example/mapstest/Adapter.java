package com.example.mapstest;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private ArrayList<PlaceInfo> data;
    private Context context;

    public Adapter(ArrayList<PlaceInfo> data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.expandableView.setVisibility(View.GONE);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(holder.layout, new AutoTransition());
                holder.expandableView.setVisibility(
                        holder.expandableView.getVisibility() == View.GONE?
                                View.VISIBLE:View.GONE
                );
            }
        });

        holder.titleText.setText(data.get(position).getName());
        holder.addressText.setText(data.get(position).getVicinity());
        holder.hourText.setText(
                    (!data.get(position).getOpen_now().equals("false"))?
                            "OPEN" : "CLOSE"
                );
        holder.userRatingText.setText("Total Rating: " + data.get(position).getUser_ratings_total());
        holder.ratingText.setText("Rating: " +data.get(position).getRating());
        if(data.get(position).getPhoto() == null){
            holder.photoView.setImageBitmap(ListData.getInstance().bitmapDefault);
        }
        else{
            holder.photoView.setImageBitmap(data.get(position).getPhoto());
        }
        holder.titleText.setText(data.get(position).getName());
        holder.distanceText.setText("Distance: "+ data.get(position).getDistance());

        holder.goHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //string url
                String lat = data.get(position).getLat();
                String lng = data.get(position).getLng();
                String url = "google.navigation:q=" + lat + ", " + lng + "&mode=l";

                Uri gmmIntentUri = Uri.parse(url);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });




    }

    @Override
    public int getItemCount() {
        if (data != null){
            return data.size();
        }
        return 1;
    }

    protected class MyViewHolder extends  RecyclerView.ViewHolder{
        private ConstraintLayout expandableView;
        private FrameLayout title;
        private LinearLayout layout;
        private TextView titleText, addressText, hourText, userRatingText, ratingText, distanceText;
        private ImageView photoView, goHereBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            expandableView = itemView.findViewById(R.id.expandable_view);
            title = itemView.findViewById(R.id.title);
            layout = itemView.findViewById(R.id.layout_tag);
            layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

            titleText = itemView.findViewById(R.id.title_text);
            addressText = itemView.findViewById(R.id.address);
            hourText = itemView.findViewById(R.id.hour_open);
            userRatingText = itemView.findViewById(R.id.user_rating);
            ratingText = itemView.findViewById(R.id.rating);
            photoView = itemView.findViewById(R.id.photo);
            distanceText = itemView.findViewById(R.id.distance);
            goHereBtn = itemView.findViewById(R.id.gohereBtn);

        }
    }
}
