package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Wireframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 04:25:19.
 * .
 */

public class MediaShareMusicGroupListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<String> groups = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private HashMap<String, List<Music>> assets = new HashMap<>();

    MediaShareMusicGroupListPresenter() {
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
        assets.clear();
        keys.clear();
        groups.clear();
        interactor.prepareMusicAssets();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        interactor.performRemoteStop();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void performBack() {
        router.navigateBack();
    }

    @Override
    public void didSelectedAt(int position) {
        String title = keys.get(position);
        List<Music> collection = assets.get(title);
        router.presentMusicAssetsWith(title,collection);
    }

    @Override
    public void didPreparedMusicAssets(HashMap<String, List<Music>> assets) {
        this.assets = assets;
        for (String key : assets.keySet())
        {
            keys.add(key);
            groups.add(key + "(" + assets.get(key).size() + ")");
        }
        view.updateListView(groups);
    }

    @Override
    public void didStopRemoteAssetFailure() {

    }
}
