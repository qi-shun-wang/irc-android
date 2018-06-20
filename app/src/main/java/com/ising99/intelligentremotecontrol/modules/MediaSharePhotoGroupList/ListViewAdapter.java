package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

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

/**
 * Created by shun on 2018/5/3.
 * .
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {

    private BaseCollectionAdapterDelegate delegate;
    private List<String> groups = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_photo_group, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = groups.get(position);
        holder.title.setText(title);

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;


        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.list_item_photo_group_title);

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
    public void setGroups(List<String> groups)
    {
        this.groups.clear();
        this.groups.addAll(groups);
    }
}

