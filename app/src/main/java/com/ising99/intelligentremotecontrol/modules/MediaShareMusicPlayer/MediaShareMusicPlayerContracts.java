package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import java.util.List;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public interface MediaShareMusicPlayerContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateMusicInfo(String title , String subtitle , int resID);
        void updatePlaybackIconWith(int resID);
    }

    interface Presenter extends BaseContracts.Presenter {
        void prepareMediaPlayerPanel();
        void performPlayback();
        boolean performFastForward();
        void performNext();
        void updatePlaybackIcon(boolean isPlaying, int currentIndex);
    }

    interface Interactor extends BaseContracts.Interactor {
        void updateCurrentIndex(int index);
        Music getCurrentAsset();
        int getCurrentIndex();
        List<Music> getAssets();
        Music playNext();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentMediaPlayerPanelWith(List<Music> assets, int position,MediaPlayer player);
    }
}
