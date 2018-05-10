package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/3.
 * .
 */

public class GridViewAdapter extends RecyclerView.Adapter <GridViewAdapter.ViewHolder>{

    private List<PhotoItem> collection = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;

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
        PhotoItem item = collection.get(position);
        holder.selectedMark.setAlpha(item.isSelected()?1f:0f);
        Glide.with(holder.itemView).load(new File(item.getPhoto().getFilePath())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        ImageView selectedMark;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.media_share_photo_imageView);
            selectedMark = itemView.findViewById(R.id.media_share_photo_selected_imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }

    public void setupDelegate(BaseCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public void setPhotos(List<PhotoItem> collection) {
        this.collection = collection;
    }

}

