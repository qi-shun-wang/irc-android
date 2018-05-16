package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

public class MusicPanelAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder,MusicPanelHeader,Music,MusicPanelHeader> {

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder headerViewHolder = (ViewHolder) holder;
        Music asset = getItem(position);
        headerViewHolder.title.setText(asset.getTitle());
        headerViewHolder.subtitle.setText(asset.getArtist());
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.title.setText("title");
        headerViewHolder.subtitle.setText("subtitle");
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_music, parent, false);

        return new ViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.media_share_music_panel_header_view, parent, false);
        return new HeaderViewHolder(view);
    }



    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView subtitle;
        ImageView thumbnail;

        HeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_player_panel_item_title);
            subtitle = itemView.findViewById(R.id.media_share_music_player_panel_item_subtitle);
            thumbnail = itemView.findViewById(R.id.media_share_music_player_panel_album_icon);
        }


    }


    public class ViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {

        TextView title;
        TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_item_title);
            subtitle = itemView.findViewById(R.id.media_share_music_item_subtitle);
//            itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
//        }
    }
}
