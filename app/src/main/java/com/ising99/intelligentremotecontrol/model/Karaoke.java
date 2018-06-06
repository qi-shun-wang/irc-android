package com.ising99.intelligentremotecontrol.model;

public class Karaoke {
    private String name;
    private String artist;
    private String type;

    public Karaoke(String name, String artist, String type) {
        this.name = name;
        this.artist = artist;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }
}
