package com.ising99.intelligentremotecontrol.modules.More;

/**
 * Created by shun on 2018/4/29.
 */

public class MoreItem {

    private int id;
    private int imageResId;
    private String title;

    MoreItem(int id, int imageResId, String title) {
        this.id = id;
        this.imageResId = imageResId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    int getImageResId() {
        return imageResId;
    }

    int getId() {
        return id;
    }
}
