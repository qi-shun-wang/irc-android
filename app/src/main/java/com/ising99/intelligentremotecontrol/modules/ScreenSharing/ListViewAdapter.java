package com.ising99.intelligentremotecontrol.modules.ScreenSharing;

import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter <ListViewAdapter.ViewHolder>{

    private List<WifiP2pDevice> displays = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_karaoke, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        WifiP2pDevice item = displays.get(position);
        holder.title.setText(item.deviceName);
        holder.subtitle.setText(item.deviceAddress);
    }

    @Override
    public int getItemCount() {
        return displays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView subtitle;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.karaoke_list_item_title_text);
            subtitle = itemView.findViewById(R.id.karaoke_list_item_subtitle_text);
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

    public void setDisplays(List<WifiP2pDevice> assets) {
        this.displays.clear();
        this.displays.addAll(assets);
    }

}