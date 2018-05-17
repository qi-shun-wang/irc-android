package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerRouterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;

import java.util.List;

public class MediaShareMusicPlayerPanelRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private MediaShareMusicPlayerRouterDelegate delegate;

    private MediaShareMusicPlayerPanelRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicPlayerPanelFragment setupModule(Context context, List<Music> assets, int position, MediaPlayer player, MediaShareMusicPlayerRouterDelegate delegate) {

        MediaShareMusicPlayerPanelFragment view = new MediaShareMusicPlayerPanelFragment();
        MediaShareMusicPlayerPanelInteractor interactor = new MediaShareMusicPlayerPanelInteractor(context, assets, position);
        MediaShareMusicPlayerPanelPresenter presenter = new MediaShareMusicPlayerPanelPresenter(player);
        MediaShareMusicPlayerPanelRouter router = new MediaShareMusicPlayerPanelRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.delegate = delegate;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void dismissPanelWhen(boolean isPlaying, int currentIndex) {
        delegate.dismissWithPlayerStatus(isPlaying, currentIndex);
    }
}

