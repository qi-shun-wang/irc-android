package com.ising99.intelligentremotecontrol.modules.More;

import android.util.Log;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.View;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/17 下午 10:04:17.
 * .
 */

public class MorePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<MoreItem> moreItemList = new ArrayList<>();

    MorePresenter() {
        moreItemList.add(new MoreItem(2, R.drawable.more_cast_icon,"媒體分享"));
        moreItemList.add(new MoreItem(1, R.drawable.more_account_icon,"客戶中心"));
        moreItemList.add(new MoreItem(3, R.drawable.more_cloud_icon,"雲端空間"));
        moreItemList.add(new MoreItem(5, R.drawable.more_tune_assistant_icon,"定調助手"));
        moreItemList.add(new MoreItem(4, R.drawable.more_file_manager_icon,"應用管理"));
        moreItemList.add(new MoreItem(0, R.drawable.more_about_icon,"關於"));
        moreItemList.add(new MoreItem(6, R.drawable.more_tips_icon,"操作提示"));
        moreItemList.add(new MoreItem(7, R.drawable.more_setting_icon,"設定"));
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public List<MoreItem> getMoreList() {
        return moreItemList;
    }

    @Override
    public void didSelectedAt(int position) {
        switch (moreItemList.get(position).getId()) {
            case 2: router.presentMediaShare();break;

        }
        Log.i("More","==position==>"+position );
    }
}
