package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.View;

import java.util.List;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareMusicPlayerRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicPlayerFragment setupModule(Context context, List<Music> assets, int position) {

        MediaShareMusicPlayerFragment view = new MediaShareMusicPlayerFragment();
        MediaShareMusicPlayerInteractor interactor = new MediaShareMusicPlayerInteractor(context, assets, position);
        MediaShareMusicPlayerPresenter presenter = new MediaShareMusicPlayerPresenter();
        MediaShareMusicPlayerRouter router = new MediaShareMusicPlayerRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void presentMediaPlayerPanelWith(List<Music> assets, int position) {
        //TODO- setup MediaPlayerPanel module
    }
}

