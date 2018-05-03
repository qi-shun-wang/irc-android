package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;

import com.ising99.intelligentremotecontrol.core.DLNA.ContentNode;
import com.ising99.intelligentremotecontrol.core.DLNA.ContentTree;
import com.ising99.intelligentremotecontrol.core.DLNA.MediaServer;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.InteractorOutput;

import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.container.Container;
import org.fourthline.cling.support.model.item.ImageItem;
import org.seamless.util.MimeType;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/3 上午 10:22:42.
 * .
 */

public class MediaSharePhotoGroupListInteractor implements MediaSharePhotoGroupListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private HashMap<String, List<Photo>> photoGroup = new HashMap<>();

    MediaSharePhotoGroupListInteractor(Context context) {
        this.context = context;
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
    private MediaServer mediaServer;

    @Override
    public void preparePhotoAssets() {

        ContentNode rootNode = ContentTree.getRootNode();
        Container imageContainer = new Container(ContentTree.IMAGE_ID,
                ContentTree.ROOT_ID, "Images", "GNaP MediaServer",
                new DIDLObject.Class("object.container"), 0);
        imageContainer.setRestricted(true);
        imageContainer.setWriteStatus(WriteStatus.NOT_WRITABLE);
        rootNode.getContainer().addContainer(imageContainer);
        rootNode.getContainer().setChildCount(rootNode.getContainer().getChildCount() + 1);

        ContentTree.addNode(ContentTree.IMAGE_ID, new ContentNode(ContentTree.IMAGE_ID, imageContainer));

        String[] imageColumns =
                {
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.TITLE,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.MIME_TYPE,
                        MediaStore.Images.Media.SIZE
                };

        Cursor imgCursor = context
                .getContentResolver()
                .query
                        (
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                imageColumns,
                                null,
                                null,
                                ""
                        );

        if (imgCursor.moveToFirst()) {
            do {
                String id = ContentTree.IMAGE_PREFIX
                        + imgCursor.getInt(imgCursor
                        .getColumnIndex(MediaStore.Images.Media._ID));
                String title = imgCursor.getString(imgCursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                String creator = "Shun";
                String filePath = imgCursor.getString(imgCursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String mimeType = imgCursor.getString(imgCursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                long size = imgCursor.getLong(imgCursor
                        .getColumnIndexOrThrow

                                (MediaStore.Images.Media.SIZE));

                Res res = new Res(new MimeType(mimeType.substring(0,
                        mimeType.indexOf('/') + 1), mimeType
                        .substring(mimeType.indexOf('/') + 1)), size,
                        "http://" + "localhost" + "/" + id);//mediaServer.getAddress()

                ImageItem imageItem = new ImageItem(id,
                        ContentTree.IMAGE_ID, title, creator, res);
                imageContainer.addItem(imageItem);
                imageContainer.setChildCount(imageContainer
                        .getChildCount() + 1);
                ContentTree.addNode(id, new ContentNode(id, imageItem,
                        filePath));
                // 获取该图片的父路径名
                String parentName = new File(filePath).getParentFile()
                        .getName();

                // 根据父路径名将图片放入到mGruopMap中
                if (!photoGroup.containsKey(parentName)) {

                    List<Photo> photos = new ArrayList<>();
                    Photo photo = new Photo(imageItem.getId(), filePath, imageItem.getParentID(), imageItem.isRestricted(), imageItem.getFirstResource().getProtocolInfo().toString(), res.getSize(), imageItem.getTitle(), res.getValue());
                    photos.add(photo);
                    photoGroup.put(parentName,photos);

                } else {
                    Photo photo = new Photo(imageItem.getId(), filePath, imageItem.getParentID(), imageItem.isRestricted(), imageItem.getFirstResource().getProtocolInfo().toString(), res.getSize(), imageItem.getTitle(), res.getValue());
                    photoGroup.get(parentName).add(photo);
                }

            } while (imgCursor.moveToNext());

            imgCursor.close();
            output.didPreparedPhotoAssets(photoGroup);

        }
    }

    private InetAddress getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return InetAddress.getByName(String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
    }
}

