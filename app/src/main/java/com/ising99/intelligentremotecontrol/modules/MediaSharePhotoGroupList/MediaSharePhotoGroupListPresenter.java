package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Wireframe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/3 上午 10:22:42.
 * .
 */

public class MediaSharePhotoGroupListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<String> groups = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private HashMap<String, List<Photo>> assets = new HashMap<>();

    MediaSharePhotoGroupListPresenter() {
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
        this.assets.clear();
        groups.clear();
        keys.clear();
        interactor.preparePhotoAssets();
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
    public void didPreparedPhotoAssets(HashMap<String, List<Photo>> assets) {
        this.assets = assets;
        for (String key : assets.keySet())
        {
            keys.add(key);
            groups.add(key + "(" + assets.get(key).size() + ")");
        }
        view.updateListView(groups);
    }

    @Override
    public void didSelectedAt(int position) {
        String title = keys.get(position);
        List<Photo> collection = assets.get(title);
        router.presentPhotoCollectionWith(title,collection);
    }

    @Override
    public void performBack() {
        router.navigateBack();
    }
}
