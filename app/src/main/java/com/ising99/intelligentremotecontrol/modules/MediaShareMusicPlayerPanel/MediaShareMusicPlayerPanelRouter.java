package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.app.Fragment;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;

import java.util.List;

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

    public static MediaShareMusicPlayerPanelFragment setupModule(Context context, List<Music> assets, int position) {

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

    @Override
    public void dismissPanel() {
        MediaShareMusicPlayerPanelFragment ref = (MediaShareMusicPlayerPanelFragment) view;
        TranslateAnimation move = (TranslateAnimation) AnimationUtils.loadAnimation(ref.getActivity(), R.anim.translate_top_to_bottom);

        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ref.getActivity().findViewById(R.id.media_share_music_player_panel_container).setVisibility(android.view.View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ref.getActivity().findViewById(R.id.media_share_music_player_panel_container).startAnimation(move);
    }
}

