package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListInteractor implements MediaShareMusicListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> collection;
    private DLNAMediaManagerProtocol manager;

    MediaShareMusicListInteractor(Context context, List<Music> collection, DLNAMediaManagerProtocol manager) {
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
        output = null;
        context = null;
    }

    @Override
    public List<Music> getMusicAssets() {
        return collection;
    }

    @Override
    public void setupCurrentDevice(Device device) {
        manager.setCurrentDevice(device);
    }

    @Override
    public boolean isDeviceConnected() {
        return manager.getCurrentDevice() != null;
    }

    @Override
    public void clearCurrentDevice() {
        manager.stop((invocation, operation, defaultMsg) -> {});
        manager.setCurrentDevice(null);
    }

}

