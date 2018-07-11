package com.ising99.intelligentremotecontrol.modules.Game;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.Action;
import com.ising99.intelligentremotecontrol.component.CircularButton;
import com.ising99.intelligentremotecontrol.component.CircularButtonDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Presenter;

import io.github.controlwear.virtual.joystick.android.JoystickView;

/**
 * Created by Shun on 2018/4/20 上午 10:50:03.
 * .
 */

public class GameFragment extends Fragment implements GameContracts.View {

    private Presenter presenter;
    private ViewGroup view;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (Presenter) presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_irc_game, container, false);
        view.findViewById(R.id.irc_game_back_btn).setOnClickListener((v)->getActivity().finish());

        ((CircularButton)view.findViewById(R.id.irc_game_dpad)).setDelegate(new CircularButtonDelegate() {
            @Override
            public void didTouchOn(Action action) {
                presenter.performTouchOn(action);
            }

            @Override
            public void didTouchOnBegan(Action action) {
                presenter.performTouchOnBegan(action);
            }

            @Override
            public void didTouchOnEnd(Action action) {
                presenter.performTouchOnEnd(action);
            }
        });
        view.findViewById(R.id.irc_game_y_btn).setOnClickListener(view1 -> presenter.performYAction());
        view.findViewById(R.id.irc_game_x_btn).setOnClickListener(view1 -> presenter.performXAction());
        view.findViewById(R.id.irc_game_a_btn).setOnClickListener(view1 -> presenter.performAAction());
        view.findViewById(R.id.irc_game_b_btn).setOnClickListener(view1 -> presenter.performBAction());
        view.findViewById(R.id.irc_game_select_btn).setOnClickListener(view1 -> presenter.performSelectAction());
        view.findViewById(R.id.irc_game_start_btn).setOnClickListener(view1 -> presenter.performStartAction());
        ((JoystickView)view.findViewById(R.id.irc_game_joystick_component)).setOnMoveListener((angle, strength) -> {
            presenter.performThumbLeft(angle, strength);
        });

        presenter.onCreate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
