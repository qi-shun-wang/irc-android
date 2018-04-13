package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/3/29.
 *
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private DMRListAdapterDelegate delegate;
    private List<Device> devices = new ArrayList<>();
    private Handler handler = new Handler();

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_device, parent, false);
        return new ViewHolder(view);

    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Device device = devices.get(position);

        holder.imageTitle.setText(device.getName());

        holder.image.setImageResource(R.drawable.kodpluswhite);
        holder.ring.setBackgroundResource(R.drawable.anim_ring);
        holder.finger.setBackgroundResource(R.drawable.anim_finger);

        handler.post(() -> {
            ((AnimationDrawable)holder.finger.getBackground()).start();
            ((AnimationDrawable)holder.ring.getBackground()).start();
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return devices.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView imageTitle;
        ImageView image;
        ImageView finger;
        ImageView ring;
        ViewHolder(View itemView) {
            super(itemView);

            imageTitle = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
            finger = itemView.findViewById(R.id.finger);
            ring = itemView.findViewById(R.id.ring);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }



    void setupDelegate(DMRListAdapterDelegate delegate) {
        this.delegate = delegate;
    }
    void setDevices(List<Device> devices) {
        this.devices = devices;
    }


}

interface DMRListAdapterDelegate {
    void onItemClick(View view , int position);
}