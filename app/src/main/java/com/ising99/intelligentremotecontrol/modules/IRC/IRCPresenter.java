package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.View;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCSelectModeDelegate;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCPresenter implements Presenter, InteractorOutput ,IRCActionDelegate , IRCSelectModeDelegate {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    IRCPresenter(){}

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View)view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor)interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe)router;
    }

    @Override
    public void decompose() {

    }

    @Override
    public void onCreate() {
        router.composingModes();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        router.decomposingModes();
    }

    @Override
    public void dispatchTextAction(String message) {
        interactor.perform(message);
    }

    @Override
    public void dispatchUpAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_UP);
    }

    @Override
    public void dispatchDownAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_DOWN);
    }

    @Override
    public void dispatchLeftAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_LEFT);
    }

    @Override
    public void dispatchRightAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_RIGHT);
    }

    @Override
    public void dispatchEnterAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_CENTER);
    }

    @Override
    public void dispatchPowerAction() {
        interactor.perform(SendCode.KEYCODE_POWER);
    }

    @Override
    public void dispatchMenuAction() {
        interactor.perform(SendCode.KEYCODE_MENU);
    }

    @Override
    public void dispatchPlaybackAction() {
        interactor.perform(SendCode.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    @Override
    public void dispatchBackAction() {
        interactor.perform(SendCode.KEYCODE_BACK);
    }


    @Override
    public void dispatchVolumeAction(boolean isIncrease) {
        if (isIncrease)
        {
            interactor.perform(SendCode.KEYCODE_VOLUME_UP);
        }
        else
        {
            interactor.perform(SendCode.KEYCODE_VOLUME_DOWN);
        }
    }

    @Override
    public void dispatchChannelAction(boolean isIncrease) {
        if (isIncrease)
        {
            interactor.perform(SendCode.KEYCODE_CHANNEL_UP);
        }
        else
        {
            interactor.perform(SendCode.KEYCODE_CHANNEL_DOWN);
        }
    }

    @Override
    public void dispatchKodAction() {
        interactor.perform(SendCode.KEYCODE_HOME);
    }

    @Override
    public void dispatchNumPadAction(int number) {
        interactor.perform(SendCode.numberConvert(number));
    }

    @Override
    public void dispatchDeleteAction() {
        interactor.perform(SendCode.KEYCODE_DEL);
    }

    @Override
    public void dispatchMuteAction() {
        interactor.perform(SendCode.KEYCODE_VOLUME_MUTE);
    }

    @Override
    public void dispatchInsertAction() {
        interactor.perform(SendCode.KEYCODE_INSERT_SONG);
    }

    @Override
    public void dispatchTerminateAction() {
        interactor.perform(SendCode.KEYCODE_PASS_SONG);
    }

    @Override
    public void dispatchTuningAction() {
        interactor.perform(SendCode.KEYCODE_TUNING);
    }

    @Override
    public void dispatchPlayerAction() {
        interactor.perform(SendCode.KEYCODE_PLAY_CONTROL);
    }

    @Override
    public void dispatchVocalAction() {
        interactor.perform(SendCode.KEYCODE_MAN_WOMEN);
    }

    @Override
    public void dispatchRecordAction() {
        interactor.perform(SendCode.KEYCODE_RECORD);
    }

    @Override
    public void dispatchReviewAction() {
        interactor.perform(SendCode.KEYCODE_APPRECIATE);
    }

    @Override
    public void dispatchReviewLAction() {
        interactor.performL(SendCode.KEYCODE_APPRECIATE);
    }

    @Override
    public void didSelectedDefaultMode() {
        router.presentDefaultMode();
        router.dismissModePanel();
    }

    @Override
    public void didSelectedNormalMode() {
        router.presentNormalMode();
        router.dismissModePanel();
    }

    @Override
    public void didSelectedTouchMode() {
        router.presentTouchMode();
        router.dismissModePanel();
    }

    @Override
    public void didSelectedTextingMode() {
        router.presentTextingMode();
        router.dismissModePanel();
    }

    @Override
    public void didSelectedGameMode() {
        router.presentGameMode();
        router.dismissModePanel();
    }

    @Override
    public void didCanceledSelection() {
        router.dismissModePanel();
    }

    @Override
    public void dispatchNumPanelAction() {
        router.presentNumPanel();
    }

    @Override
    public void dispatchMediaPanelAction() {
        router.presentMediaPanel();
    }

    @Override
    public void dispatchModeAction() {
        router.presentModePanel();
    }

    @Override
    public void dismissNumPadAction() {
        router.dismissNumPanel();
    }

    @Override
    public void dismissMediaPadAction() {
        router.dismissMediaPanel();
    }

    @Override
    public void didSent() {

    }

    @Override
    public void failure(String msg) {

    }

    @Override
    public String getAddress() {
        return interactor.getAddress();
    }
}
