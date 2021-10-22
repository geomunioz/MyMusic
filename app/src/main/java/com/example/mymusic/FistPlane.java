package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FistPlane extends AppCompatActivity implements MusicObserver{
    TextView nameSong;
    TextView nameArtist;
    TextView timeActual;
    TextView timeFinal;

    private double starTime = 0;
    private double finalTime = 0;

    MaterialButton btn_control;
    private ListSong listSong;

    private List<Song> myMusic;
    private Song actual;

    //MediaPlayer mediaPlayer = new MediaPlayer();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist_plane);

        myMusic = new ArrayList<>();

        btn_control = (MaterialButton) findViewById(R.id.control);
        btn_control.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);

        listSong = ListSong.getInstance();
        listSong.init(this);

        myMusic.addAll(listSong.getListPlay());
        actual = new Song(listSong.getSong().getUri(),listSong.getSong().getTitle(),listSong.getSong().getArtist(),listSong.getSong().getAlbum(),listSong.getSong().getDuration());


        timeActual = findViewById(R.id.textActualTime);
        timeFinal = findViewById(R.id.textFinalTime);

        nameSong = findViewById(R.id.textSongName);
        nameSong.setText(actual.getTitle());
        nameArtist = findViewById(R.id.textArtistName);
        nameArtist.setText(actual.getArtist());

        //mediaPlayer = listSong.getMplayer();
        /*finalTime = mediaPlayer.getDuration();
        timeFinal.setText((String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))));*/

        SongChange(actual);
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        actual = listSong.getSong();
        nameSong.setText(actual.getTitle());
        nameArtist.setText(actual.getArtist());
    }

    @Override
    public void ActualSongChange(Song song) {
        nameSong.setText(song.getTitle());
        nameArtist.setText(song.getArtist());
       /* finalTime = mediaPlayer.getDuration();
        timeFinal.setText((String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))));*/
        SongChange(song);
    }

    @Override
    public void HistorialListChange(List<Song> historial) {

    }

    @Override
    public void ActualCategory(String category) {

    }

    @Override
    public void ActualMediaPlayer(MediaPlayer player) {
        /*mediaPlayer = player;*/
    }

    @Override
    public void ActualListPlayer(List<Song> musicPlayer) {
        myMusic = new ArrayList<>();
        myMusic.addAll(musicPlayer);
    }

    public void SongChange(Song song){
        nameSong.setText(song.getTitle());
        nameArtist.setText(song.getArtist());
    }

    public void play(View view) {
        /*if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btn_control.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }else{
            mediaPlayer.start();
            btn_control.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }*/
    }

    public void nextSong(View view) {
        int x = myMusic.indexOf(listSong.getSong());

        if(x == myMusic.size()-1){
            listSong.setSong(myMusic.get(0));
        }else{
            listSong.setSong(myMusic.get(x+1));
        }
    }

    public void previousSong(View view) {
        int x = myMusic.indexOf(listSong.getSong());
        int y = myMusic.size() - 1;
        if(x == 0){
            listSong.setSong(myMusic.get(y));
        }else{
            listSong.setSong(myMusic.get(x-1));
        }
    }

    public void repeat(View view) {
        /*if(mediaPlayer.isLooping()){
            mediaPlayer.setLooping(false);
            //listSong.setMplayer(mediaPlayer);
            Toast.makeText(getApplicationContext(),"No Repetición", Toast.LENGTH_SHORT).show();
        }else {
            mediaPlayer.setLooping(true);
            Toast.makeText(getApplicationContext(),"Repetición", Toast.LENGTH_SHORT).show();
            //listSong.setMplayer(mediaPlayer);
        }*/
    }

    public void random(View view) {

    }

    public void back(View view) {
        onBackPressed();
    }
}