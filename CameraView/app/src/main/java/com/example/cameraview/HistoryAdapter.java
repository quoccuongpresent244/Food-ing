package com.example.cameraview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private MainActivity2 context;
    private List<HistoryItem> historyItemList;
    private View view;


    public HistoryAdapter(Context context, List<HistoryItem> historyItemList) {
        this.context = (MainActivity2) context;
        this.historyItemList = historyItemList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryItem item = historyItemList.get(position);


        //transform byte[] bitmap
        byte[] image = item.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.historyImageView.setImageBitmap(bitmap);
        //loadDecodedImage(holder.historyBtn, R.id.historyBtn, R.drawable.delete, 30, 30);
        holder.historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2.database.INSERT_FAVORITE(image);
                Toast.makeText(context, "Added to favorite list", Toast.LENGTH_SHORT).show();
                //QLibraryActivity.database.QueryData("DELETE FROM History WHERE Id = '"+ item.getId() +"'");
                //context.DialogDelete(item.getId());
            }
        });
    }


        @Override
        public int getItemCount () {
            return historyItemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            Button historyBtn;
            ImageView historyImageView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                historyBtn = itemView.findViewById(R.id.historyBtn);
                historyImageView = itemView.findViewById(R.id.historyImageView);

            }
        }
    }
