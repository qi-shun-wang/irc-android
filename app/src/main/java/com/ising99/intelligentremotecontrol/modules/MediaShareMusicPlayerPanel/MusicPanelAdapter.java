package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

public class MusicPanelAdapter extends HeaderRecyclerViewAdapter<RecyclerView.ViewHolder,MusicPanelHeader,Music,MusicPanelHeader> {

    private MediaControlActionDelegate mcaDelegate;
    private BaseCollectionAdapterDelegate delegate;
    private int maxVolume = 0;
    private HeaderViewHolder headerViewHolder;

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder headerViewHolder = (ViewHolder) holder;
        Music asset = getItem(position);
        headerViewHolder.title.setText(asset.getTitle());
        headerViewHolder.subtitle.setText(asset.getArtist());
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        headerViewHolder = (HeaderViewHolder) holder;

        headerViewHolder.title.setText(getHeader().getAsset().getTitle());
        headerViewHolder.subtitle.setText(getHeader().getAsset().getArtist());
        headerViewHolder.media.setProgress(0);
        headerViewHolder.media.setMax((int)getHeader().getAsset().getDuration()/1000);
        headerViewHolder.volume.setMax(maxVolume);
        mcaDelegate.didLoadHolder();

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

    public void setMcaDelegate(MediaControlActionDelegate mcaDelegate) {
        this.mcaDelegate = mcaDelegate;
    }

    public void setMaxVolume(int maxVolume) {
        this.maxVolume = maxVolume;
    }

    public void updateCurrentPlaybackStatus(int resID){
        if (headerViewHolder != null) headerViewHolder.playback.setImageResource(resID);
    }

    public void updateCurrentVolume(int currentVolume) {
        if (headerViewHolder != null) headerViewHolder.volume.setProgress(currentVolume);
    }

    public void updateCurrentMedia(int timeInterval){
        if (headerViewHolder != null) headerViewHolder.media.setProgress(timeInterval/1000);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , SeekBar.OnSeekBarChangeListener {

        TextView title;
        TextView subtitle;
        ImageView thumbnail;
        ImageButton playback;
        ImageButton fastForward;
        ImageButton fastBackward;
        ImageButton cast;
        SeekBar media;
        SeekBar volume;

        HeaderViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.media_share_music_player_panel_item_title);
            subtitle = itemView.findViewById(R.id.media_share_music_player_panel_item_subtitle);
            thumbnail = itemView.findViewById(R.id.media_share_music_player_panel_album_icon);
            playback = itemView.findViewById(R.id.media_share_music_player_panel_playback_btn);
            cast = itemView.findViewById(R.id.media_share_music_player_panel_cast_btn);
            fastForward = itemView.findViewById(R.id.media_share_music_player_panel_fast_forward_btn);
            fastBackward = itemView.findViewById(R.id.media_share_music_player_panel_fast_backward_btn);
            media = itemView.findViewById(R.id.media_share_music_player_panel_media_seek_bar);
            volume = itemView.findViewById(R.id.media_share_music_player_panel_volume_seek_bar);
            media.setOnSeekBarChangeListener(this);
            volume.setOnSeekBarChangeListener(this);
            playback.setOnClickListener(this);
            cast.setOnClickListener(this);
            fastForward.setOnClickListener(this);
            fastBackward.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mcaDelegate != null){
                if (playback.equals(view)) mcaDelegate.didTapOnPlayback();
                if (fastForward.equals(view)) mcaDelegate.didTapOnFastForward();
                if (fastBackward.equals(view)) mcaDelegate.didTapOnFastBackward();
                if (cast.equals(view)) mcaDelegate.didTapOnCast();
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (media.equals(seekBar)) mcaDelegate.didMediaOnProgressChanged(i, b);
            if (volume.equals(seekBar)) {
                mcaDelegate.didVolumeOnProgressChanged(i, b);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            if (media.equals(seekBar)) mcaDelegate.didMediaOnStartTrackingTouch();
            if (volume.equals(seekBar)) mcaDelegate.didVolumeOnStartTrackingTouch();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (media.equals(seekBar)) mcaDelegate.didMediaOnStopTrackingTouch();
            if (volume.equals(seekBar)) mcaDelegate.didVolumeOnStopTrackingTouch();
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
