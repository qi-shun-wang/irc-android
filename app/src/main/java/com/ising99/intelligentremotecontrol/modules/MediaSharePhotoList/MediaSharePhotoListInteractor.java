package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.ising99.intelligentremotecontrol.core.DLNA.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.InteractorOutput;

import org.fourthline.cling.model.meta.Device;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListInteractor implements MediaSharePhotoListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Photo> collection;
    private DLNAMediaManager manager;

    MediaSharePhotoListInteractor(Context context,List<Photo> collection ,DLNAMediaManager manager) {
        this.context = context;
        this.collection = collection;
        this.manager = manager;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public List<Photo> getPhotos() {
        return collection;
    }

    @Override
    public void performCast(Device device) {
        manager.play(device,"/storage/emulated/0/Pictures/3162F696-D1B3-4509-82F7-F98C701A25C6.jpg","");
    }
}

