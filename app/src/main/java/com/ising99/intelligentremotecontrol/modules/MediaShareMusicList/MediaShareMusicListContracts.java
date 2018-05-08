package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;


import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import org.fourthline.cling.model.meta.Device;

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
        void didTapOnCast();
        void didSelected(Device device);
        void didSelectedMusicAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        List<Music> getMusicAssets();
        void stopCast();
        void performCast(List<Music> photos);
        void setupCurrentDevice(Device device);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentDMRList();
    }
}
