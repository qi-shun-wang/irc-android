package com.ising99.intelligentremotecontrol.modules.Karaoke;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.View;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/17 下午 10:03:05.
 * .
 */

public class KaraokePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<Karaoke> items = new ArrayList<>();

    KaraokePresenter() {
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
        items.add(new Karaoke("原來你什麼都想要","徐瑩佳","ＭＶ"));
        items.add(new Karaoke("身騎黑馬","徐瑩佳","導"));
        items.add(new Karaoke("白天很懂夜的美","徐瑩佳","導"));
        items.add(new Karaoke("執長太黑暗","端木輕薄","ＭＶ"));
        view.setupEmptyStatus("目前尚未點歌\n請到找歌趣\n找一找\n愛唱的歌曲吧！");
        if(items.size() == 0)
        {
            view.showEmptyMessage();
        }
        else
        {
            view.hideEmptyMessage();
        }
        view.reloadListView(items);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

}
