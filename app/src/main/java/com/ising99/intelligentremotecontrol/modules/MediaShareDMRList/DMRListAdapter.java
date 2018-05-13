package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;

import java.util.ArrayList;
import java.util.List;

public class DMRListAdapter extends RecyclerView.Adapter<DMRListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Device> devices = new ArrayList<>();
    private BaseCollectionAdapterDelegate delegate ;

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
        String title;
        if(devices.get(position) instanceof LocalDevice)
        {
            title = "Android Phone";
        }
        else
        {
            title = devices.get(position).getDetails().getFriendlyName();
        }

        holder.title.setText(title);
        holder.icon.setImageResource(R.drawable.kodpluswhite);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public void onClick(View view) {
        if(delegate!=null){
            delegate.onItemClick(view,(int)view.getTag());
        }
    }

    public void setupDelegate(BaseCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }
    public void setDevices(List<Device> devices) {
        this.devices = devices;
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
