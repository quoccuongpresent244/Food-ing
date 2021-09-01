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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
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
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    protected class MyViewHolder extends  RecyclerView.ViewHolder{
        private LinearLayout expandableView;
        private FrameLayout title;
        private LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            expandableView = itemView.findViewById(R.id.expandable_view);
            title = itemView.findViewById(R.id.title);
            layout = itemView.findViewById(R.id.layout_tag);
            layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        }
    }
}
