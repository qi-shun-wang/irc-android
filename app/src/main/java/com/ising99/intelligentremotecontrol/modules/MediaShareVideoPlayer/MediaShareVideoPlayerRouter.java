package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.app.FragmentTransaction;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.View;

import org.fourthline.cling.model.meta.Device;

import jp.wasabeef.blurry.Blurry;


/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerRouter implements Wireframe, MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;
    private MediaShareDMRListFragment dmrList;

    private MediaShareVideoPlayerRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoPlayerFragment setupModule(Context context, String backTitle, Video asset, DLNAMediaManagerProtocol manager, BaseNavigator navigator) {

        MediaShareVideoPlayerFragment view = new MediaShareVideoPlayerFragment();
        MediaShareVideoPlayerInteractor interactor = new MediaShareVideoPlayerInteractor(context, asset, manager);
        MediaShareVideoPlayerPresenter presenter = new MediaShareVideoPlayerPresenter(backTitle);
        MediaShareVideoPlayerRouter router = new MediaShareVideoPlayerRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.navigator = navigator;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void presentDMRList() {
        dmrList =  MediaShareDMRListRouter.setupModule(context,this,true);

//        Blurry.with(((MediaShareVideoPlayerFragment)view).getActivity().getApplicationContext()).radius(10).sampling(2).onto(((MediaShareVideoPlayerFragment)view).getActivity().findViewById(R.id.media_share_video_player_container));
        FragmentTransaction fragmentTransaction = ((MediaShareVideoPlayerFragment)view).getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }

    @Override
    public void didClosed() {
//        Blurry.delete(((MediaShareVideoPlayerFragment)view).getActivity().findViewById(R.id.media_share_video_player_container));
        ((MediaShareVideoPlayerFragment) view).getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

