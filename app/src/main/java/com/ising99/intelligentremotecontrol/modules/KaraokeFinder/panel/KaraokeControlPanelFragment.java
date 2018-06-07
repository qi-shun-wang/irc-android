package com.ising99.intelligentremotecontrol.modules.KaraokeFinder.panel;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;

public class KaraokeControlPanelFragment extends Fragment {

    public void setDelegate(Delegate delegate) {
        this.delegate = delegate;
    }

    public interface Delegate {
        void didSelected();
    }

    private int top;
    private int height;

    private View root;
    private Delegate delegate;

    public KaraokeControlPanelFragment() {
        // Required empty public constructor
        Log.d("KaraokeControlPanel","init");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_karaoke_control_panel, container, false);
        Log.d("KaraokeControlPanel","onCreateView");
        root.findViewById(R.id.karaoke_control_panel_box).setY(top);
        root.findViewById(R.id.karaoke_control_panel_box).getLayoutParams().height = height;
        root.findViewById(R.id.karaoke_control_panel_add_btn).setOnClickListener((v)->{if (delegate != null)delegate.didSelected();});
        root.findViewById(R.id.karaoke_control_panel_insert_btn).setOnClickListener((v)->{if (delegate != null)delegate.didSelected();});
        root.findViewById(R.id.karaoke_control_panel_collect_btn).setOnClickListener((v)->{if (delegate != null)delegate.didSelected();});
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("KaraokeControlPanel","onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("KaraokeControlPanel","onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("KaraokeControlPanel","onDestroyView");
    }

    public void setTop(int top) {
        this.top = top;
        Log.d("KaraokeControlPanel","setTop");
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
