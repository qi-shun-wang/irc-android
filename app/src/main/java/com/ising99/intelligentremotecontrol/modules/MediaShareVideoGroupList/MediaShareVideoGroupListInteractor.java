package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.InteractorOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:05:36.
 * .
 */

public class MediaShareVideoGroupListInteractor implements MediaShareVideoGroupListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private HashMap<String, List<Video>> videoGroup = new HashMap<>();
    private List<Video> videoList = new ArrayList<>();

    MediaShareVideoGroupListInteractor(Context context) {
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
    public void prepareVideoAssets() {
        videoGroup.clear();
        videoList.clear();
        Cursor videoCursor = context
                .getContentResolver()
                .query
                        (
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );

        if (videoCursor != null && videoCursor.moveToFirst()) {
            do {

                String id = "video-" + videoCursor.getInt(videoCursor.getColumnIndex(MediaStore.Video.Media._ID));
                String title = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                String artist = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                String filePath = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                String mimeType = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
                long size = videoCursor.getLong(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                long duration = videoCursor.getLong(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                String resolution = videoCursor.getString(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                String directoryName = new File(filePath).getParentFile().getName();
                Video video = new Video
                        (
                                id,
                                filePath,
                                resolution,
                                false,
                                mimeType,
                                size,
                                title,
                                id,
                                duration,
                                artist,
                                directoryName);
                if (videoGroup.containsKey(directoryName)) {
                    videoGroup.get(directoryName).add(video);
                }else{
                    videoGroup.put(directoryName,new ArrayList<>());
                    videoGroup.get(directoryName).add(video);
                }
                videoList.add(video);

            } while (videoCursor.moveToNext());

            videoCursor.close();
            videoGroup.put("全部",videoList);
            output.didPreparedVideoAssets(videoGroup);
        }

    }
}

