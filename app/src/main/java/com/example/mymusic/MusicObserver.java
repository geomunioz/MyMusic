package com.example.mymusic;

import android.media.MediaPlayer;

import java.util.List;

public interface MusicObserver {
    void OnPlayListChange(List<Song> music);
    void ActualSongChange(Song song);
    void HistorialListChange(List<Song> historial);
    void ActualCategory(String category);
    void ActualMediaPlayer(MediaPlayer player);
    void ActualListPlayer(List<Song> musicPlayer);
}
