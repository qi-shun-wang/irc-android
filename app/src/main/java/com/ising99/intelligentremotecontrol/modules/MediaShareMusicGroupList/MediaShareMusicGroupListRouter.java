package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.View;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 04:25:19.
 * .
 */

public class MediaShareMusicGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareMusicGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicGroupListFragment setupModule(Context context) {

        MediaShareMusicGroupListFragment view = new MediaShareMusicGroupListFragment();
        MediaShareMusicGroupListInteractor interactor = new MediaShareMusicGroupListInteractor(context);
        MediaShareMusicGroupListPresenter presenter = new MediaShareMusicGroupListPresenter();
        MediaShareMusicGroupListRouter router = new MediaShareMusicGroupListRouter(context);

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
    public void presentPhotoCollectionWith(String title, List<Music> assets) {

    }
}

