package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

public class MusicPanelAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder,MusicPanelHeader,Music,MusicPanelHeader> {
    private MediaControlActionDelegate mcaDelegate;
    private BaseCollectionAdapterDelegate delegate;
    private int resId;

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

        headerViewHolder.title.setText(getHeader().getAsset().getTitle());
        headerViewHolder.subtitle.setText(getHeader().getAsset().getArtist());
        headerViewHolder.playback.setImageResource(resId);
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

    public void setDelegate(BaseCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setMcaDelegate(MediaControlActionDelegate mcaDelegate) {
        this.mcaDelegate = mcaDelegate;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subtitle;
        ImageView thumbnail;
        ImageButton playback;
        ImageButton fastForward;
        ImageButton fastBackward;

        HeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_player_panel_item_title);
            subtitle = itemView.findViewById(R.id.media_share_music_player_panel_item_subtitle);
            thumbnail = itemView.findViewById(R.id.media_share_music_player_panel_album_icon);
            playback = itemView.findViewById(R.id.media_share_music_player_panel_playback_btn);
            fastForward = itemView.findViewById(R.id.media_share_music_player_panel_fast_forward_btn);
            fastBackward = itemView.findViewById(R.id.media_share_music_player_panel_fast_backward_btn);
            playback.setOnClickListener(this);
            fastForward.setOnClickListener(this);
            fastBackward.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mcaDelegate != null){
                if (playback.equals(view)) mcaDelegate.didTapOnPlayback();
                if (fastForward.equals(view)) mcaDelegate.didTapOnFastForward();
                if (fastBackward.equals(view)) mcaDelegate.didTapOnFastBackward();
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_item_title);
            subtitle = itemView.findViewById(R.id.media_share_music_item_subtitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }
}
