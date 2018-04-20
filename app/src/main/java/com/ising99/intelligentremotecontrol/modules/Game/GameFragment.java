package com.ising99.intelligentremotecontrol.modules.Game;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.CircularButton;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Presenter;

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

        ((CircularButton)view.findViewById(R.id.irc_game_dpad)).setDelegate((action) ->
        {
            switch (action)
            {
                case up:    presenter.performUpAction();break;
                case down:  presenter.performDownAction();break;
                case left:  presenter.performLeftAction();break;
                case right: presenter.performRightAction();break;
            }
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
