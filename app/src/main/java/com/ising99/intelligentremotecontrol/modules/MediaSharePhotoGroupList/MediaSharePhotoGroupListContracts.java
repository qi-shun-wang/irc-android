package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/3 上午 10:22:42.
 * .
 */

public interface MediaSharePhotoGroupListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateListView(List<String> titles);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedAt(int position);

    }

    interface Interactor extends BaseContracts.Interactor {
        void preparePhotoAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didPreparedPhotoAssets(HashMap<String, List<Photo>> assets);
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentPhotoCollectionWith(String title, List<Photo> photos);
    }
}
