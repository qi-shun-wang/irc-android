package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/3/29.
 *
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private BaseCollectionAdapterDelegate delegate;
    private List<Device> devices = new ArrayList<>();
//    private Handler handler = new Handler();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_device, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Device device = devices.get(position);

        holder.imageTitle.setText(device.getName());
        Glide.with(holder.itemView.getContext()).load(R.drawable.kodpluswhite).into(holder.image);
//        holder.image.setImageResource(R.drawable.kodpluswhite);
//        holder.ring.setBackgroundResource(R.drawable.anim_ring);
//        holder.finger.setBackgroundResource(R.drawable.anim_finger);

//        handler.post(() -> {
//            ((AnimationDrawable)holder.finger.getBackground()).start();
//            ((AnimationDrawable)holder.ring.getBackground()).start();
//        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppCompatTextView imageTitle;
        ImageView image;
//        ImageView finger;
//        ImageView ring;

        ViewHolder(View itemView) {
            super(itemView);

            imageTitle = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
//            finger = itemView.findViewById(R.id.finger);
//            ring = itemView.findViewById(R.id.ring);

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
    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
