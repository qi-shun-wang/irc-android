package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/10.
 * .
 */

interface DMRListAdapterDelegate {
    void onItemClick(View view , int position);
}

public class DMRListAdapter extends RecyclerView.Adapter<DMRListAdapter.ViewHolder> implements View.OnClickListener {

    private List<RemoteDevice> devices = new ArrayList<>();

    void setupDelegate(DMRListAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    private DMRListAdapterDelegate delegate ;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_dmr, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(devices.get(position).getDetails().getFriendlyName());
        holder.icon.setImageResource(R.drawable.kodpluswhite);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    void setDevices(List<RemoteDevice> devices) {
        this.devices = devices;
    }

    @Override
    public void onClick(View view) {
        if(delegate!=null){
            delegate.onItemClick(view,(int)view.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public ImageView icon;

        ViewHolder(ViewGroup v) {
            super(v);
            this.title = v.findViewById(R.id.textView_dmr_title);
            this.icon = v.findViewById(R.id.imageView_dmr_icon);

        }
    }

}
