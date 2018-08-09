package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.InteractorOutput;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 04:25:19.
 * .
 */

public class MediaShareMusicGroupListInteractor implements MediaShareMusicGroupListContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private HashMap<String, List<Music>> musicGroup ;
    private List<Music> musicList;
    private DLNAMediaManagerProtocol manager;

    MediaShareMusicGroupListInteractor(Context context, DLNAMediaManagerProtocol manager) {
        this.context = context;
        this.musicGroup = new HashMap<>();
        this.musicList = new ArrayList<>();
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
    public void performRemoteStop() {
        manager.stop((invocation, operation, defaultMsg) -> output.didStopRemoteAssetFailure());
    }

    @Override
    public void prepareMusicAssets() {
        musicGroup.clear();
        musicList.clear();
        Cursor audioCursor = context
                .getContentResolver()
                .query
                        (
                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                null,
                                null,
                                null,
                                null
                        );

        if (audioCursor != null && audioCursor.moveToFirst()) {

            do {
                String id = "music-" + audioCursor.getInt(audioCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String title = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String artist = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String filePath = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                String mimeType = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE));
                long size = audioCursor.getLong(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                long duration = audioCursor.getLong(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                String album = audioCursor.getString(audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                String directoryName = new File(filePath).getParentFile().getName();

                Music music = new Music
                        (
                                id,
                                filePath,
                                album,
                                false,
                                mimeType,
                                size,
                                title,
                                id,
                                duration,
                                artist);
                if (musicGroup.containsKey(directoryName)) {
                    musicGroup.get(directoryName).add(music);
                }else{
                    musicGroup.put(directoryName,new ArrayList<>());
                    musicGroup.get(directoryName).add(music);
                }
                musicList.add(music);


            } while (audioCursor.moveToNext());

            audioCursor.close();
            musicGroup.put("全部",musicList);
            output.didPreparedMusicAssets(musicGroup);
        }

    }
}

