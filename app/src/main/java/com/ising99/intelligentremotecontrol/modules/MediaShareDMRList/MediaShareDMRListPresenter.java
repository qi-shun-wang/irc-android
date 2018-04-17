package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.util.Log;

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
 * Created by shun on 2018/4/10 上午 10:33:06.
 */

public class MediaShareDMRListPresenter implements Presenter, InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    MediaShareDMRListPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new MediaShareDMRListInteractor(context, this);
        router = new MediaShareDMRListRouter(context, this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                interactor.startSearchDMR();
            }
        },3000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                view.reloadDMRList(interactor.getCurrentDevices());
                interactor.stopSearchDMR();
            }
        },9000);

    }

    @Override
    public void onPause() {
        interactor.stopSearchDMR();
    }

    @Override
    public void onDestroy() {
        interactor.decompose();
        context = null;
        view = null;
        interactor = null;
        router = null;
    }

    @Override
    public void setupView(BaseContracts.View view) {

    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {

    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {

    }

    @Override
    public void decompose() {

    }


    @Override
    public void prepareCastDeviceAt(int index) {

        RemoteDevice device = interactor.getCurrentDevices().get(index);

        Log.v("prepareCastDeviceAt","==prepareCastDeviceAt==>"+device.getDetails().getFriendlyName());
    }
}
