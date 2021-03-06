package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<PhotoItem> photos = new ArrayList<>();
    private List<Photo> selectedPhotos;
    private boolean isFirstPerformedCasting = true;
    private int selectedIndex = 0;
    private Timer worker;
    private String title;

    MediaSharePhotoListPresenter(String title) {
        this.title = title;
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
        for (Photo photo: interactor.getPhotos()){
            photos.add(new PhotoItem(photo,false));
        }
        view.setupNavigationTitle(title);
        if (interactor.isDeviceConnected()) view.updateCastButtonWith(R.drawable.media_share_cast_red_icon);
        else view.updateCastButtonWith(R.drawable.media_share_cast_gray_icon);
    }

    @Override
    public void onResume() {
        view.reloadGridView(photos);
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
        interactor.setupSelectedPhotos(new ArrayList<>());
        interactor.setupCurrentRemoteAsset(0);
        router.navigateBack();
    }

    @Override
    public void didTapOnCast() {
        if (interactor.isDeviceConnected()) {
            if (worker!= null) worker.cancel();
            prepareCasting();
        }
        else router.presentDMRList();
    }

    @Override
    public void didSelected(Device device) {
        isFirstPerformedCasting = false;
        interactor.setupCurrentDevice(device);
        if (interactor.isDeviceConnected()) view.updateCastButtonWith(R.drawable.media_share_cast_red_icon);
        else view.updateCastButtonWith(R.drawable.media_share_cast_gray_icon);
        prepareCasting();
    }

    @Override
    public void didSelectedPhotoAt(int position) {

        PhotoItem item = photos.get(position);
        item.setSelected(!item.isSelected());
        view.reloadGridView(photos);
    }

    private void prepareCasting(){

        selectedPhotos = new ArrayList<>();
        for (PhotoItem item : photos) {
            if (item.isSelected()){
                selectedPhotos.add(item.getPhoto());
            }
        }
        if (selectedPhotos.size() == 0) {
            view.showWarningBadge("最少選一張！");
            return;
        }
        view.showWarningBadge("媒體準備播放...");
        selectedIndex = 0;
        interactor.setupSelectedPhotos(selectedPhotos);
        interactor.setupCurrentRemoteAsset(selectedIndex);

    }

    @Override
    public void didSetRemoteAssetSuccess() {
        selectedIndex++;
        interactor.performRemotePlay();
    }

    @Override
    public void didSetRemoteAssetFailure() {
        //todo show set asset failure badge
    }

    @Override
    public void didPlayRemoteAssetSuccess() {
        //todo show play asset success badge
        view.hideWarningBadge("媒體播放中...");
        if (selectedPhotos.size() -1 < selectedIndex) {
            return;
        }
        prepareNextPlay();
    }

    private void prepareNextPlay() {
        worker = new Timer();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                interactor.setupCurrentRemoteAsset(selectedIndex);
            }
        },10000);
    }

    @Override
    public void didPlayRemoteAssetFailure() {
        //todo show play asset failure badge
        prepareStop();

    }

    private void prepareStop() {
        if (worker!= null) worker.cancel();
        interactor.performRemoteStop();
    }

    @Override
    public void didStopRemoteAssetFailure() {
        //todo show Stop asset failure badge
    }
}
