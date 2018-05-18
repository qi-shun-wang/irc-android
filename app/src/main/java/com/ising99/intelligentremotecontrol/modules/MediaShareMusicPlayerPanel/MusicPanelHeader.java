package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

public class MusicPanelHeader {

    private Music asset;
    private int currentTimeInterval;
    private int playbackResID;

    public MusicPanelHeader(Music asset, int currentTimeInterval, int playbackResID) {
        this.asset = asset;
        this.currentTimeInterval = currentTimeInterval;
        this.playbackResID = playbackResID;
    }

    public Music getAsset() {
        return asset;
    }

    public int getCurrentTimeInterval() {
        return currentTimeInterval;
    }

    public int getPlaybackResID() {
        return playbackResID;
    }
}
