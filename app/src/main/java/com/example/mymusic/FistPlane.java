package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FistPlane extends AppCompatActivity implements MusicObserver, MediaListener{
    TextView nameSong;
    TextView nameArtist;
    TextView timeActual;
    TextView timeFinal;
    MaterialButton repeat;
    MaterialButton random;
    ProgressBar bar;

    private double starTime = 0;
    private double finalTime = 0;

    MaterialButton btn_control;
    private ListSong listSong;

    private List<Song> myMusic;
    private Song actual;
    MediaPlayerManager manager;
    //MediaPlayer mediaPlayer = new MediaPlayer();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist_plane);

        myMusic = new ArrayList<>();

        btn_control = (MaterialButton) findViewById(R.id.control);
        bar = (ProgressBar) findViewById(R.id.progressBar);
        repeat = (MaterialButton) findViewById(R.id.repeat);
        random = (MaterialButton) findViewById(R.id.random);

        listSong = ListSong.getInstance();
        listSong.init(this);
        manager = listSong.getManager();
        manager.addListener(this);

        myMusic.addAll(listSong.getListPlay());
        actual = new Song(listSong.getSong().getUri(),listSong.getSong().getTitle(),listSong.getSong().getArtist(),listSong.getSong().getAlbum(),listSong.getSong().getDuration());

        if(manager.isActivo()){
            btn_control.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }else {
            btn_control.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }

        if(manager.getMedia().isLooping()){
            repeat.setIconTintResource(R.color.green);
        }

        if(manager.getRandom()){
            random.setIconTintResource(R.color.green);
        }

        timeActual = findViewById(R.id.textActualTime);
        timeFinal = findViewById(R.id.textFinalTime);

        nameSong = findViewById(R.id.textSongName);
        nameSong.setText(actual.getTitle());
        nameArtist = findViewById(R.id.textArtistName);
        nameArtist.setText(actual.getArtist());

        //mediaPlayer = listSong.getMplayer();

        finalTime = manager.getMedia().getDuration();
        timeFinal.setText((String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))));

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
       finalTime = manager.getMedia().getDuration();
        timeFinal.setText((String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                finalTime)))));
        bar.setProgress(0);
        //SongChange(song);
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
        if(manager.isActivo()){
            manager.Pause();
            btn_control.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }else{
            manager.Play();
            btn_control.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }
    }

    public void nextSong(View view) {
        int x = myMusic.indexOf(listSong.getSong());
        if(manager.getRandom()){
            int aleatorio = (int) (Math.random()*myMusic.size());
            listSong.setSong(myMusic.get(aleatorio));
        }else{
            if(x == myMusic.size()-1){
                listSong.setSong(myMusic.get(0));
            }else{
                listSong.setSong(myMusic.get(x+1));
            }
        }

    }

    public void previousSong(View view) {
        int x = myMusic.indexOf(listSong.getSong());
        int y = myMusic.size() - 1;

        if(manager.getRandom()){
            int aleatorio = (int) (Math.random()*myMusic.size());
            listSong.setSong(myMusic.get(aleatorio));
        }else{
            if(x == 0){
                listSong.setSong(myMusic.get(y));
            }else{
                listSong.setSong(myMusic.get(x-1));
            }
        }
    }

    public void repeat(View view) {
        if(manager.getMedia().isLooping()){
            manager.getMedia().setLooping(false);
            //listSong.setMplayer(mediaPlayer);
            repeat.setIconTintResource(R.color.white);
            Toast.makeText(getApplicationContext(),"No Repetición", Toast.LENGTH_SHORT).show();
        }else {
            manager.getMedia().setLooping(true);
            Toast.makeText(getApplicationContext(),"Repetición", Toast.LENGTH_SHORT).show();
            repeat.setIconTintResource(R.color.green);
            //listSong.setMplayer(mediaPlayer);
        }
    }

    public void random(View view) {
        if(manager.getRandom()){
            manager.setRandom(false);
            random.setIconTintResource(R.color.white);
        }else{
            random.setIconTintResource(R.color.green);
            manager.setRandom(true);
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onProgress(int time) {
        int total = manager.getMedia().getDuration();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                starTime = time;
                timeActual.setText((String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes((long) starTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) starTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        starTime)))));

                long pos = (long) (100L * time/ total);
                bar.setProgress((int) pos);
            }
        });

    }

    @Override
    public void finish(boolean bol) {
        int x = myMusic.indexOf(listSong.getSong());
        int y = myMusic.size() - 1;
        if(x == 0){
            listSong.setSong(myMusic.get(y));
        }else{
            listSong.setSong(myMusic.get(x-1));
        }
    }
}