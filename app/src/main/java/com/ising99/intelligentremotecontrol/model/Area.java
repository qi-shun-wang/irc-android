package com.ising99.intelligentremotecontrol.model;

public class Area {
    private String name;
    private boolean isSelected = false;

    public Area(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getName() {
        return name;
    }
}
