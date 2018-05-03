package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.InteractorOutput;

import java.util.List;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListInteractor implements MediaSharePhotoListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Photo> collection;

    MediaSharePhotoListInteractor(Context context,List<Photo> collection) {
        this.context = context;
        this.collection = collection;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public List<Photo> getPhotos() {
        return collection;
    }
}

