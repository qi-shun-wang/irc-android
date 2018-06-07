package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Area;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

public class AreaListViewAdapter extends RecyclerView.Adapter<AreaListViewAdapter.ViewHolder> {

    private List<Area> areas = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_area, parent, false);

        return new AreaListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area item = areas.get(position);
        holder.title.setText(item.getName());
        if (item.isSelected()) {
            holder.underline.setAlpha(1f);
        } else {
            holder.underline.setAlpha(0f);
        }
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        View underline;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.karaoke_artist_area_title);
            underline = itemView.findViewById(R.id.karaoke_artist_area_underline);
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

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
