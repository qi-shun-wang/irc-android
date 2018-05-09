package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 04:25:19.
 * .
 */

public interface MediaShareMusicGroupListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateListView(List<String> titles);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        void prepareMusicAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didPreparedMusicAssets(HashMap<String, List<Music>> assets);
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentMusicAssetsWith(String title, List<Music> assets);
    }
}
