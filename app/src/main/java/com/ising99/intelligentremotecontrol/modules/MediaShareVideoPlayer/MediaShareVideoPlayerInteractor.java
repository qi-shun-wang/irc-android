package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.InteractorOutput;

import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerInteractor implements MediaShareVideoPlayerContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private Video asset;
    private DLNAMediaManager manager;
    private Timer worker;
    private Device currentCastingDevice;

    MediaShareVideoPlayerInteractor(Context context, Video asset, DLNAMediaManager manager) {
        this.context = context;
        this.asset = asset;
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
    public Video getVideoAsset() {
        return asset;
    }

    @Override
    public void stopCast() {
        if (currentCastingDevice == null || worker == null) return;
        worker.cancel();
        manager.stop(currentCastingDevice);
    }

    @Override
    public void performCast() {
        worker = new Timer();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    String path = URLEncoder.encode(asset.getFilePath(), "UTF-8");
                    manager.play(currentCastingDevice,"/video" + path,"");
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }
        },1000,asset.getDuration());
    }


    @Override
    public void setupCurrentDevice(Device device) {
        currentCastingDevice = device;
    }
}

