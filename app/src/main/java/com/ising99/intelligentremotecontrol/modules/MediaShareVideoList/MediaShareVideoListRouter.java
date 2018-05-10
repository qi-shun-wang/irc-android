package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerActivity;

import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;


    private MediaShareVideoListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoListFragment setupModule(Context context, List<Video> collection, String title) {

        MediaShareVideoListFragment view = new MediaShareVideoListFragment();
        MediaShareVideoListInteractor interactor = new MediaShareVideoListInteractor(context,collection);
        MediaShareVideoListPresenter presenter = new MediaShareVideoListPresenter(title);
        MediaShareVideoListRouter router = new MediaShareVideoListRouter(context);

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
    public void presentVideoPlayer(Video asset,String backTitle) {
        Intent i = new Intent(context, MediaShareVideoPlayerActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("ASSET", asset);
        i.putExtra("BACK_TITLE", backTitle);
        context.startActivity(i);
    }

}

