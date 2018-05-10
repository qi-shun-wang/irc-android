package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.app.FragmentTransaction;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.View;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListRouter implements Wireframe ,MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private MediaShareDMRListFragment dmrList;

    public MediaShareVideoListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoListFragment setupModule(Context context, List<Video> collection, DLNAMediaManager manager) {

        MediaShareVideoListFragment view = new MediaShareVideoListFragment();
        MediaShareVideoListInteractor interactor = new MediaShareVideoListInteractor(context,collection,manager);
        MediaShareVideoListPresenter presenter = new MediaShareVideoListPresenter();
        MediaShareVideoListRouter router = new MediaShareVideoListRouter(context);

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
        dmrList =  MediaShareDMRListRouter.setupModule(context,this);

        Blurry.with(((MediaShareVideoListFragment)view).getActivity().getApplicationContext()).radius(10).sampling(2).onto(((MediaShareVideoListFragment)view).getActivity().findViewById(R.id.media_share_music_list_container));
        FragmentTransaction fragmentTransaction = ((MediaShareVideoListFragment)view).getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void didClosed() {
        Blurry.delete(((MediaShareVideoListFragment)view).getActivity().findViewById(R.id.media_share_music_list_container));
        ((MediaShareVideoListFragment) view).getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

