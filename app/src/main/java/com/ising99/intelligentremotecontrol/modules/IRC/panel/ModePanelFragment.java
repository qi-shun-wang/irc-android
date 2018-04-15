package com.ising99.intelligentremotecontrol.modules.IRC.panel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModePanelFragment extends Fragment {


    public ModePanelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mode_panel, container, false);
    }

}
