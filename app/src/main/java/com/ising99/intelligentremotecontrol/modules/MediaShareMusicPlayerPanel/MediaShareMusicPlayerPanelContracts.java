package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import java.util.List;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public interface MediaShareMusicPlayerPanelContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void setupMusicAssets(List<Music> assets);
        void clearPanelListener();
        void updateMediaPanel(Music asset, int currentTimeInterval, int playbackIconResID);
        void scrollToTop();
    }

    interface Presenter extends BaseContracts.Presenter {
        void updateScrollState(int state);
        void updateScroll(int dx ,int dy);
        void didTapOnItemAt(int position);
        void performPlayback();
        void performFastForward();
        void performFastBackward();
        void startMediaSeeking();
        void stopMediaSeeking();
        void didMediaSeekAt(int timeInterval);
    }

    interface Interactor extends BaseContracts.Interactor {
        Music getCurrentMusicAsset();
        int getCurrentIndex();
        Music updateCurrentIndex(int index);
        List<Music> getMusicAssets();
        Music playNext();
        Music playLast();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void dismissPanelWhen(boolean isPlaying, int currentIndex);
    }
}
