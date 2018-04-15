package com.ising99.intelligentremotecontrol.modules.IRC.mode;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.DirectionTouchPad;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCActionDelegate;

/**
 * A simple {@link Fragment} subclass.
 */
public class IRCTouchFragment extends Fragment {

    private IRCActionDelegate delegate;

    public IRCTouchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_irc_touch, container, false);
        v.findViewById(R.id.irc_normal_power_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchPowerAction();});
        v.findViewById(R.id.irc_normal_volume_p_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchVolumeAction(true);});
        v.findViewById(R.id.irc_normal_volume_m_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchVolumeAction(false);});
        v.findViewById(R.id.irc_normal_mode_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchModeAction();});

        v.findViewById(R.id.irc_normal_home_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchKodAction();});
        v.findViewById(R.id.irc_normal_menu_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchMenuAction();});
        v.findViewById(R.id.irc_normal_back_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchBackAction();});

        ((DirectionTouchPad)v.findViewById(R.id.irc_touch_dpad)).setDelegate((action ->
        {
            if (delegate == null) return;

            switch (action)
            {
                case up:    delegate.dispatchUpAction();break;
                case down:  delegate.dispatchDownAction();break;
                case left:  delegate.dispatchLeftAction();break;
                case right: delegate.dispatchRightAction();break;
                case center:delegate.dispatchEnterAction();break;
            }
        }));

        return v;
    }

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }
}
