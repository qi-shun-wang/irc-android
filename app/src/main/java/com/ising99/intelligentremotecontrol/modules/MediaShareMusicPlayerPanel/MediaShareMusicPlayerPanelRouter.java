package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareMusicPlayerPanelRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicPlayerPanelFragment setupModule(Context context) {

        MediaShareMusicPlayerPanelFragment view = new MediaShareMusicPlayerPanelFragment();
        MediaShareMusicPlayerPanelInteractor interactor = new MediaShareMusicPlayerPanelInteractor(context);
        MediaShareMusicPlayerPanelPresenter presenter = new MediaShareMusicPlayerPanelPresenter();
        MediaShareMusicPlayerPanelRouter router = new MediaShareMusicPlayerPanelRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }
}

