package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/10.
 */

public class DMRListAdapter extends RecyclerView.Adapter<DMRListAdapter.ViewHolder>  {

    private List<RemoteDevice> devices = new ArrayList<RemoteDevice>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view

        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_dmr, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(devices.get(position).getDetails().getFriendlyName());
        holder.icon.setImageResource(R.drawable.kodpluswhite);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setDevices(List<RemoteDevice> devices) {
        this.devices = devices;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView icon;

        public ViewHolder(ViewGroup v) {
            super(v);
            this.title = v.findViewById(R.id.textView_dmr_title);
            this.icon = v.findViewById(R.id.imageView_dmr_icon);

        }
    }

}
