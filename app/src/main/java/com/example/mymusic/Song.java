package com.example.mymusic;

import android.net.Uri;

public class Song {
    private long uri;//Uri uri;
    private String title;
    private String artist;
    private String album;
    private int duration;
    //private int size;

    public Song(long uri, String title, String artist, String album, int duration) {
        this.uri = uri;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
        //this.size = size;
    }

    public String getTitle(){
        return title;
    }

    public int getDuration(){
        return duration;
    }

}
