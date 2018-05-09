package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/3 上午 10:22:42.
 * .
 */

public class MediaSharePhotoGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaSharePhotoGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaSharePhotoGroupListFragment setupModule(Context context) {

        MediaSharePhotoGroupListFragment view = new MediaSharePhotoGroupListFragment();
        MediaSharePhotoGroupListInteractor interactor = new MediaSharePhotoGroupListInteractor(context);
        MediaSharePhotoGroupListPresenter presenter = new MediaSharePhotoGroupListPresenter();
        MediaSharePhotoGroupListRouter router = new MediaSharePhotoGroupListRouter(context);

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
    public void presentPhotoCollectionWith(String title, List<Photo> photos) {
        Intent i = new Intent(context, MediaSharePhotoListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("TITLE",title);
        i.putParcelableArrayListExtra("ASSETS", (ArrayList<? extends Parcelable>) photos);
        context.startActivity(i);
    }
}

