package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.InteractorOutput;

import org.fourthline.cling.model.meta.Device;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListInteractor implements MediaShareVideoListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Video> collection;
    private DLNAMediaManager manager;
    private Timer worker;
    private int cursor = 0;
    private Device currentCastingDevice;

    MediaShareVideoListInteractor(Context context, List<Video> collection , DLNAMediaManager manager) {
        this.context = context;
        this.collection = collection;
        this.manager = manager;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        worker.cancel();
        output = null;
        context = null;
    }

    @Override
    public List<Video> getVideoAssets() {
        return collection;
    }

    @Override
    public void stopCast() {
        if (currentCastingDevice == null || worker == null) return;
        worker.cancel();
        manager.stop(currentCastingDevice);
    }

    @Override
    public void performCast(List<Video> assets) {
        worker = new Timer();
        cursor = 0;
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                if (cursor >= assets.size()) return;
                manager.play(currentCastingDevice,"/video" + assets.get(cursor).getFilePath(),"");
                cursor ++;
            }
        },1000,50000);
    }

    @Override
    public void setupCurrentDevice(Device device) {
        currentCastingDevice = device;
    }
}

