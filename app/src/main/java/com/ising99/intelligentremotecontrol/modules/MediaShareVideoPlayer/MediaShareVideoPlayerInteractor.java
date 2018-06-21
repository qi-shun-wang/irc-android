package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerInteractor implements MediaShareVideoPlayerContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private Video asset;
    private DLNAMediaManager manager;

    MediaShareVideoPlayerInteractor(Context context, Video asset, DLNAMediaManagerProtocol manager) {
        this.context = context;
        this.asset = asset;
        this.manager = (DLNAMediaManager) manager;
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
    public Video getVideoAsset() {
        return asset;
    }

    @Override
    public boolean isDeviceConnected() {
        return manager.getCurrentDevice() != null;
    }

    @Override
    public void clearConnectedDevice() {
        manager.stop((invocation, operation, defaultMsg) -> manager.setCurrentDevice(null));
    }

    @Override
    public void setupCurrentDevice(Device device) {
        manager.setCurrentDevice(device);
    }

    @Override
    public void setupCurrentRemoteAsset() {
        try {
            String path = URLEncoder.encode(asset.getFilePath(), "UTF-8");
            manager.setAVTransportURI("/video" + path, new DLNAMediaManagerCallback.Common() {
                @Override
                public void success(ActionInvocation invocation) {
                    output.didSetRemoteAssetSuccess();
                }

                @Override
                public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
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
                output.didPlayRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didPlayRemoteAssetFailure();
            }
        });
    }

    @Override
    public void performRemoteStop() {
        manager.stop((invocation, operation, defaultMsg) -> output.didStopRemoteAssetFailure());
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

    @Override
    public void performRemoteSeek(long timeInterval) {
        manager.seek(timeInterval, new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {
                output.didSeekRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didStopRemoteAssetFailure();
            }
        });
    }

    @Override
    public void fetchRemoteTimeInterval() {
        manager.getPositionInfo(new DLNAMediaManagerCallback.Value() {
            @Override
            public void received(ActionInvocation invocation, long timeInterval) {
                output.didFetchRemoteTimeIntervalSuccess((int)timeInterval*1000);
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didFetchRemoteTimeIntervalFailure();
            }
        });
    }

}

