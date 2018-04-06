package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by shun on 2018/3/29.
 *
 */

public class ImageItem implements Serializable {
    private transient Bitmap image;
//    private transient Bitmap finger;
//    private transient Bitmap ring;
    private String title;

    ImageItem(Bitmap image,
//              Bitmap finger,
//              Bitmap ring,
              String title) {
        super();
        this.image = image;
//        this.ring = ring;
//        this.finger = finger;
        this.title = title;
    }

    Bitmap getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Bitmap getFinger() {
//        return finger;
//    }
//
//    public Bitmap getRing() {
//        return ring;
//    }
}
