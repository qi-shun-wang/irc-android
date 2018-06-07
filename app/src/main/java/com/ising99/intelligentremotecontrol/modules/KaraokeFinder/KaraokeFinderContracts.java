package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.List;

/**
 * Created by shun on 2018/6/7 下午 03:15:28.
 * .
 */

public interface KaraokeFinderContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadKaraokeList(List<Karaoke> karaokes);
        void showControlPanelAt(int top, int height);
        void setupEmptyStatus(String message);
        void showEmptyMessage();
        void hideEmptyMessage();
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedAt(int position, int height);
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
