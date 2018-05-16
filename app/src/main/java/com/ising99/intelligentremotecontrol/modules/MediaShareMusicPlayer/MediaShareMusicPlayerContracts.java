package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

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
    }

    interface Presenter extends BaseContracts.Presenter {
        void prepareMediaPlayerPanel();

    }

    interface Interactor extends BaseContracts.Interactor {
        Music getCurrentAsset();
        int getCurrentIndex();
        List<Music> getAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentMediaPlayerPanelWith(List<Music> assets, int position);
    }
}
