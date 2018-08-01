package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.component.Action;
import com.ising99.intelligentremotecontrol.core.CoapClient.GameCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.View;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCSelectModeDelegate;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCPresenter implements Presenter, InteractorOutput ,IRCActionDelegate , IRCSelectModeDelegate {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private Timer worker;
    private boolean isDeviceConnected = false;
    private boolean isViewDetach = false;

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
        view.setupActionBinding();
//        view.updateNetworkStatus("尚未連接WiFi");
        view.setupDisconnectedDeviceImage();
        view.updateConnectedDeviceStatus("尚未連接到設備");
    }

    @Override
    public void onResume() {
        isViewDetach = false;
        isDeviceConnected = false;
        interactor.checkWiFiStatus();
    }

    @Override
    public void onPause() {
        isViewDetach = true;

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
    public void dispatchDirection(Action action, float shiftValue) {
        interactor.fetchGameEventNumber();
        switch (action){
            case vertical:
                interactor.dispatchAxis(GameCode.THUMB_L_AXIS_Y,""+shiftValue*20);
                break;
            case horizontal:
                interactor.dispatchAxis(GameCode.THUMB_L_AXIS_X,"-"+shiftValue*20);
                break;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                interactor.dispatchAxis(GameCode.THUMB_L_AXIS_Y,"-"+0.0);
                interactor.dispatchAxis(GameCode.THUMB_L_AXIS_X,""+36000);
            }
        },500);
    }

    @Override
    public void dispatchDirection(Action action) {
        switch (action){
            case up:interactor.perform(SendCode.KEYCODE_DPAD_UP);break;
            case down:interactor.perform(SendCode.KEYCODE_DPAD_DOWN);break;
            case right:interactor.perform(SendCode.KEYCODE_DPAD_RIGHT);break;
            case left:interactor.perform(SendCode.KEYCODE_DPAD_LEFT);break;
            case center:
                interactor.perform(SendCode.KEYCODE_DPAD_CENTER);
                break;
        }
    }

    @Override
    public void dispatchDirectionBegan(Action action) {
        switch (action){
            case up:interactor.performBegan(SendCode.KEYCODE_DPAD_UP);break;
            case down:interactor.performBegan(SendCode.KEYCODE_DPAD_DOWN);break;
            case right:interactor.performBegan(SendCode.KEYCODE_DPAD_RIGHT);break;
            case left:interactor.performBegan(SendCode.KEYCODE_DPAD_LEFT);break;
            case center:interactor.performBegan(SendCode.KEYCODE_DPAD_CENTER);break;
        }
    }

    @Override
    public void dispatchDirectionEnd(Action action) {
        switch (action){
            case up:interactor.performEnd(SendCode.KEYCODE_DPAD_UP);break;
            case down:interactor.performEnd(SendCode.KEYCODE_DPAD_DOWN);break;
            case right:interactor.performEnd(SendCode.KEYCODE_DPAD_RIGHT);break;
            case left:interactor.performEnd(SendCode.KEYCODE_DPAD_LEFT);break;
            case center:interactor.performEnd(SendCode.KEYCODE_DPAD_CENTER);break;
        }
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

    @Override
    public void didTapOnDeviceDiscovery() {
        router.openDeviceDiscovery();
    }

    @Override
    public void onWindowFocusChanged(boolean isFocus) {
        if (isFocus) {
            worker = new Timer();
            worker.schedule(new TimerTask() {
                @Override
                public void run() {
                    interactor.checkWiFiStatus();
                }
            }, 1000, 2000);

        } else {
            isDeviceConnected = false;
            worker.cancel();
        }
    }

    @Override
    public void didLastConnectionInvalid() {
        if (isViewDetach) return;
        view.updateConnectedDeviceStatus("尚未連接到設備");
    }

    @Override
    public void didFetchGameEventNumberFailure() {

    }

    @Override
    public void didFetchGameEventNumberSuccess() {

    }

    @Override
    public void didConnectedToWiFi(String name) {
        if (isViewDetach) return;
        if (isDeviceConnected) {
            return;
        }
        view.hideWarningBadge();
        interactor.checkLastConnectedDevice();
//        view.updateConnectedDeviceStatus("尚未連接到設備");
    }

    @Override
    public void didNotConnectedToWiFi() {
        if (isViewDetach) return;
        isDeviceConnected = false;
        view.showWarningBadge();
        view.updateConnectedDeviceStatus("等待連接WiFi...");
    }

    @Override
    public void didConnectedToDevice(Device device) {
        if (isViewDetach) return;
        isDeviceConnected = true;
        view.updateConnectedDeviceStatus("目前已連到設備：" + device.getName());
        view.setupConnectedDeviceImage();
    }
}
