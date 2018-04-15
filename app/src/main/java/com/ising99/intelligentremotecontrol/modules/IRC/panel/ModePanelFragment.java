package com.ising99.intelligentremotecontrol.modules.IRC.panel;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCSelectModeDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModePanelFragment extends Fragment implements ModeListAdapterDelegate {

    private final int DEFAULT_MODE_TAG = 0;
    private final int NORMAL_MODE_TAG = 1;
    private final int TOUCH_MODE_TAG = 2;
//    private final int MOUSE_MODE_TAG = 3;
    private final int INPUT_MODE_TAG = 4;
    private final int GAME_MODE_TAG = 5;

    private IRCSelectModeDelegate delegate;
    public ModePanelFragment() {}

    private List<ModeItem> modes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mode_panel, container, false);
        RecyclerView listView = view.findViewById(R.id.irc_mode_pannel_list);
        view.findViewById(R.id.irc_mode_container).setOnClickListener((v)-> delegate.didCanceledSelection());

        modes.add(new ModeItem(DEFAULT_MODE_TAG, R.drawable.irc_mode_normal_icon,"預設模式"));
        modes.add(new ModeItem(NORMAL_MODE_TAG, R.drawable.irc_mode_normal_icon,"精簡模式"));
        modes.add(new ModeItem(TOUCH_MODE_TAG, R.drawable.irc_mode_touch_icon,"滑動模式"));
//        modes.add(new ModeItem(MOUSE_MODE_TAG, R.drawable.irc_mode_mouse_icon,"滑鼠模式"));
        modes.add(new ModeItem(INPUT_MODE_TAG, R.drawable.irc_mode_keyboard_icon,"輸入模式"));
        modes.add(new ModeItem(GAME_MODE_TAG, R.drawable.irc_mode_game_icon,"遊戲模式"));

        ListViewAdapter adapter = new ListViewAdapter();
        adapter.setupDelegate(this);
        adapter.setModes(modes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

        if (delegate == null) return;

        switch (modes.get(position).getTag())
        {
            case DEFAULT_MODE_TAG   :delegate.didSelectedDefaultMode();break;
            case NORMAL_MODE_TAG    :delegate.didSelectedNormalMode();break;
            case TOUCH_MODE_TAG     :delegate.didSelectedTouchMode();break;
//            case MOUSE_MODE_TAG     :delegate.didSelectedMouseMode();break;
            case INPUT_MODE_TAG     :delegate.didSelectedTextingMode();break;
            case GAME_MODE_TAG      :delegate.didSelectedGameMode();break;
        }
    }

    public void setDelegate(IRCSelectModeDelegate delegate) {
        this.delegate = delegate;
    }
}
