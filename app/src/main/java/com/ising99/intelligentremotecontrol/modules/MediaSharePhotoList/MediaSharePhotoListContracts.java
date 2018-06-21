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
        void showWarningBadge(String text);
        void hideWarningBadge(String text);
        void setupNavigationTitle(String title);
        void updateCastButtonWith(int resId);
    }

    interface Presenter extends BaseContracts.Presenter {
        void performBack();
        void didTapOnCast();
        void didSelected(Device device);
        void didSelectedPhotoAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        List<Photo> getPhotos();
        boolean isDeviceConnected();
        void setupCurrentDevice(Device device);
        void setupSelectedPhotos(List<Photo> assets);
        void setupCurrentRemoteAsset(int index);
        void performRemotePlay();
        void performRemoteStop();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSetRemoteAssetSuccess();
        void didSetRemoteAssetFailure();

        void didPlayRemoteAssetSuccess();
        void didPlayRemoteAssetFailure();

        void didStopRemoteAssetFailure();

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void navigateBack();
        void presentDMRList();
    }
}
