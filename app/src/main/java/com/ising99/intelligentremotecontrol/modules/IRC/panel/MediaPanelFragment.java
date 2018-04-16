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
public class MediaPanelFragment extends Fragment {


    public MediaPanelFragment() {}
    private IRCActionDelegate delegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_media_panel, container, false);
        v.findViewById(R.id.irc_media_panel_close_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dismissMediaPadAction();});
        v.findViewById(R.id.irc_media_panel_terminate_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchTerminateAction();});
        v.findViewById(R.id.irc_media_panel_mute_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchMuteAction();});
        v.findViewById(R.id.irc_media_panel_insert_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchInsertAction();});
        v.findViewById(R.id.irc_media_panel_tuning_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchTuningAction();});
        v.findViewById(R.id.irc_media_panel_player_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchPlayerAction();});
        v.findViewById(R.id.irc_media_panel_vocal_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchVocalAction();});
        v.findViewById(R.id.irc_media_panel_record_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchRecordAction();});
        v.findViewById(R.id.irc_media_panel_review_btn).setOnClickListener((view)-> {if(delegate != null) delegate.dispatchReviewAction();});
        return v;
    }

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }
}
