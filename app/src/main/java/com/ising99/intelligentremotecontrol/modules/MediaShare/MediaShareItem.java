package com.ising99.intelligentremotecontrol.modules.MediaShare;

/**
 * Created by shun on 2018/5/2.
 */

public class MediaShareItem {
    private String title;
    private int imageResID;

    public MediaShareItem(String title, int imageResID) {
        this.title = title;
        this.imageResID = imageResID;
    }

    public int getImageResID() {
        return imageResID;
    }

    public String getTitle() {
        return title;
    }
}
