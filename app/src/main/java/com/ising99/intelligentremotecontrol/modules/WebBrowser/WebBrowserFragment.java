package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Presenter;

/**
 * Created by Shun on 2018/4/13 下午 05:15:24.
 * .
 */

public class WebBrowserFragment extends Fragment implements WebBrowserContracts.View {

    private Presenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_browser, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new WebBrowserPresenter(getActivity().getApplicationContext(), this);
        presenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO Setup OnFragmentInteractionListener

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
        Runtime.getRuntime().gc();
    }
}
