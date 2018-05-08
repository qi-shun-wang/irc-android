package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

/**
 * Created by shun on 2018/5/8.
 * .
 */

public class PhotoItem {

    private Photo photo;
    private boolean isSelected;

    public PhotoItem(Photo photo, boolean isSelected) {
        this.photo = photo;
        this.isSelected = isSelected;
    }

    public Photo getPhoto() {
        return photo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    void setSelected(boolean selected) {
        isSelected = selected;
    }
}
