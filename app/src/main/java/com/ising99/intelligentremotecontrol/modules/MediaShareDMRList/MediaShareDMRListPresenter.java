package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Wireframe;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/4 上午 10:51:23.
 * .
 */

public class MediaShareDMRListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private MediaShareDMRListFragmentDelegate delegate;
    private Timer startSearchTask;
    private Timer stopSearchTask ;

    MediaShareDMRListPresenter(MediaShareDMRListFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    private void performTasks(){
        startSearchTask = new Timer();
        stopSearchTask = new Timer();
        view.startSearchIconRotation();
        startSearchTask.schedule(new TimerTask() {
            @Override
            public void run() {
                view.updateSearchedStatus("正在搜尋設備中...");
                interactor.startSearchDMR();
            }
        },3000);

        stopSearchTask.schedule(new TimerTask() {
            @Override
            public void run() {
                view.reloadDMRList(interactor.getCurrentDevices());
                view.updateSearchedStatus("已找到的設備");
                view.stopSearchIconRotation();
                interactor.stopSearchDMR();
            }
        },9000);
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
        performTasks();
    }

    @Override
    public void onPause() {
        startSearchTask.cancel();
        stopSearchTask.cancel();
        interactor.stopSearchDMR();
    }

    @Override
    public void onDestroy() {
        router.dismissDMRList();
    }


    @Override
    public void prepareCastDeviceAt(int index) {
        RemoteDevice device = interactor.getCurrentDevices().get(index);
        delegate.didSelected(device);
        delegate.didClosed();
    }

    @Override
    public void performDismiss() {
        delegate.didClosed();
    }

    @Override
    public void performRefreshTask() {
        performTasks();
    }
}
