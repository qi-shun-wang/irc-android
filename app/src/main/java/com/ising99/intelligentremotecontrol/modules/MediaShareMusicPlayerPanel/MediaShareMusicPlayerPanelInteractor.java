package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;

import java.util.List;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelInteractor implements MediaShareMusicPlayerPanelContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Music> assets;
    private int currentIndex;

    MediaShareMusicPlayerPanelInteractor(Context context, List<Music> assets, int position) {
        this.context = context;
        this.currentIndex = position;
        this.assets = assets;
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
    public Music getCurrentMusicAsset() {
        return assets.get(currentIndex);
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }

    @Override
    public List<Music> getMusicAssets() {
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

    @Override
    public Music playLast() {
        if (currentIndex>0){
            currentIndex--;
        }else {
            currentIndex = assets.size()-1;
        }

        return assets.get(currentIndex);
    }

    @Override
    public Music updateCurrentIndex(int index) {
        currentIndex = index;
        return assets.get(currentIndex);
    }
}

