package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerCallback;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.InteractorOutput;

import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private List<Photo> selectedPhotos;

    MediaSharePhotoListInteractor(Context context,List<Photo> collection ,DLNAMediaManagerProtocol manager) {
        this.context = context;
        this.collection = collection;
        this.manager = (DLNAMediaManager) manager;
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
    public void setupCurrentDevice(Device device) {
        manager.setCurrentDevice(device);
    }

    @Override
    public void setupSelectedPhotos(List<Photo> assets) {
        this.selectedPhotos = assets;
    }

    @Override
    public void setupCurrentRemoteAsset(int index) {
        try {
            String path = URLEncoder.encode( selectedPhotos.get(index).getFilePath(), "UTF-8");
            manager.setAVTransportURI("/image" + path, new DLNAMediaManagerCallback.Common() {
                @Override
                public void success(ActionInvocation invocation) {
                    output.didSetRemoteAssetSuccess();
                }

                @Override
                public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                    output.didSetRemoteAssetFailure();
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
                output.didPlayRemoteAssetSuccess();
            }

            @Override
            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                output.didPlayRemoteAssetFailure();
            }
        });
    }

    @Override
    public void performRemoteStop() {
        manager.stop((invocation, operation, defaultMsg) -> output.didStopRemoteAssetFailure());
    }
}

