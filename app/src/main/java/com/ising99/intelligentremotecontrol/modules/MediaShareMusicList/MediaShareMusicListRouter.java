package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.app.FragmentTransaction;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerRouter;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListRouter implements Wireframe   {

    private Context context;
    private Presenter presenter;
    private View view;
    private DLNAMediaManager manager;

    private MediaShareMusicListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicListFragment setupModule(Context context, List<Music> collection, DLNAMediaManager manager) {

        MediaShareMusicListFragment view = new MediaShareMusicListFragment();
        MediaShareMusicListInteractor interactor = new MediaShareMusicListInteractor(context, collection);
        MediaShareMusicListPresenter presenter = new MediaShareMusicListPresenter();
        MediaShareMusicListRouter router = new MediaShareMusicListRouter(context);

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
    public void presentMediaPlayerWith(List<Music> assets, int position) {

        MediaShareMusicPlayerFragment mediaShareMusicPlayerFragment = MediaShareMusicPlayerRouter.setupModule(context, assets, position, manager);
        MediaShareMusicListFragment ref = (MediaShareMusicListFragment) view;

        FragmentTransaction fragmentTransaction = ref.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.media_share_media_player_container, mediaShareMusicPlayerFragment).commit();

        TranslateAnimation move = (TranslateAnimation) AnimationUtils.loadAnimation(ref.getActivity(), R.anim.translate_bottom_to_top);
        ref.getActivity().findViewById(R.id.media_share_media_player_container).startAnimation(move);
    }

}

