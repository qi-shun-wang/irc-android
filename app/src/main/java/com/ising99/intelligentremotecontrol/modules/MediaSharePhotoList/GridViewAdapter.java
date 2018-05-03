package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/3.
 * .
 */

public class GridViewAdapter extends RecyclerView.Adapter <GridViewAdapter.ViewHolder>{

    private List<Photo> collection = new ArrayList<>();
    private GridViewAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_photo, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = collection.get(position);
        Glide.with(holder.itemView).load(new File(photo.getLocalURL())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.media_share_photo_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }

    void setupDelegate(GridViewAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    void setPhotos(List<Photo> collection) {
        this.collection = collection;
    }

}

interface GridViewAdapterDelegate {
    void onItemClick(View view , int position);
}
