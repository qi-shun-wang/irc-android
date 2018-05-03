package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ising99.intelligentremotecontrol.R;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class MediaShareSection extends StatelessSection {

    private String title;
    private List<MediaShareItem> list;
    private MediaShareSectionDelegate delegate;

    MediaShareSection(String title, List<MediaShareItem> list) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.section_media_share_item)
                .headerResourceId(R.layout.section_media_share_header)
                .build());

        this.title = title;
        this.list = list;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        MediaShareItem item = list.get(position);

        itemHolder.tvItem.setText(item.getTitle());
        itemHolder.imgItem.setImageResource(item.getImageResID());

        itemHolder.rootView.setOnClickListener(v -> {
            if (delegate == null) return;
            delegate.didSelectedAt(position);
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvTitle.setText(title);
    }

    public void setDelegate(MediaShareSectionDelegate delegate) {
        this.delegate = delegate;
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final ImageView imgItem;
        private final TextView tvItem;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            imgItem = view.findViewById(R.id.imgItem);
            tvItem = view.findViewById(R.id.tvItem);
        }
    }
}

