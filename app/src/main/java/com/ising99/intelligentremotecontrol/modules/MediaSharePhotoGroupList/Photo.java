package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shun on 2018/5/3.
 */

public class Photo  implements Parcelable {

    private String id;
    private String filePath;
    private String dir_id;
    private Boolean isRestricted;
    private String info;
    private long size;
    private String title;
    private String uri;

    public Photo(
            String id,
            String filePath,
            String dir_id,
            Boolean isRestricted,
            String info,
            long size,
            String title,
            String url
    ) {
        this.id = id;
        this.title = title;
        this.filePath = filePath;
        this.dir_id = dir_id;
        this.isRestricted = isRestricted;
        this.info = info;
        this.size = size;
        this.uri = url;
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
                        this.dir_id,
                        String.valueOf(this.isRestricted),
                        this.info,
                        String.valueOf(this.size),
                        this.title,
                        this.uri,
                });
    }

    public Photo(Parcel in){
        String[] data = new String[8];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.id = data[0];
        this.filePath = data[1];
        this.dir_id = data[2];
        this.isRestricted = Boolean.valueOf(data[3]);
        this.info = data[4];
        this.size = Long.valueOf(data[5]);
        this.title = data[6];
        this.uri = data[7];

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getFilePath() {
        return filePath;
    }
}
