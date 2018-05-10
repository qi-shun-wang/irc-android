package com.ising99.intelligentremotecontrol.modules.IRC.panel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/15.
 *
 */

public class ListViewAdapter extends  RecyclerView.Adapter<ListViewAdapter.ViewHolder>  {

    private BaseCollectionAdapterDelegate delegate;
    private List<ModeItem> modes = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_mode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModeItem mode = modes.get(position);
        holder.title.setText(mode.getTitle());
        holder.image.setImageResource(mode.getImageResId());
    }

    @Override
    public int getItemCount() {
        return modes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.irc_mode_list_item_title);
            image = itemView.findViewById(R.id.irc_mode_list_item_image);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }

    void setupDelegate(BaseCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }
    void setModes(List<ModeItem> modes) {
        this.modes = modes;
    }

}
