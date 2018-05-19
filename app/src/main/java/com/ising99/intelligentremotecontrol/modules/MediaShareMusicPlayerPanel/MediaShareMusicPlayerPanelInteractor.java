package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;

import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelInteractor implements MediaShareMusicPlayerPanelContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> assets;
    private int currentIndex;
    private DLNAMediaManager manager;
    private Device currentCastingDevice;

    MediaShareMusicPlayerPanelInteractor(Context context, List<Music> assets, int position, DLNAMediaManager manager) {
        this.context = context;
        this.currentIndex = position;
        this.assets = assets;
        this.manager = manager;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public Music getCurrentMusicAsset() {
        return assets.get(currentIndex);
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public List<Music> getMusicAssets() {
        return assets;
    }

    @Override
    public Music playNext() {
        if (currentIndex<assets.size()-1){
            currentIndex++;
        }else {
            currentIndex = 0;
        }

        return assets.get(currentIndex);
    }

    @Override
    public Music playLast() {
        if (currentIndex>0){
            currentIndex--;
        }else {
            currentIndex = assets.size()-1;
        }

        return assets.get(currentIndex);
    }

    @Override
    public void setupCurrentDevice(Device device) {
        currentCastingDevice = device;
    }

    @Override
    public void performCast() {
        try {
            String path = URLEncoder.encode( assets.get(currentIndex).getFilePath(), "UTF-8");
            manager.setAVTransportURI(currentCastingDevice,"/music" + path);
            //TODO-add function callback when set success
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void stopCast() {
        if (currentCastingDevice == null) return;
        manager.stop(currentCastingDevice);
    }

    @Override
    public Music updateCurrentIndex(int index) {
        currentIndex = index;
        return assets.get(currentIndex);
    }
}

