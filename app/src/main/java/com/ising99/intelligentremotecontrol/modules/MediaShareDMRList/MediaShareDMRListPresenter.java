package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Wireframe;

import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.types.UDN;

import java.util.ArrayList;
import java.util.List;
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
    private boolean prependedLocalDevice;
    private Device localDevice;
    private boolean isSearching = false;

    MediaShareDMRListPresenter(MediaShareDMRListFragmentDelegate delegate, boolean prependedLocalDevice) {
        this.delegate = delegate;
        this.prependedLocalDevice = prependedLocalDevice;
        if (prependedLocalDevice) {
            try {
                localDevice = new LocalDevice(new DeviceIdentity(UDN.valueOf("Android Phone")));
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private void performTasks(){
        if (isSearching) return;
        isSearching = true;
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
                isSearching = false;
                if (prependedLocalDevice)
                {
                    List<Device> devices = new ArrayList<>();
                    devices.add(localDevice);
                    devices.addAll(interactor.getCurrentDevices());
                    view.reloadDMRList(devices);
                }
                else
                {
                    view.reloadDMRList(interactor.getCurrentDevices());
                }

                view.updateSearchedStatus("已找到的設備");
                view.stopSearchIconRotation();
                interactor.stopSearchDMR();
            }
        },6000);
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
        Device device;
        if (prependedLocalDevice)
        {
            if (index == 0)
            {
                device = localDevice;
            }
            else
            {
                device = interactor.getCurrentDevices().get(index - 1);
            }
        }
        else
        {
            device = interactor.getCurrentDevices().get(index);
        }

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
