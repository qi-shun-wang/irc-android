package com.ising99.intelligentremotecontrol.modules.IRC.mode;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCActionDelegate;

/**
 * A simple {@link Fragment} subclass.
 */
public class IRCTextingFragment extends Fragment {

    private IRCActionDelegate delegate;

    public IRCTextingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_irc_texting, container, false);
        v.findViewById(R.id.irc_normal_power_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchPowerAction();});
        v.findViewById(R.id.irc_normal_volume_p_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchVolumeAction(true);});
        v.findViewById(R.id.irc_normal_volume_m_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchVolumeAction(false);});
        v.findViewById(R.id.irc_normal_mode_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchModeAction();});

        v.findViewById(R.id.irc_normal_home_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchKodAction();});
        v.findViewById(R.id.irc_normal_menu_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchMenuAction();});
        v.findViewById(R.id.irc_normal_back_btn).setOnClickListener((view) -> {if(delegate != null) delegate.dispatchBackAction();});

        ((EditText)v.findViewById(R.id.irc_texting_edit_text)).setOnEditorActionListener(((textView, i, keyEvent) -> {
            if (delegate == null) return false;
            if ( (i == EditorInfo.IME_ACTION_DONE) || ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN ))) {

                // Do stuff when user presses enter
                delegate.dispatchTextAction(textView.getText().toString());
                textView.setText("");
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(0,0);
                }
                return true;

            }
            return false;
        }));

        return v;
    }

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }
}
