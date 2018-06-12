package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.KaraokeBookmark;

import java.util.ArrayList;
import java.util.List;

public class BookmarkListViewAdapter extends RecyclerView.Adapter <BookmarkListViewAdapter.ViewHolder> {

    private List<KaraokeBookmark> items = new ArrayList<>();
    private BookmarkCollectionAdapterDelegate delegate;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_karaoke_bookmark, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        KaraokeBookmark item = items.get(position);
        holder.title.setText(item.getName());

        holder.itemView.setBackground(item.isSelected() ? holder.selected : item.isEmpty() ? holder.add : holder.unselected);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        Drawable selected;
        Drawable unselected;
        Drawable add;

        ViewHolder(View itemView) {
            super(itemView);
            selected = itemView.getResources().getDrawable( R.drawable.karaoke_bookmark_selected);
            unselected = itemView.getResources().getDrawable( R.drawable.karaoke_bookmark_unselected);
            add = itemView.getResources().getDrawable( R.drawable.karaoke_bookmark_add);
            title = itemView.findViewById(R.id.list_item_karaoke_bookmark_title_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (delegate != null) {
                if (getAdapterPosition() == items.size()-1)
                {
                    delegate.didClickOnAddition();
                }
                else
                {
                    delegate.onItemClick(view, getAdapterPosition());
                }
            }
        }
    }

    public void setupDelegate(BookmarkCollectionAdapterDelegate delegate) {
        this.delegate = delegate;
    }

    public void setBookmarks(List<KaraokeBookmark> items) {
        this.items.clear();
        this.items.addAll(items);
        this.items.add(new KaraokeBookmark(true));
    }
}
