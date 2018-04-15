package com.ising99.intelligentremotecontrol.modules.IRC;

/**
 * Created by Shun on 2018/4/12 下午 02:16:54.
 * .
 */


public interface IRCActionDelegate {
    void dispatchUpAction();
    void dispatchDownAction();
    void dispatchLeftAction();
    void dispatchRightAction();
    void dispatchEnterAction();
    void dispatchPowerAction();
    void dispatchNumPanelAction();
    void dispatchMediaPanelAction();
    void dispatchModeAction();
    void dispatchMenuAction();
    void dispatchPlaybackAction();
    void dispatchBackAction();
    void dispatchVolumeAction(boolean isIncrease);
    void dispatchChannelAction(boolean isIncrease);
    void dispatchKodAction();
}
