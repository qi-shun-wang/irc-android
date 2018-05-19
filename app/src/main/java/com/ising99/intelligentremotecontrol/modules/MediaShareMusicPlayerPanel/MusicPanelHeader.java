package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

public class MusicPanelHeader {

    private Music asset;

    MusicPanelHeader(Music asset) {
        this.asset = asset;
    }

    public Music getAsset() {
        return asset;
    }

}
