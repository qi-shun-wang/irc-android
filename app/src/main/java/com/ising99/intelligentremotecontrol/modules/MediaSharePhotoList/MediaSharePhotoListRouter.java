package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.app.FragmentTransaction;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.View;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListRouter implements Wireframe , MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;

    private MediaShareDMRListFragment dmrList;

    private MediaSharePhotoListRouter(Context context) {
        this.context = context;
    }

    public static MediaSharePhotoListFragment setupModule(Context context, List<Photo> collection, DLNAMediaManager manager) {

        MediaSharePhotoListFragment view = new MediaSharePhotoListFragment();
        MediaSharePhotoListInteractor interactor = new MediaSharePhotoListInteractor(context,collection,manager);
        MediaSharePhotoListPresenter presenter = new MediaSharePhotoListPresenter();
        MediaSharePhotoListRouter router = new MediaSharePhotoListRouter(context);

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

        Blurry.with(((MediaSharePhotoListFragment)view).getActivity().getApplicationContext()).radius(10).sampling(2).onto(((MediaSharePhotoListFragment)view).getActivity().findViewById(R.id.media_share_photo_list_container));
        FragmentTransaction fragmentTransaction = ((MediaSharePhotoListFragment)view).getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_photo_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void didClosed() {
        Blurry.delete(((MediaSharePhotoListFragment)view).getActivity().findViewById(R.id.media_share_photo_list_container));
        ((MediaSharePhotoListFragment)view).getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

