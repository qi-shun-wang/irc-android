package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

import java.util.List;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public interface MediaSharePhotoListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadGridView(List<Photo> collection);
    }

    interface Presenter extends BaseContracts.Presenter {

    }

    interface Interactor extends BaseContracts.Interactor {
        List<Photo> getPhotos();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}