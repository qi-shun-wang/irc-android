package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerRouter;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListRouter implements Wireframe, MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;
    private DLNAMediaManagerProtocol manager;
    private MediaShareDMRListFragment dmrList;
    private MediaShareMusicPlayerFragment mediaShareMusicPlayerFragment;

    private MediaShareMusicListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicListFragment setupModule(Context context, String title, List<Music> collection, DLNAMediaManagerProtocol manager, BaseNavigator navigator) {

        MediaShareMusicListFragment view = new MediaShareMusicListFragment();
        MediaShareMusicListInteractor interactor = new MediaShareMusicListInteractor(context, collection, manager);
        MediaShareMusicListPresenter presenter = new MediaShareMusicListPresenter(title);
        MediaShareMusicListRouter router = new MediaShareMusicListRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.manager = manager;
        router.navigator = navigator;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void presentMediaPlayerWith(List<Music> assets, int position) {

        mediaShareMusicPlayerFragment = MediaShareMusicPlayerRouter.setupModule(context, assets, position, manager);
        MediaShareMusicListFragment ref = (MediaShareMusicListFragment) view;

        FragmentTransaction fragmentTransaction = ref.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.media_share_media_player_container, mediaShareMusicPlayerFragment).commit();

        TranslateAnimation move = (TranslateAnimation) AnimationUtils.loadAnimation(ref.getActivity(), R.anim.translate_bottom_to_top);
        ref.getActivity().findViewById(R.id.media_share_media_player_container).startAnimation(move);
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }

    @Override
    public void presentDMRList() {
        dmrList =  MediaShareDMRListRouter.setupModule(context,this,true);
        MediaShareMusicListFragment ref = (MediaShareMusicListFragment)view;
//        Blurry.with(ref.getActivity().getApplicationContext()).radius(10).sampling(2).onto(ref.getActivity().findViewById(R.id.media_share_media_player_container));
        FragmentTransaction fragmentTransaction = ((MediaShareMusicListFragment)view).getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void dismissCurrentPlayer() {
            if (mediaShareMusicPlayerFragment!=null){
                FragmentTransaction fragmentTransaction = ((Fragment)view).getFragmentManager().beginTransaction();
                fragmentTransaction.remove(mediaShareMusicPlayerFragment).commit();
            }
    }

    @Override
    public void didClosed() {
//        Blurry.delete(((MediaShareMusicListFragment)view).getActivity().findViewById(R.id.media_share_media_player_container));
        ((MediaShareMusicListFragment) view).getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

