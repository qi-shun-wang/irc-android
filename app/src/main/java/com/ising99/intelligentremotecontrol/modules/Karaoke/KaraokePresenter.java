package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.View;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Wireframe;

/**
 * Created by shun on 2018/4/13 下午 05:16:29.
 * .
 */

public class KaraokePresenter implements Presenter, InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    KaraokePresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new KaraokeInteractor(context, this);
        router = new KaraokeRouter(context, this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        interactor.decompose();
        router.decompose();
        context = null;
        view = null;
        interactor = null;
        router = null;
    }


    //TODO: Implement your Presenter methods here

    //TODO: Implement your InteractorOutput methods here

}
