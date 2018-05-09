package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.InteractorOutput;

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
    private HashMap<String, List<Music>> musicGroup = new HashMap<>();
    private List<Music> musicList = new ArrayList<>();

    MediaShareMusicGroupListInteractor(Context context) {
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
    public void prepareMusicAssets() {
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

                //                if (artist != null && artist.contains("?")|| artist.equals("<unknown>")) {
                //                    artist = getString(R.string.unknown_artist);
                //                }
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
                musicList.add(music);


            } while (audioCursor.moveToNext());

            audioCursor.close();
            musicGroup.put("Total",musicList);
            output.didPreparedMusicAssets(musicGroup);
        }

    }
}

