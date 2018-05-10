package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/9.
 * .
 */

public class ListViewAdapter extends RecyclerView.Adapter <ListViewAdapter.ViewHolder>{

    private List<Video> assets = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_video, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video item = assets.get(position);
        holder.title.setText(item.getTitle());
        holder.thumbnail.setImageBitmap(ThumbnailUtils.createVideoThumbnail(item.getFilePath(), MediaStore.Video.Thumbnails.MICRO_KIND));
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_video_item_title);
            thumbnail = itemView.findViewById(R.id.media_share_video_item_thumbnail);
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

    public void setVideoAssets(List<Video> assets) {
        this.assets = assets;
    }

}
