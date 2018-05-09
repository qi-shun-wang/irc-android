package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public interface MediaShareVideoListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadGridView(List<Video> assets);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnCast();
        void didSelected(Device device);
        void didSelectedVideoAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        List<Video> getVideoAssets();
        void stopCast();
        void performCast(List<Video> photos);
        void setupCurrentDevice(Device device);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentDMRList();
    }
}
