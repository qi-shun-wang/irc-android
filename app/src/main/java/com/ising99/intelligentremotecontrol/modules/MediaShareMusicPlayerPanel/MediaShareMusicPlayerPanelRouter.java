package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.app.FragmentTransaction;
import android.content.Context;
import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerRouterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

public class MediaShareMusicPlayerPanelRouter implements Wireframe ,MediaShareDMRListFragmentDelegate {

    private Context context;
    private Presenter presenter;
    private View view;
    private MediaShareMusicPlayerRouterDelegate delegate;
    private MediaShareDMRListFragment dmrList;

    private MediaShareMusicPlayerPanelRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicPlayerPanelFragment setupModule(Context context, List<Music> assets, int position, int volumeScale, MediaPlayer player, MediaShareMusicPlayerRouterDelegate delegate, DLNAMediaManager manager, boolean isRemoteMode, boolean isRemotePlaying, boolean shouldPlayRemoteWithSeek) {

        MediaShareMusicPlayerPanelFragment view = new MediaShareMusicPlayerPanelFragment();
        MediaShareMusicPlayerPanelInteractor interactor = new MediaShareMusicPlayerPanelInteractor(context, assets, position, manager);
        MediaShareMusicPlayerPanelPresenter presenter = new MediaShareMusicPlayerPanelPresenter(player, volumeScale, isRemoteMode, isRemotePlaying, shouldPlayRemoteWithSeek);
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
    public void dismissPanelWhen(boolean isPlaying, boolean isRemoteMode, int currentIndex, int volumeScale) {
        delegate.dismissWithPlayerStatus(isPlaying, isRemoteMode, currentIndex, volumeScale);
    }

    @Override
    public void presentDMRList() {
        dmrList =  MediaShareDMRListRouter.setupModule(context,this,false);
        MediaShareMusicPlayerPanelFragment ref = (MediaShareMusicPlayerPanelFragment) view;

        Blurry.with(ref.getActivity().getApplicationContext()).radius(10).sampling(2).onto(ref.getActivity().findViewById(R.id.media_share_music_list_container));
        FragmentTransaction fragmentTransaction = ref.getFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down,R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.media_share_list_dmr_container, dmrList, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void didClosed() {
        MediaShareMusicPlayerPanelFragment ref = (MediaShareMusicPlayerPanelFragment) view;
        Blurry.delete(ref.getActivity().findViewById(R.id.media_share_music_list_container));
        ref.getFragmentManager().beginTransaction().detach(dmrList).commit();
        dmrList = null;
    }

    @Override
    public void didSelected(Device device) {
        presenter.didSelected(device);
    }
}

