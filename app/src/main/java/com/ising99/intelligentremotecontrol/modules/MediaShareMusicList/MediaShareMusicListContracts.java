package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;


import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public interface MediaShareMusicListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadGridView(List<Music> assets);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedMusicAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        List<Music> getMusicAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentMediaPlayerWith(List<Music> assets, int position);
    }
}
