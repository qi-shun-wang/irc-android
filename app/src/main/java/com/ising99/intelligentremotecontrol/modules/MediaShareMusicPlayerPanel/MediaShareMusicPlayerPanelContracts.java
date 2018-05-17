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
        void setupCurrentMusicAsset(Music asset);
        void clearPanelListener();
    }

    interface Presenter extends BaseContracts.Presenter {
        void updateScrollState(int state);
        void updateScroll(int dx ,int dy);
    }

    interface Interactor extends BaseContracts.Interactor {
        Music getCurrentMusicAsset();
        List<Music> getMusicAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void dismissPanel();
    }
}
