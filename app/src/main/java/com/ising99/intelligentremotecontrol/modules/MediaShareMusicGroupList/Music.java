package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by shun on 2018/5/8.
 * .
 */

public class Music  implements Parcelable {

    private String id;
    private String filePath;
    private String album;
    private Boolean isRestricted;
    private String info;
    private long size;
    private String title;
    private String uri;
    private long duration;
    private String artist;


    public Music(
            String id,
            String filePath,
            String dir_id,
            Boolean isRestricted,
            String info,
            long size,
            String title,
            String url,
            long duration, String artist) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.album = dir_id;
        this.isRestricted = isRestricted;
        this.info = info;
        this.size = size;
        this.uri = url;
        this.duration = duration;
        this.artist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]
                {
                        this.id,
                        this.filePath,
                        this.album,
                        String.valueOf(this.isRestricted),
                        this.info,
                        String.valueOf(this.size),
                        this.title,
                        this.uri,
                        String.valueOf(this.duration),
                        this.artist
                });
    }

    public Music(Parcel in){
        String[] data = new String[10];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0];
        this.filePath = data[1];
        this.album = data[2];
        this.isRestricted = Boolean.valueOf(data[3]);
        this.info = data[4];
        this.size = Long.valueOf(data[5]);
        this.title = data[6];
        this.uri = data[7];
        this.duration = Long.valueOf(data[8]);
        this.artist = data[9];

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Music createFromParcel(Parcel in) {
            return new Music(in);
        }

        public Music[] newArray(int size) {
            return new Music[size];
        }
    };

    public String getFilePath() {
        return filePath;
    }

    public long getDuration() {
        return duration;
    }

    public String getArtist() {
        return artist;
    }
}
