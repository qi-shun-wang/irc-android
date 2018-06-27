package com.ising99.intelligentremotecontrol.modules.Karaoke;

import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.List;

/**
 * Created by shun on 2018/4/17 下午 10:03:05.
 * .
 */

public interface KaraokeContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadListView(List<Karaoke> items);
        void setupEmptyStatus(String message);
        void showEmptyMessage();
        void hideEmptyMessage();
    }

    interface Presenter extends BaseContracts.Presenter {
        void navigateToFinder();
        void navigateToFavorite();
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentKaraokeFavoriteCollection();
        void presentKaraokeArtistFinder();
    }
}
