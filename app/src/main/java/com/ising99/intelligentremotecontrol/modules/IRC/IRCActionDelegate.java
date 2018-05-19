package com.ising99.intelligentremotecontrol.modules.IRC;

/**
 * Created by Shun on 2018/4/12 下午 02:16:54.
 * .
 */

public interface IRCActionDelegate {
    void dispatchTextAction(String message);
    void dispatchUpAction();
    void dispatchDownAction();
    void dispatchLeftAction();
    void dispatchRightAction();
    void dispatchEnterAction();
    void dispatchEnterActionL();
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
    void dismissNumPadAction();
    void dismissMediaPadAction();
    void dispatchNumPadAction(int number);
    void dispatchDeleteAction();
    void dispatchMuteAction();
    void dispatchInsertAction();
    void dispatchTerminateAction();
    void dispatchTuningAction();
    void dispatchPlayerAction();
    void dispatchVocalAction();
    void dispatchRecordAction();
    void dispatchReviewAction();
    void dispatchReviewLAction();

}
