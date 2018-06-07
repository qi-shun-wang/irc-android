package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Area;
import com.ising99.intelligentremotecontrol.model.Artist;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Presenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shun on 2018/6/7 上午 09:33:08.
 * .
 */

public class KaraokeArtistFinderFragment extends Fragment implements KaraokeArtistFinderContracts.View ,BaseCollectionAdapterDelegate{

    private Presenter presenter;
    private ViewGroup view;
    private List<Button> types;
    private RecyclerView areaList;
    private RecyclerView artistList;
    private AreaListViewAdapter areaAdapter;
    private ArtistListViewAdapter artistAdapter;

    public KaraokeArtistFinderFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_karaoke_artist_finder, container, false);
        types = new ArrayList<>();
        types.add(view.findViewById(R.id.karaoke_artist_type_0));
        types.add(view.findViewById(R.id.karaoke_artist_type_1));
        types.add(view.findViewById(R.id.karaoke_artist_type_2));
        types.add(view.findViewById(R.id.karaoke_artist_type_3));
        types.add(view.findViewById(R.id.karaoke_artist_type_4));
        types.add(view.findViewById(R.id.karaoke_artist_type_5));
        for (int i = 0; i <types.size() ; i++) {
            Button type = types.get(i);
            type.setTag(i);
            type.setOnClickListener((btn -> presenter.performTypeSelectAt((int)btn.getTag())));
        }

        areaList = view.findViewById(R.id.karaoke_artist_areas_list_view);
        areaAdapter = new AreaListViewAdapter();
        areaAdapter.setupDelegate(this);
        LinearLayoutManager horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        areaList.setHasFixedSize(true);
        areaList.setLayoutManager(horizontal);
        areaList.setAdapter(areaAdapter);

        artistList = view.findViewById(R.id.karaoke_artist_artists_list_view);
        artistAdapter = new ArtistListViewAdapter();
        artistAdapter.setupDelegate(this);
        LinearLayoutManager vertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        artistList.setHasFixedSize(true);
        artistList.setLayoutManager(vertical);
        artistList.setAdapter(artistAdapter);

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
    public void updateTypeSelectedBackgroundAt(int tag) {
        Drawable selected =  getResources().getDrawable(R.drawable.karaoke_type_selected_icon);
        Drawable unselected =  getResources().getDrawable(R.drawable.karaoke_type_unselected_icon);
        getActivity().runOnUiThread(()->{
            for (int i = 0; i <types.size() ; i++) {
                if (i == tag)
                {
                    types.get(i).setBackground(selected);
                }
                else {
                    types.get(i).setBackground(unselected);
                }
            }

        });
    }

    @Override
    public void reloadAreaList(List<Area> areas) {
        areaAdapter.setAreas(areas);
        areaAdapter.notifyDataSetChanged();
    }

    @Override
    public void reloadArtistList(List<Artist> artists) {
        artistAdapter.setArtists(artists);
        artistAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getParent().equals(areaList))
        {
            presenter.performAreaSelectAt(position);
        }
        if (view.getParent().equals(artistList))
        {
            presenter.performArtistSelectAt(position);
        }
    }
}
