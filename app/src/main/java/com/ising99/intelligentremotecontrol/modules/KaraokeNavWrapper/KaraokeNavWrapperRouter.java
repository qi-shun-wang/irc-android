package com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeFragment;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/6/27 上午 09:31:08.
 * .
 */

public class KaraokeNavWrapperRouter implements Wireframe, BaseNavigator {

    private Context context;
    private Presenter presenter;
    private View view;
    private List<Fragment> stack = new ArrayList<>();

    public KaraokeNavWrapperRouter(Context context) {
        this.context = context;
    }

    public static KaraokeNavWrapperFragment setupModule(Context context) {

        KaraokeNavWrapperFragment view = new KaraokeNavWrapperFragment();
        KaraokeNavWrapperInteractor interactor = new KaraokeNavWrapperInteractor(context);
        KaraokeNavWrapperPresenter presenter = new KaraokeNavWrapperPresenter();
        KaraokeNavWrapperRouter router = new KaraokeNavWrapperRouter(context);

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
    public void createRootFragment() {
        KaraokeFragment karaoke = KaraokeRouter.setupModule(context, this);
        stack.clear();
        stack.add(karaoke);
        ((KaraokeNavWrapperFragment) view).getFragmentManager().beginTransaction().replace(R.id.fragment_karaoke_container,karaoke).commit();
    }

    @Override
    public void push(Fragment fragment) {
        ((KaraokeNavWrapperFragment) view).getFragmentManager().beginTransaction().replace(R.id.fragment_karaoke_container,fragment).addToBackStack(null).commit();
        stack.add(fragment);
    }

    @Override
    public Fragment pop() {
        Fragment item = stack.remove(stack.size()-1);
        ((KaraokeNavWrapperFragment) view).getFragmentManager().beginTransaction()
                .replace(R.id.fragment_karaoke_container,stack.get(stack.size()-1))
                .remove(item)
                .commit();
        return item;
    }
}

