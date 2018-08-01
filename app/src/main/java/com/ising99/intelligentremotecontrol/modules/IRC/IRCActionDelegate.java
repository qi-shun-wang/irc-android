package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.component.Action;

/**
 * Created by Shun on 2018/4/12 下午 02:16:54.
 * .
 */

public interface IRCActionDelegate {
    void dispatchDirection(Action action,float shiftValue);
    void dispatchDirection(Action action);
    void dispatchDirectionBegan(Action action);
    void dispatchDirectionEnd(Action action);
    void dispatchTextAction(String message);
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
