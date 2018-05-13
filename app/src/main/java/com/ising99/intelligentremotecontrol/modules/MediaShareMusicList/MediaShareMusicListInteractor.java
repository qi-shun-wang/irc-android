package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.InteractorOutput;

import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListInteractor implements MediaShareMusicListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> collection;
    private DLNAMediaManager manager;
    private Timer worker;
    private int cursor = 0;
    private Device currentCastingDevice;

    MediaShareMusicListInteractor(Context context, List<Music> collection , DLNAMediaManager manager) {
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
    public List<Music> getMusicAssets() {
        return collection;
    }

    @Override
    public void setupCurrentDevice(Device device) {
        currentCastingDevice = device;
    }

    @Override
    public void performCast(List<Music> assets) {
        worker = new Timer();
        cursor = 0;
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                if (cursor >= assets.size()) return;
                try {
                    String path = URLEncoder.encode( assets.get(cursor).getFilePath(), "UTF-8");
                    manager.setAVTransportURI(currentCastingDevice,"/music" + path);
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                cursor ++;
            }
        },1000,50000);
    }

    @Override
    public void stopCast() {
        if (currentCastingDevice == null || worker == null) return;
        worker.cancel();
        manager.stop(currentCastingDevice);
    }

}

