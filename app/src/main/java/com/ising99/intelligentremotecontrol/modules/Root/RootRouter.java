package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Fragment;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperRouter;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCRouter;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingFragment;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingRouter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private Fragment view ;
    private List<Fragment> fragments = new ArrayList<>();
    private DLNAMediaManagerProtocol manager;

    private RootRouter(Context context) {
        this.context = context;
    }

    static RootFragment setupModule(Context context, RemoteControlCoAPService service, DLNAMediaManagerProtocol manager){

        RootFragment view = new RootFragment();
        RootInteractor interactor = new RootInteractor(context);
        RootPresenter presenter = new RootPresenter();
        RootRouter router = new RootRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.manager = manager;

        interactor.setupPresenter(presenter);

        IRCFragment irc = IRCRouter.setupModule(context, service);
//        KaraokeFragment karaoke = KaraokeRouter.setupModule(context);
//        KaraokeArtistFinderFragment karaoke = KaraokeArtistFinderRouter.setupModule(context);
//        KaraokeFinderFragment karaokeFinder = KaraokeFinderRouter.setupModule(context);
//        KaraokeFavoriteCollectionFragment karaokeFavoriteCollectionFragment = KaraokeFavoriteCollectionRouter.setupModule(context);

//        MoreFragment more = MoreRouter.setupModule(context);
//        WebBrowserFragment web = WebBrowserRouter.setupModule(context);
//        MovieFragment movie = MovieRouter.setupModule(context);
//        MediaShareFragment mediaShare = MediaShareRouter.setupModule(context);
        MediaShareNavWrapperFragment mediaShare = MediaShareNavWrapperRouter.setupModule(context, manager);
//        KaraokeNavWrapperFragment karaoke = KaraokeNavWrapperRouter.setupModule(context);
//        ScreenSharingFragment screenSharing = ScreenSharingRouter.setupModule(context);
        router.fragments.add(irc);

//        router.fragments.add(web);
//        router.fragments.add(more);
        router.fragments.add(mediaShare);
//        router.fragments.add(karaoke);
//        router.fragments.add(screenSharing);
//        router.fragments.add(movie);
        return view;
    }

    @Override
    public void presentTabAt(int index) {
        view.getFragmentManager().beginTransaction().replace(R.id.root_content_container,fragments.get(index)).commit();
    }

}
