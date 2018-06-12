package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.panel;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;

public class KaraokeBookmarkAdditionPanel extends Fragment {

    private String name = "";

    public KaraokeBookmarkAdditionPanel() {
        // Required empty public constructor
    }

    private KaraokeBookmarkAdditionPanelDelegate delegate ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_karaoke_bookmark_addition_panel, container, false);
        view.findViewById(R.id.karaoke_bookmark_cancel_btn).setOnClickListener(view1 -> {if(delegate!=null)delegate.didCancel();});
        view.findViewById(R.id.karaoke_bookmark_commit_btn).setOnClickListener(view1 -> {
            if(delegate != null) {
                if (name.length() == 0)
                {
                    view.findViewById(R.id.karaoke_bookmark_edit_text_hint).setVisibility(View.VISIBLE);
                }
                else
                {
                    delegate.didCommit(name);
                }
            }
        });

        ((EditText)view.findViewById(R.id.karaoke_bookmark_edit_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Text","="+charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Text","=="+charSequence);
                name = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Text","==="+editable.toString());

            }
        });
        return view;
    }

    public void setDelegate(KaraokeBookmarkAdditionPanelDelegate delegate) {
        this.delegate = delegate;
    }

}
