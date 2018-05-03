package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

/**
 * Created by shun on 2018/5/3.
 */

public class PhotoCatalog {

    private String thumbnailPath;
    private String name;
    private int amount;


    public PhotoCatalog(String thumbnailPath, String name, int amount) {
        this.thumbnailPath = thumbnailPath;
        this.name = name;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }
}
