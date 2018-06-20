package com.ising99.intelligentremotecontrol.modules.IRC;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.Game.GameActivity;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCDefaultFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCNormalFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCTextingFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.mode.IRCTouchFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.MediaPanelFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.ModePanelFragment;
import com.ising99.intelligentremotecontrol.modules.IRC.panel.NumberPanelFragment;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private Fragment view ;

    private IRCDefaultFragment default_mode;
    private IRCNormalFragment normal_mode;
    private IRCTextingFragment input_mode;
    private IRCTouchFragment touch_mode;
    private ModePanelFragment modePanel;
    private NumberPanelFragment numPanel;
    private MediaPanelFragment mediaPanel;

    private IRCRouter(Context context) {
        this.context = context;
        default_mode = new IRCDefaultFragment();
        normal_mode = new IRCNormalFragment();
        input_mode = new IRCTextingFragment();
        touch_mode = new IRCTouchFragment();
        modePanel = new ModePanelFragment();
        numPanel = new NumberPanelFragment();
        mediaPanel = new MediaPanelFragment();

    }

    public static IRCFragment setupModule(Context context, RemoteControlCoAPService service){

        IRCFragment view = new IRCFragment();
        IRCInteractor interactor = new IRCInteractor(context, service);
        IRCPresenter presenter = new IRCPresenter();
        IRCRouter router = new IRCRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        router.default_mode.setDelegate(presenter);
        router.normal_mode.setDelegate(presenter);
        router.input_mode.setDelegate(presenter);
        router.touch_mode.setDelegate(presenter);
        router.numPanel.setDelegate(presenter);
        router.mediaPanel.setDelegate(presenter);
        router.modePanel.setDelegate(presenter);

        return view;
    }

    @Override
    public void composingModes() {
        view.getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_mode_content_container,default_mode)
                .add(R.id.fragment_num_content_container,numPanel)
                .add(R.id.fragment_media_content_container,mediaPanel)
                .add(R.id.fragment_mode_list_content_container,modePanel)
                .hide(numPanel)
                .hide(mediaPanel)
                .hide(modePanel)
                .commit();
    }

    @Override
    public void decomposingModes() {
        try {
            view.getFragmentManager()
                    .beginTransaction()
                    .remove(default_mode)
                    .remove(numPanel)
                    .remove(mediaPanel)
                    .remove(modePanel)
                    .commit();
        }
        catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Override
    public void presentNumPanel() {
        view.getFragmentManager().beginTransaction().show(numPanel).commit();
    }

    @Override
    public void presentMediaPanel() {
        view.getFragmentManager().beginTransaction().show(mediaPanel).commit();
    }

    @Override
    public void presentModePanel() {
        view.getFragmentManager().beginTransaction().show(modePanel).commit();
    }

    @Override
    public void presentTextingMode() {
        view.getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,input_mode).commit();
    }

    @Override
    public void presentTouchMode() {
        view.getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,touch_mode).commit();
    }

    @Override
    public void presentNormalMode() {
        view.getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,normal_mode).commit();
    }

    @Override
    public void presentDefaultMode() {
        view.getFragmentManager().beginTransaction().replace(R.id.fragment_mode_content_container,default_mode).commit();
    }

    @Override
    public void presentGameMode() {
        Intent i = new Intent(context, GameActivity.class);

        i.putExtra("ADDRESS",presenter.getAddress());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void dismissNumPanel() {
        view.getFragmentManager().beginTransaction().hide(numPanel).commit();
    }

    @Override
    public void dismissMediaPanel() {
        view.getFragmentManager().beginTransaction().hide(mediaPanel).commit();
    }

    @Override
    public void dismissModePanel() {
        view.getFragmentManager().beginTransaction().hide(modePanel).commit();
    }

    @Override
    public void openDeviceDiscovery() {
        Intent intentToDeviceDiscoveryActivity = new Intent(context, DeviceDiscoveryActivity.class);
        intentToDeviceDiscoveryActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentToDeviceDiscoveryActivity);
    }
}

