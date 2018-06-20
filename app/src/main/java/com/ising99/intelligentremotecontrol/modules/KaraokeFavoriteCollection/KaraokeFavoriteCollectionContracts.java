package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.model.KaraokeBookmark;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.List;

/**
 * Created by shun on 2018/6/11 下午 02:55:45.
 * .
 */

public interface KaraokeFavoriteCollectionContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadKaraokeList(List<Karaoke> karaokes);
        void reloadBookmarkList(List<KaraokeBookmark>bookmarks);
        void updateBookmarkToolBar(String name);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didSelectedAt(int position);
        void performBookmarkCreation(String name);
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
