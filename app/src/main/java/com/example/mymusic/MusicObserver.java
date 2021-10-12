package com.example.mymusic;

import java.util.List;

public interface MusicObserver {
    void OnPlayListChange(List<Song> music);
    void ActualSongChange(Song song);
}
