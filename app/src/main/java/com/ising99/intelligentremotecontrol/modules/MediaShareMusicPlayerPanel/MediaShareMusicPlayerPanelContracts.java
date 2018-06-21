package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public interface MediaShareMusicPlayerPanelContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void setupMusicAssets(List<Music> assets);
        void setupMaxVolume(int value);
        void updateCurrentVolume(int value);
        void updateCurrentPlaybackIcon(int resID);
        void updateCurrentMedia(int currentTimeInterval);
        void clearPanelListener();
        void updateMediaPanel(Music asset);
        void scrollToTop();
        void showWarningBadge(String text);
        void hideWarningBadge(String text);
        void updateCastButtonWith(int resId);
    }

    interface Presenter extends BaseContracts.Presenter {
        void updateScrollState(int state);
        void updateScroll(int dx ,int dy);
        void didTapOnItemAt(int position);
        void performPlayback();
        void performFastForward();
        void performFastBackward();
        void prepareUpdateHolder();
        void performCast();
        void startMediaSeeking();
        void stopMediaSeeking();
        void didMediaSeekAt(int timeInterval);

        void startVolumeSeeking();
        void stopVolumeSeeking();
        void didVolumeSeekAt(int scale);

        void didSelected(Device device);
    }

    interface Interactor extends BaseContracts.Interactor {
        Music getCurrentMusicAsset();
        int getCurrentIndex();
        Music updateCurrentIndex(int index);
        List<Music> getMusicAssets();
        Music playNext();
        Music playLast();

        void setupCurrentDevice(Device device);
        void setupCurrentRemoteAsset();
        void performRemotePlay();
        void performRemoteStop();
        void performRemotePause();
        void performRemoteSeek(long timeInterval);
        void fetchRemotePosition();
        boolean isDeviceConnected();
        void clearConnectedDevice();
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

        void didFetchRemotePositionSuccess(int timeInterval);
        void didFetchRemotePositionFailure();
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void dismissPanelWhen(boolean isPlaying, boolean isRemoteMode, int currentIndex, int volumeScale);
        void presentDMRList();
    }
}
