package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import com.ising99.intelligentremotecontrol.model.Area;
import com.ising99.intelligentremotecontrol.model.Artist;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import java.util.List;

/**
 * Created by shun on 2018/6/7 上午 09:33:08.
 * .
 */

public interface KaraokeArtistFinderContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateTypeSelectedBackgroundAt(int tag);
        void reloadAreaList(List<Area> areas);
        void reloadArtistList(List<Artist> artists);
    }

    interface Presenter extends BaseContracts.Presenter {
        void performTypeSelectAt(int tag);
        void performAreaSelectAt(int position);
        void performArtistSelectAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
