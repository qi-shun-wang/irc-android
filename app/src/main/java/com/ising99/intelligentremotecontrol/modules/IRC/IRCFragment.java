package com.ising99.intelligentremotecontrol.modules.IRC;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by Shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCFragment extends Fragment implements IRCContracts.View ,MediaShareDMRListFragmentDelegate{

    private Presenter presenter;
    private MediaShareDMRListFragment dmrListFragment;
    View.OnClickListener tapUpAction = (v) -> presenter.didSend(SendCode.KEYCODE_DPAD_UP);
    View.OnClickListener tapDownAction = (v) -> presenter.didSend(SendCode.KEYCODE_DPAD_DOWN);
    View.OnClickListener tapLeftAction = (v) -> presenter.didSend(SendCode.KEYCODE_DPAD_LEFT);
    View.OnClickListener tapRightAction = (v) -> presenter.didSend(SendCode.KEYCODE_DPAD_RIGHT);
    View.OnClickListener tapEnterAction = (v) -> presenter.didSend(SendCode.KEYCODE_ENTER);
    View.OnClickListener tapMenuAction = (v) -> presenter.didSend(SendCode.KEYCODE_MENU);
    View.OnClickListener tapBackAction = (v) -> presenter.didSend(SendCode.KEYCODE_BACK);
    View.OnClickListener tapVolumeUpAction = (v) -> presenter.didSend(SendCode.KEYCODE_VOLUME_UP);
    View.OnClickListener tapVolumeDownAction = (v) -> presenter.didSend(SendCode.KEYCODE_VOLUME_DOWN);
    View.OnClickListener tapVolumeMuteAction = (v) -> presenter.didSend(SendCode.KEYCODE_VOLUME_MUTE);
    View.OnClickListener tapChannelUpAction = (v) -> presenter.didSend(SendCode.KEYCODE_CHANNEL_UP);
    View.OnClickListener tapChannelDownAction = (v) -> presenter.didSend(SendCode.KEYCODE_CHANNEL_DOWN);
    View.OnClickListener tapPowerAction = (v) -> presenter.didSend(SendCode.KEYCODE_POWER);
    View.OnClickListener tapKODPlusAction = (v) -> presenter.didSend(SendCode.KEYCODE_KOD_PLUS);
    View.OnClickListener tapLanguageAction = (v) -> presenter.didSend(SendCode.KEYCODE_LANGUAGE);
    View.OnClickListener tapPlaybackAction = (v) -> presenter.didSend(SendCode.KEYCODE_MEDIA_PLAY_PAUSE);

    View.OnClickListener tapCastAction = (v) -> {
        dmrListFragment = new MediaShareDMRListFragment();
        dmrListFragment.setDelegate(this);
        getActivity().findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
//        Blurry.with(getApplicationContext()).sampling(8).onto(findViewById(R.id.irc_container));
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up,R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.fragment_container, dmrListFragment, "MediaShareDMRListFragment");
        fragmentTransaction.commit();
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        presenter = new IRCPresenter(getActivity().getApplicationContext(), this);
        view.findViewById(R.id.button_up).setOnClickListener(tapUpAction);
        view.findViewById(R.id.button_down).setOnClickListener(tapDownAction);
        view.findViewById(R.id.button_left).setOnClickListener(tapLeftAction);
        view.findViewById(R.id.button_right).setOnClickListener(tapRightAction);
        view.findViewById(R.id.button_enter).setOnClickListener(tapEnterAction);
        view.findViewById(R.id.button_power).setOnClickListener(tapPowerAction);
        view.findViewById(R.id.button_menu).setOnClickListener(tapMenuAction);
        view.findViewById(R.id.button_kod_plus).setOnClickListener(tapKODPlusAction);
        view.findViewById(R.id.button_back).setOnClickListener(tapBackAction);
        view.findViewById(R.id.button_volume_decrease).setOnClickListener(tapVolumeDownAction);
        view.findViewById(R.id.button_volume_increase).setOnClickListener(tapVolumeUpAction);
        view.findViewById(R.id.button_channel_increase).setOnClickListener(tapChannelUpAction);
        view.findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        view.findViewById(R.id.button_mute).setOnClickListener(tapVolumeMuteAction);
        view.findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        view.findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        view.findViewById(R.id.button_language).setOnClickListener(tapLanguageAction);
        view.findViewById(R.id.button_playback).setOnClickListener(tapPlaybackAction);
        view.findViewById(R.id.button_cast).setOnClickListener(tapCastAction);
        presenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO Setup OnFragmentInteractionListener

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
}
