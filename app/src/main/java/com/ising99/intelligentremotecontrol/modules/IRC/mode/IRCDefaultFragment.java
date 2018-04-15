package com.ising99.intelligentremotecontrol.modules.IRC.mode;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.CircularButton;
import com.ising99.intelligentremotecontrol.component.DoubleSideButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class IRCDefaultFragment extends Fragment {


    public IRCDefaultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_irc_default, container, false);

        ((CircularButton)v.findViewById(R.id.irc_default_dpad)).setDelegate((action)-> Log.d("Action",action.toString()));
        ((DoubleSideButton)v.findViewById(R.id.irc_ds_btn_channel)).setDelegate((action)-> Log.d("Action",action.toString()));
        ((DoubleSideButton)v.findViewById(R.id.irc_ds_btn_volume)).setDelegate((action)-> Log.d("Action",action.toString()));
        return  v;
    }


}
