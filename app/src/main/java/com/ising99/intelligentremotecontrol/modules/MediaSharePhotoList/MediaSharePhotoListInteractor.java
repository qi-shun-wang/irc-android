package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListInteractor implements MediaSharePhotoListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private List<Photo> collection;
    private DLNAMediaManager manager;
    private Timer worker;
    private int cursor = 0;

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
        worker.cancel();
        output = null;
        context = null;
    }

    @Override
    public List<Photo> getPhotos() {
        return collection;
    }

    @Override
    public void setupCurrentDevice(Device device) {
        manager.setCurrentDevice(device);

    }

    @Override
    public void setupCurrentRemoteAsset() {
        try {
            String path = URLEncoder.encode( collection.get(cursor).getFilePath(), "UTF-8");
            manager.setAVTransportURI("/image" + path, new DLNAMediaManagerCallback.Common() {
                @Override
                public void success(ActionInvocation invocation) {

                }

                @Override
                public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {

                }
            });
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }

    @Override
    public void performRemotePlay() {
        manager.play(new DLNAMediaManagerCallback.Common() {
            @Override
            public void success(ActionInvocation invocation) {

            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {

            }
        });
    }

    @Override
    public void performRemoteStop() {
        manager.stop((invocation, operation, defaultMsg) -> {

        });
    }

    //TODO move worker into presenter
//    @Override
//    public void performCast(List<Photo> assets) {
//        worker = new Timer();
//        cursor = 0;
//
//
//        worker.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (cursor >= assets.size()) return;
//                try {
//                    String path = URLEncoder.encode( assets.get(cursor).getFilePath(), "UTF-8");
//                    manager.setAVTransportURI("/image" + path, new DLNAMediaManagerCallback.Common() {
//                        @Override
//                        public void success(ActionInvocation invocation) {
//
//                        }
//
//                        @Override
//                        public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
//
//                        }
//                    });
//                } catch (UnsupportedEncodingException e){
//                    e.printStackTrace();
//                }
//                cursor ++;
//            }
//        },1000,5000);
//    }

}

