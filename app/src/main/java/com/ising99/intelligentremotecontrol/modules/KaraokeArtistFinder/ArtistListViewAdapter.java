package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Artist;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

public class ArtistListViewAdapter extends RecyclerView.Adapter<ArtistListViewAdapter.ViewHolder>  {

    private BaseCollectionAdapterDelegate delegate;
    private List<Artist> artists = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Artist artist = artists.get(position);
        holder.title.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.list_item_artist_title);

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
    public void setArtists(List<Artist> artists)
    {
        this.artists = artists;
    }
}
