package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.view.SurfaceHolder;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public interface MediaShareVideoPlayerContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updatePlaybackIconWith(int resID);
        void setupSeekBarMaxScale(int scale);
        void updateCurrentTimeLabel(String text);
        void updateEndTimeLabel(String text);
        void updateSeekBarLocation(int scale);
    }

    interface Presenter extends BaseContracts.Presenter {
        void prepareMediaPlayer(SurfaceHolder surfaceHolder);
        void performPlayBack();
        void startTrackingTouch();
        void progressChangedAt(int secScale);
        void stopTrackingTouch();
        void didSelected(Device device);
        void didTapOnCast();
    }

    interface Interactor extends BaseContracts.Interactor {
        Video getVideoAsset();

        void setupCurrentDevice(Device device);
        void setupCurrentRemoteAsset();
        void performRemotePlay();
        void performRemoteStop();
        void performRemotePause();
        void performRemoteSeek(long timeInterval);
        void fetchRemoteTimeInterval();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSetRemoteAssetSuccess();
        void didSetRemoteAssetFailure();

        void didPlayRemoteAssetSuccess();
        void didPlayRemoteAssetFailure();

        void didStopRemoteAssetFailure();

        void didPauseRemoteAssetSuccess();
        void didPauseRemoteAssetFailure();

        void didSeekRemoteAssetSuccess();
        void didSeekRemoteAssetFailure();

        void didFetchRemoteTimeIntervalSuccess(int timeInterval);
        void didFetchRemoteTimeIntervalFailure();
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentDMRList();
    }
}
