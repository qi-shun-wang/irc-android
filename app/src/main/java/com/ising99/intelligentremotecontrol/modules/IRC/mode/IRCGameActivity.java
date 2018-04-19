package com.ising99.intelligentremotecontrol.modules.IRC.mode;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ising99.intelligentremotecontrol.R;

/**
 * Created by shun on 2018/4/18.
 */

public class IRCGameActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irc_game);
        findViewById(R.id.irc_game_back_btn).setOnClickListener((v)->finish());

    }

}
