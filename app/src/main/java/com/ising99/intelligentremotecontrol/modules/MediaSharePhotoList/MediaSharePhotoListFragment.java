package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Presenter;

import java.util.List;

/**
 * Created by Shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListFragment extends Fragment implements MediaSharePhotoListContracts.View ,BaseCollectionAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private GridViewAdapter adapter;

    public MediaSharePhotoListFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_photo_list, container, false);
        RecyclerView listView = view.findViewById(R.id.media_share_photo_list_view);

        adapter = new GridViewAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL,false);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        adapter.setupDelegate(this);
        view.findViewById(R.id.media_share_photo_list_cast_btn).setOnClickListener((v)->presenter.didTapOnCast());

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
        presenter.didSelectedPhotoAt(position);
    }

    @Override
    public void reloadGridView(List<PhotoItem> collection) {
        adapter.setPhotos(collection);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showWarningBadge(String text) {
        getActivity().runOnUiThread(()->{
            //todo
            TextView warningText = view.findViewById(R.id.media_share_photo_list_warning_text);
            warningText.setBackgroundColor(Color.RED);
            warningText.setText(text);
            warningText.setAlpha(1);
        });

    }

    @Override
    public void hideWarningBadge(String text) {
        getActivity().runOnUiThread(()->{
            //todo
            TextView warningText = view.findViewById(R.id.media_share_photo_list_warning_text);
            warningText.setText(text);
            warningText.setBackgroundColor(Color.GREEN);
            warningText.animate().setDuration(5000).alpha(0).start();
        });
    }
}
