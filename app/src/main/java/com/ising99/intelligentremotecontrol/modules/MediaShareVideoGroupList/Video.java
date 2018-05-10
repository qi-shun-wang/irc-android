package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shun on 2018/5/9.
 * .
 */

public class Video implements Parcelable {

    private String id;
    private String filePath;
    private String resolution;
    private Boolean isRestricted;
    private String info;
    private long size;
    private String title;
    private String uri;
    private long duration;
    private String artist;
    private String directoryName;


    public Video(
            String id,
            String filePath,
            String resolution,
            Boolean isRestricted,
            String info,
            long size,
            String title,
            String url,
            long duration, String artist, String directoryName) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.resolution = resolution;
        this.isRestricted = isRestricted;
        this.info = info;
        this.size = size;
        this.uri = url;
        this.duration = duration;
        this.artist = artist;
        this.directoryName = directoryName;
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
                        this.resolution,
                        String.valueOf(this.isRestricted),
                        this.info,
                        String.valueOf(this.size),
                        this.title,
                        this.uri,
                        String.valueOf(this.duration),
                        this.artist,
                        this.directoryName
                });
    }

    public Video(Parcel in){
        String[] data = new String[11];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0];
        this.filePath = data[1];
        this.resolution = data[2];
        this.isRestricted = Boolean.valueOf(data[3]);
        this.info = data[4];
        this.size = Long.valueOf(data[5]);
        this.title = data[6];
        this.uri = data[7];
        this.duration = Long.valueOf(data[8]);
        this.artist = data[9];
        this.directoryName = data[10];

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
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

    public String getTitle() {
        return title;
    }

    public String getDirectoryName() {
        return directoryName;
    }
}
