package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.View;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/6/7 下午 03:15:28.
 * .
 */

public class KaraokeFinderPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<Karaoke> karaokes;

    KaraokeFinderPresenter() {
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
        karaokes = new ArrayList<>();
        karaokes.add(new Karaoke("原來你什麼都想要","徐瑩佳","ＭＶ"));
        karaokes.add(new Karaoke("身騎黑馬","徐瑩佳","導"));
        karaokes.add(new Karaoke("白天很懂夜的美","徐瑩佳","導"));
        karaokes.add(new Karaoke("執長太黑暗","端木輕薄","ＭＶ"));
        view.setupEmptyStatus("搜尋條件找不到你要的歌曲！");
        if(karaokes.size() == 0)
        {
            view.showEmptyMessage();
        }
        else
        {
            view.hideEmptyMessage();
        }
        view.reloadKaraokeList(karaokes);
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

    @Override
    public void didSelectedAt(int position, int height) {
        view.showControlPanelAt(position*height, height);
    }

    @Override
    public void performBack() {
        router.navigateBack();
    }
}
