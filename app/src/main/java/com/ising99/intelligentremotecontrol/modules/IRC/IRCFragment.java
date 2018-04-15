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
import com.ising99.intelligentremotecontrol.modules.IRC.panel.ModePanelFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;

import jp.wasabeef.blurry.Blurry;

public class IRCFragment extends Fragment
        implements
        IRCContracts.View,
        MediaShareDMRListFragmentDelegate,
        IRCActionDelegate,
        IRCSelectModeDelegate
{

    private Presenter presenter;
    private MediaShareDMRListFragment dmrListFragment;
    private IRCDefaultFragment default_mode;
    private IRCNormalFragment normal_mode;
    private IRCTextingFragment input_mode;
    private IRCTouchFragment touch_mode;
    private ModePanelFragment modePanel;
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

        getFragmentManager().beginTransaction().add(R.id.fragment_mode_content_container,default_mode).commit();

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
    public void didClosed() {
        getFragmentManager().beginTransaction().remove(dmrListFragment).commit();
        Blurry.delete(getActivity().findViewById(R.id.irc_container));
        getActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
        dmrListFragment = null;
    }

    @Override
    public void didSelected() {

    }

    @Override
    public void dispatchUpAction() {
        presenter.didSend(SendCode.KEYCODE_DPAD_UP);
    }

    @Override
    public void dispatchDownAction() {
        presenter.didSend(SendCode.KEYCODE_DPAD_DOWN);
    }

    @Override
    public void dispatchLeftAction() {
        presenter.didSend(SendCode.KEYCODE_DPAD_LEFT);
    }

    @Override
    public void dispatchRightAction() {
        presenter.didSend(SendCode.KEYCODE_DPAD_RIGHT);
    }

    @Override
    public void dispatchEnterAction() {
        presenter.didSend(SendCode.KEYCODE_ENTER);
    }

    @Override
    public void dispatchPowerAction() {
        presenter.didSend(SendCode.KEYCODE_POWER);
    }

    @Override
    public void dispatchNumPanelAction() {

    }

    @Override
    public void dispatchMediaPanelAction() {

    }

    @Override
    public void dispatchModeAction() {
        getFragmentManager().beginTransaction().show(modePanel).commit();
    }

    @Override
    public void dispatchMenuAction() {
        presenter.didSend(SendCode.KEYCODE_MENU);
    }

    @Override
    public void dispatchPlaybackAction() {
        presenter.didSend(SendCode.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    @Override
    public void dispatchBackAction() {
        presenter.didSend(SendCode.KEYCODE_BACK);
    }

    @Override
    public void dispatchVolumeAction(boolean isIncrease) {
        if (isIncrease)
        {
            presenter.didSend(SendCode.KEYCODE_VOLUME_UP);
        }
        else
        {
            presenter.didSend(SendCode.KEYCODE_VOLUME_DOWN);
        }
    }

    @Override
    public void dispatchChannelAction(boolean isIncrease) {
        if (isIncrease)
        {
            presenter.didSend(SendCode.KEYCODE_CHANNEL_UP);
        }
        else
        {
            presenter.didSend(SendCode.KEYCODE_CHANNEL_DOWN);
        }
    }

    @Override
    public void dispatchKodAction() {
        presenter.didSend(SendCode.KEYCODE_KOD_PLUS);
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
    public void didSelectedMouseMode() {
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
    }
}
