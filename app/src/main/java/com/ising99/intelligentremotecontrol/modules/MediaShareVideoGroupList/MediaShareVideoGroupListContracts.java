package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:05:36.
 * .
 */

public interface MediaShareVideoGroupListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateListView(List<String> titles);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {
        void prepareVideoAssets();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didPreparedVideoAssets(HashMap<String, List<Video>> assets);
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentVideoAssetsWith(String title, List<Video> assets);
    }
}
