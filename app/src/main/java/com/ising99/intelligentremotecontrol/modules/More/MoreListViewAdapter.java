package com.ising99.intelligentremotecontrol.modules.More;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.ising99.intelligentremotecontrol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/29.
 *
 */

public class MoreListViewAdapter extends RecyclerView.Adapter <MoreListViewAdapter.ViewHolder>{

    private List<MoreItem> moreList = new ArrayList<>();
    private MoreListAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_more, parent, false);

        int height = parent.getMeasuredWidth()/2;
        view.setMinimumHeight(height);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoreItem more = moreList.get(position);
        holder.title.setText(more.getTitle());
        holder.image.setImageResource(more.getImageResId());
    }

    @Override
    public int getItemCount() {
        return moreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView_more_title);
            image = itemView.findViewById(R.id.imageView_more_icon);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (delegate != null) delegate.onItemClick(view, getAdapterPosition());
        }
    }

    void setupDelegate(MoreListAdapterDelegate delegate) {
        this.delegate = delegate;
    }
    void setMores(List<MoreItem> moreList) {
        this.moreList = moreList;
    }

}

interface MoreListAdapterDelegate {
    void onItemClick(View view , int position);
}