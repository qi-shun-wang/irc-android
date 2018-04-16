package com.ising99.intelligentremotecontrol.modules.IRC.panel;

/**
 * Created by shun on 2018/4/15.
 *
 */

class ModeItem {

    private int tag;
    private int imageResId;
    private String title;

    ModeItem(int tag, int imageResId, String title) {
        this.tag = tag;
        this.imageResId = imageResId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    int getImageResId() {
        return imageResId;
    }

    int getTag() {
        return tag;
    }
}
