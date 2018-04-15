package com.ising99.intelligentremotecontrol.modules.IRC.mode;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.CircularButton;
import com.ising99.intelligentremotecontrol.component.DoubleSideButton;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCActionDelegate;

/**
 * A simple {@link Fragment} subclass.
 */
public class IRCDefaultFragment extends Fragment {

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }

    public IRCDefaultFragment() {}

    private IRCActionDelegate delegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_irc_default, container, false);

        v.findViewById(R.id.irc_default_power_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchPowerAction();});
        v.findViewById(R.id.irc_default_number_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPanelAction();});
        v.findViewById(R.id.irc_default_media_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchMediaPanelAction();});
        v.findViewById(R.id.irc_default_mode_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchModeAction();});

        ((CircularButton)v.findViewById(R.id.irc_default_dpad)).setDelegate((action) ->
        {
            switch (action)
            {
                case up:    delegate.dispatchUpAction();break;
                case down:  delegate.dispatchDownAction();break;
                case left:  delegate.dispatchLeftAction();break;
                case right: delegate.dispatchRightAction();break;
                case center:delegate.dispatchEnterAction();break;
            }
        });

        v.findViewById(R.id.irc_default_menu_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchMenuAction();});
        v.findViewById(R.id.irc_default_playback_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchPlaybackAction();});
        v.findViewById(R.id.irc_default_back_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchBackAction();});

        ((DoubleSideButton)v.findViewById(R.id.irc_default_channel_ds_btn)).setDelegate((isUpSide)->{if(delegate != null) delegate.dispatchChannelAction(isUpSide);});
        v.findViewById(R.id.irc_default_kod_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchKodAction();});
        ((DoubleSideButton)v.findViewById(R.id.irc_default_volume_ds_btn)).setDelegate((isUpSide)->{if(delegate != null) delegate.dispatchVolumeAction(isUpSide);});
        return  v;
    }


}
