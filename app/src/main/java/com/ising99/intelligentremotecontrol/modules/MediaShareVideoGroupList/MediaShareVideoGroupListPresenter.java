package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Wireframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:05:36.
 * .
 */

public class MediaShareVideoGroupListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<String> groups = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private HashMap<String, List<Video>> assets = new HashMap<>();

    MediaShareVideoGroupListPresenter() {
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
        interactor.prepareVideoAssets();
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
    public void didSelectedAt(int position) {
        String title = keys.get(position);
        List<Video> collection = assets.get(title);
        router.presentVideoAssetsWith(title,collection);
    }

    @Override
    public void didPreparedVideoAssets(HashMap<String, List<Video>> assets) {
        this.assets = assets;
        for (String key : assets.keySet())
        {
            keys.add(key);
            groups.add(key + "(" + assets.get(key).size() + ")");
        }
        view.updateListView(groups);
    }
}
