package com.ising99.intelligentremotecontrol.modules.IRC;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCDefaultFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCNormalFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCSelectModeDelegate;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCTextingFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCTouchFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.MediaPanelFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.ModePanelFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.NumberPanelFragment;

public class IRCFragment extends Fragment
        implements
        IRCContracts.View,
        IRCActionDelegate,
        IRCSelectModeDelegate
{

    private Presenter presenter;
    private IRCDefaultFragment default_mode;
    private IRCNormalFragment normal_mode;
    private IRCTextingFragment input_mode;
    private IRCTouchFragment touch_mode;
    private ModePanelFragment modePanel;
    private NumberPanelFragment numPanel;
    private MediaPanelFragment mediaPanel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_irc, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        default_mode = new IRCDefaultFragment();
        normal_mode = new IRCNormalFragment();
        input_mode = new IRCTextingFragment();
        touch_mode = new IRCTouchFragment();

        default_mode.setDelegate(this);
        normal_mode.setDelegate(this);
        input_mode.setDelegate(this);
        touch_mode.setDelegate(this);

        getFragmentManager().beginTransaction().add(R.id.fragment_mode_content_container,default_mode).commit();

        numPanel = new NumberPanelFragment();
        numPanel.setDelegate(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_num_content_container,numPanel).commit();
        getFragmentManager().beginTransaction().hide(numPanel).commit();

        mediaPanel = new MediaPanelFragment();
        mediaPanel.setDelegate(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_media_content_container,mediaPanel).commit();
        getFragmentManager().beginTransaction().hide(mediaPanel).commit();

        modePanel = new ModePanelFragment();
        modePanel.setDelegate(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_mode_list_content_container,modePanel).commit();
        getFragmentManager().beginTransaction().hide(modePanel).commit();
        presenter = new IRCPresenter(getActivity().getApplicationContext(), this);
        presenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
        Runtime.getRuntime().gc();
    }

    @Override
    public void dispatchTextAction(String message) {
        presenter.didSent(message);
    }

    @Override
    public void dispatchUpAction() {
        presenter.didSent(SendCode.KEYCODE_DPAD_UP);
    }

    @Override
    public void dispatchDownAction() {
        presenter.didSent(SendCode.KEYCODE_DPAD_DOWN);
    }

    @Override
    public void dispatchLeftAction() {
        presenter.didSent(SendCode.KEYCODE_DPAD_LEFT);
    }

    @Override
    public void dispatchRightAction() {
        presenter.didSent(SendCode.KEYCODE_DPAD_RIGHT);
    }

    @Override
    public void dispatchEnterAction() {
        presenter.didSent(SendCode.KEYCODE_ENTER);
    }

    @Override
    public void dispatchPowerAction() {
        presenter.didSent(SendCode.KEYCODE_POWER);
    }

    @Override
    public void dispatchNumPanelAction() {
        getFragmentManager().beginTransaction().show(numPanel).commit();
    }

    @Override
    public void dispatchMediaPanelAction() {
        getFragmentManager().beginTransaction().show(mediaPanel).commit();
    }

    @Override
    public void dispatchModeAction() {
        getFragmentManager().beginTransaction().show(modePanel).commit();
    }

    @Override
    public void dispatchMenuAction() {
        presenter.didSent(SendCode.KEYCODE_MENU);
    }

    @Override
    public void dispatchPlaybackAction() {
        presenter.didSent(SendCode.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    @Override
    public void dispatchBackAction() {
        presenter.didSent(SendCode.KEYCODE_BACK);
    }

    @Override
    public void dispatchVolumeAction(boolean isIncrease) {
        if (isIncrease)
        {
            presenter.didSent(SendCode.KEYCODE_VOLUME_UP);
        }
        else
        {
            presenter.didSent(SendCode.KEYCODE_VOLUME_DOWN);
        }
    }

    @Override
    public void dispatchChannelAction(boolean isIncrease) {
        if (isIncrease)
        {
            presenter.didSent(SendCode.KEYCODE_CHANNEL_UP);
        }
        else
        {
            presenter.didSent(SendCode.KEYCODE_CHANNEL_DOWN);
        }
    }

    @Override
    public void dispatchKodAction() {
        presenter.didSent(SendCode.KEYCODE_KOD_PLUS);
    }

    @Override
    public void dismissNumPadAction() {
        getFragmentManager().beginTransaction().hide(numPanel).commit();
    }

    @Override
    public void dismissMediaPadAction() {
        getFragmentManager().beginTransaction().hide(mediaPanel).commit();
    }

    @Override
    public void dispatchNumPadAction(int number) {
        presenter.didSent(number);
    }

    @Override
    public void dispatchMuteAction() {
        presenter.didSent(SendCode.KEYCODE_VOLUME_MUTE);
    }

    @Override
    public void dispatchInsertAction() {
        presenter.didSent(SendCode.SendCode_INSERT_SONG);
    }

    @Override
    public void dispatchTerminateAction() {
        presenter.didSent(SendCode.SendCode_PASS_SONG);
    }

    @Override
    public void dispatchTuningAction() {
        presenter.didSent(SendCode.SendCode_TUNING);
    }

    @Override
    public void dispatchPlayerAction() {
        presenter.didSent(SendCode.SendCode_PLAY_CONTROL);
    }

    @Override
    public void dispatchVocalAction() {
        presenter.didSent(SendCode.SendCode_MAN_WOMEN);
    }

    @Override
    public void dispatchRecordAction() {
        presenter.didSent(SendCode.SendCode_RECORD);
    }

    @Override
    public void dispatchReviewAction() {
        presenter.didSent(SendCode.SendCode_APPRECIATE);
    }

    @Override
    public void didSelectedDefaultMode() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,default_mode).commit();
        getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void didSelectedNormalMode() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,normal_mode).commit();
        getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void didSelectedTouchMode() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,touch_mode).commit();
        getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void didSelectedTextingMode() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,input_mode).commit();
        getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void didSelectedGameMode() {
        getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void didCanceledSelection() {
        getFragmentManager().beginTransaction().hide(modePanel).commit();
        getFragmentManager().beginTransaction().hide(numPanel).commit();
    }
}
