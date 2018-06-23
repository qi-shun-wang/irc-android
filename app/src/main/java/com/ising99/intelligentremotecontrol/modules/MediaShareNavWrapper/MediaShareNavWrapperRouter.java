package com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper;

import android.app.Fragment;
import android.content.Context;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.View;

import java.util.ArrayList;
import java.util.List;

public class MediaShareNavWrapperRouter implements Wireframe ,Navigator {

    private Context context;
    private Presenter presenter;
    private View view;
    private List<Fragment> stack = new ArrayList<>();
    private DLNAMediaManagerProtocol manager;

    private MediaShareNavWrapperRouter(Context context) {
        this.context = context;
    }

    public static MediaShareNavWrapperFragment setupModule(Context context, DLNAMediaManagerProtocol manager) {

        MediaShareNavWrapperFragment view = new MediaShareNavWrapperFragment();
        MediaShareNavWrapperInteractor interactor = new MediaShareNavWrapperInteractor(context);
        MediaShareNavWrapperPresenter presenter = new MediaShareNavWrapperPresenter();
        MediaShareNavWrapperRouter router = new MediaShareNavWrapperRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.manager = manager;

        interactor.setupPresenter(presenter);

        return view;

    }

    @Override
    public void createRootFragment() {
        MediaShareFragment mediaShare = MediaShareRouter.setupModule(context,this, manager);
        stack.clear();
        stack.add(mediaShare);
        ((MediaShareNavWrapperFragment) view).getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_container,mediaShare).commit();
    }

    @Override
    public void push(Fragment fragment) {
        ((MediaShareNavWrapperFragment) view).getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_container,fragment).addToBackStack(null).commit();
        stack.add(fragment);
    }

    @Override
    public Fragment pop() {
        Fragment item = stack.remove(stack.size()-1);
        ((MediaShareNavWrapperFragment) view).getFragmentManager().beginTransaction()
                .replace(R.id.fragment_media_share_container,stack.get(stack.size()-1))
                .remove(item)
                .commit();
        return item;
    }
}

