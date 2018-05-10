package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public interface MediaShareVideoPlayerContracts extends BaseContracts {

    interface View extends BaseContracts.View {
    }

    interface Presenter extends BaseContracts.Presenter {
        String getFilePath();
        void didSelected(Device device);
        void didTapOnCast();
    }

    interface Interactor extends BaseContracts.Interactor {
        Video getVideoAsset();
        void stopCast();
        void performCast();
        void setupCurrentDevice(Device device);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentDMRList();
    }
}
