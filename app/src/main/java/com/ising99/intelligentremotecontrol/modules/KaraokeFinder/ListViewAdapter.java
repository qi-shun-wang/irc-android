package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    private List<Karaoke> items = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_karaoke, parent, false);

        return new ListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Karaoke item = items.get(position);
        holder.title.setText(item.getName());
        holder.subtitle.setText(item.getArtist());
        holder.type.setText(item.getType());
        holder.itemView.setBackgroundColor(position%2 == 0 ? Color.WHITE:Color.argb(1,13,13,13));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subtitle;
        TextView type;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.karaoke_list_item_title_text);
            subtitle = itemView.findViewById(R.id.karaoke_list_item_subtitle_text);
            type = itemView.findViewById(R.id.karaoke_list_item_type_text);
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

    public void setKaraokeAssets(List<Karaoke> items) {
        this.items = items;
    }

}
