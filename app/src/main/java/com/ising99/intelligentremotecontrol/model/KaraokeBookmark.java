package com.ising99.intelligentremotecontrol.model;

public class KaraokeBookmark {
    private String name;
    private boolean isSelected = false;
    private boolean isEmpty = false;

    public KaraokeBookmark(String name) {
        this.name = name;

    }

    public KaraokeBookmark(String name, boolean isSelected) {
        this(name);
        this.isSelected = isSelected;
    }

    public KaraokeBookmark(boolean isEmpty){
        this("");
        this.isEmpty = isEmpty;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
