package com.ising99.intelligentremotecontrol.modules.More;

import android.content.Context;
import android.app.Fragment;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareActivity;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.View;

/**
 * Created by shun on 2018/4/17 下午 10:04:17.
 * .
 */

public class MoreRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MoreRouter(Context context) {
        this.context = context;
    }

    public static MoreFragment setupModule(Context context) {

        MoreFragment view = new MoreFragment();
        MoreInteractor interactor = new MoreInteractor(context);
        MorePresenter presenter = new MorePresenter();
        MoreRouter router = new MoreRouter(context);

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
    public void presentMediaShare() {
        Intent i = new Intent(context, MediaShareActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

