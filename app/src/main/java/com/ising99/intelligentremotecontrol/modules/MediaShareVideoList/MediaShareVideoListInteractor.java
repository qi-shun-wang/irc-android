package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.InteractorOutput;

import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListInteractor implements MediaShareVideoListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Video> collection;

    MediaShareVideoListInteractor(Context context, List<Video> collection) {
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
    public List<Video> getVideoAssets() {
        return collection;
    }

}