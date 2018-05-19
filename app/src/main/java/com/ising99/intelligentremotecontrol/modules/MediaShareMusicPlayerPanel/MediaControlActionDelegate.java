package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

interface MediaControlActionDelegate {
    void didTapOnPlayback();
    void didTapOnCast();
    void didTapOnFastBackward();
    void didTapOnFastForward();
    void didMediaOnProgressChanged(int i, boolean b);
    void didVolumeOnProgressChanged(int i, boolean b);
    void didVolumeOnStartTrackingTouch();
    void didMediaOnStartTrackingTouch();
    void didVolumeOnStopTrackingTouch();
    void didMediaOnStopTrackingTouch();
    void didLoadHolder();
}
