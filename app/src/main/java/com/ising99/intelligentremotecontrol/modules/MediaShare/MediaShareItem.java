package com.ising99.intelligentremotecontrol.modules.MediaShare;

/**
 * Created by shun on 2018/5/2.
 */

public class MediaShareItem {
    private String title;
    private int imageResID;
    private MediaShareType type;

     MediaShareItem(String title, int imageResID, MediaShareType type) {
        this.title = title;
        this.imageResID = imageResID;
        this.type = type;
    }

    public int getImageResID() {
        return imageResID;
    }

    public String getTitle() {
        return title;
    }

    public MediaShareType getType() {
        return type;
    }
}

enum MediaShareType {
    music,
    photo,
    video
}