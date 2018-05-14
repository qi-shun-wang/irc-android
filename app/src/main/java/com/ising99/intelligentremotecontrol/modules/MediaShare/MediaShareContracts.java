package com.ising99.intelligentremotecontrol.modules.MediaShare;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.List;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public interface MediaShareContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void showAlert();
    }

    interface Presenter extends BaseContracts.Presenter {
        List<MediaShareItem> prepareSectionItems();
        void didSelectAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        boolean isGrantedPermission();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentPhotoList();
        void presentMusicList();
        void presentVideoList();
    }
}
