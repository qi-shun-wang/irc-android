package com.ising99.intelligentremotecontrol.modules.IRC.mode;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.DirectionTouchPad;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCActionDelegate;

/**
 * A simple {@link Fragment} subclass.
 */

public class IRCTouchFragment extends Fragment {

    private IRCActionDelegate delegate;

    public IRCTouchFragment() {}
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_irc_touch, container, false);
        image = v.findViewById(R.id.irc_touch_dpad_image);
        image.setAlpha(0f);
        v.findViewById(R.id.irc_normal_power_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchPowerAction();});
        v.findViewById(R.id.irc_normal_volume_p_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchVolumeAction(true);});
        v.findViewById(R.id.irc_normal_volume_m_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchVolumeAction(false);});
        v.findViewById(R.id.irc_normal_mode_btn).setOnClickListener(view -> {if(delegate != null) delegate.dispatchModeAction();});

        v.findViewById(R.id.irc_normal_home_btn).setOnClickListener(view -> {
            if(delegate != null) delegate.dispatchKodAction();
            image.setImageResource(R.drawable.irc_touch_home_icon);
            performAnimation();
        });

        v.findViewById(R.id.irc_normal_menu_btn).setOnClickListener(view -> {
            if(delegate != null) delegate.dispatchMenuAction();
            image.setImageResource(R.drawable.irc_touch_menu_icon);
            performAnimation();
        });

        v.findViewById(R.id.irc_normal_back_btn).setOnClickListener(view -> {
            if(delegate != null) delegate.dispatchBackAction();
            image.setImageResource(R.drawable.irc_touch_back_icon);
            performAnimation();
        });

        ((DirectionTouchPad)v.findViewById(R.id.irc_touch_dpad)).setDelegate((action ->
        {
            if (delegate == null) return;
            delegate.dispatchDirection(action);

            switch (action)
            {
                case up:
                    image.setImageResource(R.drawable.irc_touch_arrow_up_icon);
                    performAnimation();
                    break;
                case down:
                    image.setImageResource(R.drawable.irc_touch_arrow_down_icon);
                    performAnimation();
                    break;
                case left:
                    image.setImageResource(R.drawable.irc_touch_arrow_left_icon);
                    performAnimation();
                    break;
                case right:
                    image.setImageResource(R.drawable.irc_touch_arrow_right_icon);
                    performAnimation();
                    break;
                case center:
                    break;
            }
        }));

        return v;
    }

    private void performAnimation(){
        image.setAlpha(1f);
        image.animate().alpha(0).setDuration(500).start();
    }

    public void setDelegate(IRCActionDelegate delegate) {
        this.delegate = delegate;
    }
}
