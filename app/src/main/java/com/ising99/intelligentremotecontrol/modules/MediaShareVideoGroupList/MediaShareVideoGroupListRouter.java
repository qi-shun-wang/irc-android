package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.content.Context;
import android.app.Fragment;
import android.content.Intent;
import android.os.Parcelable;

import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:05:36.
 * .
 */

public class MediaShareVideoGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareVideoGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoGroupListFragment setupModule(Context context) {

        MediaShareVideoGroupListFragment view = new MediaShareVideoGroupListFragment();
        MediaShareVideoGroupListInteractor interactor = new MediaShareVideoGroupListInteractor(context);
        MediaShareVideoGroupListPresenter presenter = new MediaShareVideoGroupListPresenter();
        MediaShareVideoGroupListRouter router = new MediaShareVideoGroupListRouter(context);

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
    public void presentVideoAssetsWith(String title, List<Video> assets) {
        Intent i = new Intent(context, MediaShareVideoListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("TITLE",title);
        i.putParcelableArrayListExtra("ASSETS", (ArrayList<? extends Parcelable>) assets);
        context.startActivity(i);
    }
}

