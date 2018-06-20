package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeFragment;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperRouter;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCRouter;

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

    private RootRouter(Context context) {
        this.context = context;
    }

    static RootFragment setupModule(Context context, RemoteControlCoAPService service){

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

        interactor.setupPresenter(presenter);

        IRCFragment irc = IRCRouter.setupModule(context, service);
//        KaraokeFragment karaoke = KaraokeRouter.setupModule(context);
//        KaraokeArtistFinderFragment karaoke = KaraokeArtistFinderRouter.setupModule(context);
//        KaraokeFinderFragment karaokeFinder = KaraokeFinderRouter.setupModule(context);
        KaraokeFavoriteCollectionFragment karaokeFavoriteCollectionFragment = KaraokeFavoriteCollectionRouter.setupModule(context);

//        MoreFragment more = MoreRouter.setupModule(context);
//        WebBrowserFragment web = WebBrowserRouter.setupModule(context);
//        MovieFragment movie = MovieRouter.setupModule(context);
//        MediaShareFragment mediaShare = MediaShareRouter.setupModule(context);
        MediaShareNavWrapperFragment mediaShare = MediaShareNavWrapperRouter.setupModule(context);
        router.fragments.add(irc);

//        router.fragments.add(web);
//        router.fragments.add(more);
        router.fragments.add(mediaShare);
        router.fragments.add(karaokeFavoriteCollectionFragment);
//        router.fragments.add(movie);
        return view;
    }

    @Override
    public void presentTabAt(int index) {
        view.getFragmentManager().beginTransaction().replace(R.id.root_content_container,fragments.get(index)).commit();
    }


}
