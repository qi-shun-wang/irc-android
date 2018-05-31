package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerInteractor implements MediaShareMusicPlayerContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> assets;
    private int currentIndex;
    private DLNAMediaManager manager;

    MediaShareMusicPlayerInteractor(Context context, List<Music> assets, int position, DLNAMediaManager manager) {
        this.context = context;
        this.assets = assets;
        this.currentIndex = position;
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
    public void updateCurrentIndex(int index) {
        currentIndex = index;
    }

    @Override
    public Music getCurrentAsset() {
        return assets.get(currentIndex);
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public List<Music> getAssets() {
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
    public void performRemoteStop() {
        manager.stop((invocation, operation, defaultMsg) -> output.didStopRemoteAssetFailure());
    }

    @Override
    public void performRemotePlay() {
        manager.play(new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                output.didPlayRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didPlayRemoteAssetFailure();
            }
        });
    }

    @Override
    public void performRemotePause() {
        manager.pause(new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                output.didPauseRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didPauseRemoteAssetFailure();
            }
        });
    }
}

