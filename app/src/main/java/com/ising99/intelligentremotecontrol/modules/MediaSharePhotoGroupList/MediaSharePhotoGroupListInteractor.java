package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.InteractorOutput;

import java.io.File;
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
    private List<Photo> photos = new ArrayList<>();

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

    @Override
    public void preparePhotoAssets() {
        photoGroup.clear();
        photos.clear();
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

        if (imgCursor != null && imgCursor.moveToFirst()) {
            do {

                String id = "image-" + imgCursor.getInt(imgCursor.getColumnIndex(MediaStore.Images.Media._ID));
                String title = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                String filePath = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                String mimeType = imgCursor.getString(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                long size = imgCursor.getLong(imgCursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                String directoryName = new File(filePath).getParentFile().getName();

                Photo photo = new Photo
                        (
                                id,
                                filePath,
                                "0",
                                false,
                                mimeType,
                                size,
                                title,
                                id
                        );
                if (photoGroup.containsKey(directoryName)) {
                    photoGroup.get(directoryName).add(photo);
                }else{
                    photoGroup.put(directoryName,new ArrayList<>());
                    photoGroup.get(directoryName).add(photo);
                }
                photos.add(photo);

            } while (imgCursor.moveToNext());

            imgCursor.close();
            photoGroup.put("全部",photos);
            output.didPreparedPhotoAssets(photoGroup);

        }
    }

}

