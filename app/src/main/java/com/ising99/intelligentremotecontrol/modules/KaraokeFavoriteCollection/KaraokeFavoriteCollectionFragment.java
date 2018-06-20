package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.model.KaraokeBookmark;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.panel.KaraokeBookmarkAdditionPanel;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.panel.KaraokeBookmarkAdditionPanelDelegate;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.ListViewAdapter;

import java.util.List;

/**
 * Created by Shun on 2018/6/11 下午 02:55:45.
 * .
 */

public class KaraokeFavoriteCollectionFragment extends Fragment implements KaraokeFavoriteCollectionContracts.View, BookmarkCollectionAdapterDelegate, KaraokeBookmarkAdditionPanelDelegate{

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView bookmarkList;
    private RecyclerView karaokeList;
    private ListViewAdapter adapter;
    private BookmarkListViewAdapter bookmarkAdapter;
    private KaraokeBookmarkAdditionPanel panel;

    public KaraokeFavoriteCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (Presenter) presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_karaoke_favorite_collection, container, false);
        bookmarkList = view.findViewById(R.id.karaoke_favorite_collection_bookmark_list_view);
        bookmarkAdapter = new BookmarkListViewAdapter();
        bookmarkAdapter.setupDelegate(this);
        RecyclerView.LayoutManager horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        bookmarkList.setHasFixedSize(true);
        bookmarkList.setLayoutManager(horizontal);
        bookmarkList.setAdapter(bookmarkAdapter);


        karaokeList = view.findViewById(R.id.karaoke_favorite_karaoke_list_view);
        adapter = new ListViewAdapter();
        adapter.setupDelegate(this);
        LinearLayoutManager vertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        karaokeList.setHasFixedSize(true);
        karaokeList.setLayoutManager(vertical);
        karaokeList.setAdapter(adapter);

        view.findViewById(R.id.karaoke_bookmark_edit_btn).setOnClickListener((btn -> {
            view.findViewById(R.id.karaoke_bookmark_name_text).setVisibility(View.GONE);
            view.findViewById(R.id.karaoke_bookmark_name_edit_text).setVisibility(View.VISIBLE);
        }));


        presenter.onCreate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getParent().equals(bookmarkList))
        {
            presenter.didSelectedAt(position);
        }
    }



    @Override
    public void didClickOnAddition() {
        view.findViewById(R.id.karaoke_favorite_collection_bookmark_container).setClickable(false);
        panel = new KaraokeBookmarkAdditionPanel();
        panel.setDelegate(this);
        getFragmentManager().beginTransaction().replace(R.id.karaoke_bookmark_panel_container, panel).commit();
    }

    @Override
    public void reloadKaraokeList(List<Karaoke> karaokes) {
        adapter.setKaraokeAssets(karaokes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void reloadBookmarkList(List<KaraokeBookmark> bookmarks) {
        bookmarkAdapter.setBookmarks(bookmarks);
        bookmarkAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateBookmarkToolBar(String name) {
        ((TextView)view.findViewById(R.id.karaoke_bookmark_name_text)).setText(name);
    }


    @Override
    public void didCancel() {
        dismissPanel();
    }

    @Override
    public void didCommit(String name) {
        presenter.performBookmarkCreation(name);
        dismissPanel();
    }

    private void dismissPanel(){
        view.findViewById(R.id.karaoke_favorite_collection_bookmark_container).setClickable(true);
        getFragmentManager().beginTransaction().remove(panel).commit();
    }
}
