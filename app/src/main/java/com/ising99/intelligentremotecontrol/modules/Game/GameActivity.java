package com.ising99.intelligentremotecontrol.modules.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;

/**
 * Created by shun on 2018/4/18.
 */

public class GameActivity extends Activity {
    GameFragment game;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irc_game);

        Intent i = getIntent();
        String ip = i.getExtras().getString("ADDRESS","");
        int port = i.getExtras().getInt("PORT",5683);
        game = GameRouter.setupModule(getApplicationContext(),new RemoteControlCoAPService(ip,port));
        getFragmentManager().beginTransaction().replace(R.id.irc_game_content_container,game).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
