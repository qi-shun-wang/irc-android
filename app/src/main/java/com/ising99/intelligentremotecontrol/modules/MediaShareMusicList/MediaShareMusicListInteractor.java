package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.InteractorOutput;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListInteractor implements MediaShareMusicListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> collection;

    MediaShareMusicListInteractor(Context context, List<Music> collection) {
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
    public List<Music> getMusicAssets() {
        return collection;
    }

}

