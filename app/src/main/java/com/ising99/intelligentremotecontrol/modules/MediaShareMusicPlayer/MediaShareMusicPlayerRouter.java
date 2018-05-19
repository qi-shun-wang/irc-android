package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelRouter;

import java.util.List;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerRouter implements Wireframe , MediaShareMusicPlayerRouterDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private DLNAMediaManager manager;

    private MediaShareMusicPlayerRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicPlayerFragment setupModule(Context context, List<Music> assets, int position, DLNAMediaManager manager) {

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
        router.manager = manager;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void presentMediaPlayerPanelWith(List<Music> assets, int position, MediaPlayer player, int volumeScale) {
        MediaShareMusicPlayerFragment ref = (MediaShareMusicPlayerFragment) view;

        MediaShareMusicPlayerPanelFragment panel = MediaShareMusicPlayerPanelRouter.setupModule(context, assets, position, volumeScale, player, this, manager);

        FragmentTransaction transaction = ref.getFragmentManager().beginTransaction();
        transaction.replace(R.id.media_share_music_player_panel_container, panel).commit();

        TranslateAnimation move = (TranslateAnimation) AnimationUtils.loadAnimation(ref.getActivity(), R.anim.translate_bottom_to_top);
        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ref.getActivity().findViewById(R.id.media_share_music_player_panel_container).setVisibility(android.view.View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ref.getActivity().findViewById(R.id.media_share_music_player_panel_container).startAnimation(move);

    }

    @Override
    public void dismissWithPlayerStatus(boolean isPlaying, int currentIndex, int volumeScale) {
        presenter.updatePlaybackIcon(isPlaying, currentIndex, volumeScale);
        MediaShareMusicPlayerFragment ref = (MediaShareMusicPlayerFragment) view;
        TranslateAnimation move = (TranslateAnimation) AnimationUtils.loadAnimation(ref.getActivity(), R.anim.translate_top_to_bottom);

        move.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                ref.getActivity().findViewById(R.id.media_share_music_player_panel_container).setClickable(false);
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

