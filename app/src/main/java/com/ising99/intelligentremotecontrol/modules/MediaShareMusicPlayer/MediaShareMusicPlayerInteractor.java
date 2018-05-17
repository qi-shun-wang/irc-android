package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.InteractorOutput;

import java.util.List;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerInteractor implements MediaShareMusicPlayerContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> assets;
    private int currentIndex;

    MediaShareMusicPlayerInteractor(Context context, List<Music> assets, int position) {
        this.context = context;
        this.assets = assets;
        this.currentIndex = position;
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
    public Music getCurrentAsset() {
        return assets.get(currentIndex);
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public List<Music> getAssets() {
        return assets;
    }

    @Override
    public Music playNext() {
        if (currentIndex<assets.size()-1){
            currentIndex++;
        }else {
            currentIndex = 0;
        }

        return assets.get(currentIndex);
    }
}

