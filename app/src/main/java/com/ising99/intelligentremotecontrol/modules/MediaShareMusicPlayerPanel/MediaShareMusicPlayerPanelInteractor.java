package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
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
        manager.setCurrentDevice(device);
    }

    @Override
    public void setupCurrentRemoteAsset() {
        try {
            String path = URLEncoder.encode( assets.get(currentIndex).getFilePath(), "UTF-8");
            manager.setAVTransportURI("/music" + path, new DLNAMediaManagerCallback.Common() {
                @Override
                public void success(ActionInvocation invocation) {
                    Log.i("DLNAMediaManager", "SetAVTransportURI success.");
                    output.didSetRemoteAssetSuccess();
                }

                @Override
                public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                    Log.e("DLNAMediaManager", "SetAVTransportURI failed");
                    output.didSetRemoteAssetFailure();
                }
            });
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    @Override
    public void performRemotePlay() {
        manager.play(new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                Log.i("DLNAMediaManager", "Play success.");
                output.didPlayRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("DLNAMediaManager", "Play failed");
                output.didPlayRemoteAssetFailure();
            }
        });

    }

    @Override
    public void performRemotePause() {
        manager.pause(new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                Log.i("DLNAMediaManager", "Pause success.");
                output.didPauseRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("DLNAMediaManager", "Pause failed");
                output.didPauseRemoteAssetFailure();
            }
        });
    }

    @Override
    public void performRemoteStop() {
        manager.stop((arg0, arg1, arg2) -> {
            Log.e("DLNAMediaManager", "Stop failed");
            output.didStopRemoteAssetFailure();
        });
    }

    @Override
    public void performRemoteSeek(long timeInterval) {
        manager.seek(timeInterval, new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                Log.i("DLNAMediaManager", "seek success.");
                output.didSeekRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                Log.e("DLNAMediaManager", "seek failed");
                output.didSeekRemoteAssetFailure();
            }
        });
    }

    @Override
    public Music updateCurrentIndex(int index) {
        currentIndex = index;
        return assets.get(currentIndex);
    }
}

