package com.example.mymusic;

public interface MediaListener{
    void onProgress(int time);
    void finish(boolean bol);
}
