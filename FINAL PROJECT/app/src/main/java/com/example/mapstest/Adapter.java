package com.example.mapstest;

import android.animation.LayoutTransition;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private ArrayList<PlaceInfo> data;

    public Adapter(ArrayList<PlaceInfo> data){
        this.data = data;
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
        holder.userRatingText.setText(data.get(position).getUser_ratings_total());
        holder.ratingText.setText(data.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        if (data != null){
            return data.size();
        }
        return 1;
    }

    protected class MyViewHolder extends  RecyclerView.ViewHolder{
        private LinearLayout expandableView;
        private FrameLayout title;
        private LinearLayout layout;
        private TextView titleText, addressText, hourText, userRatingText, ratingText, distanceText;

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
        }
    }
}
