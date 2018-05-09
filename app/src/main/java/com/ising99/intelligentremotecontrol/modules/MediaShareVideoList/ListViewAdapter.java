package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.GridViewAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/9.
 * .
 */

public class ListViewAdapter extends RecyclerView.Adapter <ListViewAdapter.ViewHolder>{

    private List<Video> assets = new ArrayList<>();
    private GridViewAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_music, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video item = assets.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_item_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }

    public void setupDelegate(GridViewAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public void setVideoAssets(List<Video> assets) {
        this.assets = assets;
    }

}
