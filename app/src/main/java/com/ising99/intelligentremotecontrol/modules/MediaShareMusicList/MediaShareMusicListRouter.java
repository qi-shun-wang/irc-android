package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.app.FragmentTransaction;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.View;


import org.fourthline.cling.model.meta.Device;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListRouter implements Wireframe ,MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private MediaShareDMRListFragment dmrList;

    private MediaShareMusicListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicListFragment setupModule(Context context, List<Music> collection, DLNAMediaManager manager) {

        MediaShareMusicListFragment view = new MediaShareMusicListFragment();
        MediaShareMusicListInteractor interactor = new MediaShareMusicListInteractor(context,collection,manager);
        MediaShareMusicListPresenter presenter = new MediaShareMusicListPresenter();
        MediaShareMusicListRouter router = new MediaShareMusicListRouter(context);

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
    public void presentDMRList() {
        dmrList =  MediaShareDMRListRouter.setupModule(context,this,false);

        Blurry.with(((MediaShareMusicListFragment)view).getActivity().getApplicationContext()).radius(10).sampling(2).onto(((MediaShareMusicListFragment)view).getActivity().findViewById(R.id.media_share_music_list_container));
        FragmentTransaction fragmentTransaction = ((MediaShareMusicListFragment)view).getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void didClosed() {
        Blurry.delete(((MediaShareMusicListFragment) view).getActivity().findViewById(R.id.media_share_music_list_container));
        ((MediaShareMusicListFragment) view).getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

