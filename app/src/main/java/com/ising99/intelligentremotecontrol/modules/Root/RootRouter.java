package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareRouter;
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
        RootInteractor interactor = new RootInteractor(context, service);
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
//        MoreFragment more = MoreRouter.setupModule(context);
//        WebBrowserFragment web = WebBrowserRouter.setupModule(context);
//        MovieFragment movie = MovieRouter.setupModule(context);
        MediaShareFragment mediaShare = MediaShareRouter.setupModule(context);
        router.fragments.add(irc);
//        router.fragments.add(karaoke);
//        router.fragments.add(web);
//        router.fragments.add(more);
        router.fragments.add(mediaShare);
//        router.fragments.add(movie);
        return view;
    }


    @Override
    public void openDeviceDiscovery() {
        Intent intentToDeviceDiscoveryActivity = new Intent(context, DeviceDiscoveryActivity.class);
        intentToDeviceDiscoveryActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentToDeviceDiscoveryActivity);
    }

    @Override
    public void presentTabAt(int index) {
        view.getFragmentManager().beginTransaction().replace(R.id.root_content_container,fragments.get(index)).commit();
    }


}
