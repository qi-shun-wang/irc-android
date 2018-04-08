package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;

import java.util.ArrayList;

/**
 * Created by shun on 2018/3/29.
 *
 */

public class GridViewAdapter extends BaseAdapter {

    private Handler handler = new Handler();
    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<>();

    void append(ImageItem item){
        data.add(item);
    }
    void removeAll(){
        data.clear();
    }

    GridViewAdapter(Context context,int layoutResourceId){
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = row.findViewById(R.id.text);
            holder.image = row.findViewById(R.id.image);
            holder.finger = row.findViewById(R.id.finger);
            holder.ring = row.findViewById(R.id.ring);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = data.get(position);
        holder.imageTitle.setText(item.getTitle());
        holder.image.setImageBitmap(item.getImage());
        holder.ring.setBackgroundResource(R.drawable.anim_ring);
        holder.finger.setBackgroundResource(R.drawable.anim_finger);

        handler.post(() -> {
            ((AnimationDrawable)holder.finger.getBackground()).start();
            ((AnimationDrawable)holder.ring.getBackground()).start();
        });
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        ImageView finger;
        ImageView ring;
    }


}
