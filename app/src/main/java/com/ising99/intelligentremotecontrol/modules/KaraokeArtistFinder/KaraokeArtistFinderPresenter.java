package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import com.ising99.intelligentremotecontrol.model.Area;
import com.ising99.intelligentremotecontrol.model.Artist;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.View;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/6/7 上午 09:33:08.
 * .
 */

public class KaraokeArtistFinderPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<Area> areas;
    private List<Artist> artists;

    KaraokeArtistFinderPresenter() {
        areas = new ArrayList<>();
        areas.add(new Area("台灣"));
        areas.add(new Area("香港"));
        areas.add(new Area("大陸"));
        areas.add(new Area("日韓/新馬"));
        areas.add(new Area("歐美"));
        areas.get(0).setSelected(true);

        artists = new ArrayList<>();
        artists.add(new Artist("早安少女"));
        artists.add(new Artist("謝金燕"));
//        artists.add(new Artist("小康妮"));
        artists.add(new Artist("費玉清"));
        artists.add(new Artist("吳亦凡"));
        artists.add(new Artist("阮經天"));
        artists.add(new Artist("迪克牛仔"));
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onResume() {
        view.updateTypeSelectedBackgroundAt(0);
        view.reloadAreaList(areas);
        view.reloadArtistList(artists);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void performTypeSelectAt(int tag) {
        view.updateTypeSelectedBackgroundAt(tag);
    }

    @Override
    public void performAreaSelectAt(int position) {
        for (Area area:areas){
            area.setSelected(false);
        }
        areas.get(position).setSelected(true);
        view.reloadAreaList(areas);
    }

    @Override
    public void performArtistSelectAt(int position) {

    }
}
