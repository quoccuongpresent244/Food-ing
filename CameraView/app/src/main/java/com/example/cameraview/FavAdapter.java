package com.example.cameraview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    private MainActivity2 context;
    private List<HistoryItem> historyItemList;
    private View view;


    public FavAdapter(Context context, List<HistoryItem> historyItemList) {
        this.context = (MainActivity2) context;
        this.historyItemList = historyItemList;
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, int position) {
        HistoryItem item = historyItemList.get(position);


        //transform byte[] bitmap
        byte[] image = item.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.favImageView.setImageBitmap(bitmap);
        //loadDecodedImage(holder.historyBtn, R.id.historyBtn, R.drawable.delete, 30, 30);
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2.database.QueryData("DELETE FROM Favorite WHERE Id = '"+ item.getId() +"'");
                MainActivity2.favoriteFragment.UpdateData();
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

        Button favBtn;
        ImageView favImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favBtn = itemView.findViewById(R.id.favBtn);
            itemView.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favImageView = itemView.findViewById(R.id.favImageView);

        }
    }
}
