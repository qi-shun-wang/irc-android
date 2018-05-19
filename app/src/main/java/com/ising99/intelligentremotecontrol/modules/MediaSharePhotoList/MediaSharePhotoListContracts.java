package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public interface MediaSharePhotoListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadGridView(List<PhotoItem> collection);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnCast();
        void didSelected(Device device);
        void didSelectedPhotoAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        List<Photo> getPhotos();

        void setupCurrentDevice(Device device);
        void setupCurrentRemoteAsset();
        void performRemotePlay();
        void performRemoteStop();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

        void presentDMRList();
    }
}
