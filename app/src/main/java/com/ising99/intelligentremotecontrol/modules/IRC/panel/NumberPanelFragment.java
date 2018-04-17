package com.ising99.intelligentremotecontrol.modules.IRC.panel;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCActionDelegate;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberPanelFragment extends Fragment {

    private IRCActionDelegate delegate;
    public NumberPanelFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_number_panel, container, false);
        v.findViewById(R.id.irc_number_panel_close_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dismissNumPadAction();});
        v.findViewById(R.id.irc_number_panel_num0_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(0);});
        v.findViewById(R.id.irc_number_panel_num1_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(1);});
        v.findViewById(R.id.irc_number_panel_num2_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(2);});
        v.findViewById(R.id.irc_number_panel_num3_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(3);});
        v.findViewById(R.id.irc_number_panel_num4_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(4);});
        v.findViewById(R.id.irc_number_panel_num5_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(5);});
        v.findViewById(R.id.irc_number_panel_num6_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(6);});
        v.findViewById(R.id.irc_number_panel_num7_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(7);});
        v.findViewById(R.id.irc_number_panel_num8_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(8);});
        v.findViewById(R.id.irc_number_panel_num9_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchNumPadAction(9);});
        v.findViewById(R.id.irc_number_panel_outside_view).setOnClickListener((view) -> {if(delegate != null) delegate.dismissNumPadAction();});
        return v;
    }

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }
}
