package com.example.mymusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MusicObserver, MediaListener{
    BottomNavigationView bottomNavigation;
    MaterialCardView playerMin;
    Button btn_play;
    MediaPlayerManager manager;
    //MediaPlayer mediaPlayer = new MediaPlayer();

    private ListSong listSong;

    private List<Song> myMusic;
    private Song cancion;
    boolean state;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();

        playerMin = (MaterialCardView) findViewById(R.id.playerMin);
        myMusic = new ArrayList<>();

        btn_play = (Button)findViewById(R.id.playMin);


        listSong = ListSong.getInstance();
        listSong.init(this);
        //listSong.setContext(this);
        manager = listSong.getManager();
        manager.addListener(this);
        manager.setContext(this);
        //mediaPlayer = listSong.getMplayer();
        listSong.setMusica(DataManager.getData(this));

        myMusic.addAll(listSong.getListPlay());

        state = false;

        if(manager.isActivo()){
            btn_play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }else {
            btn_play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(manager.isActivo()){
            btn_play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }else {
            btn_play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }

    private void setUpNavigation() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_container);
        NavigationUI.setupWithNavController(bottomNavigation, navHostFragment.getNavController());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    public void playerPlane(View view){
        Intent i = new Intent(this, FistPlane.class);
        startActivity(i);
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        myMusic.clear();
        myMusic.addAll(music);
        Log.i("Musica", "OnPlayListChange: "+myMusic.size());
    }

    @Override
    public void ActualSongChange(Song song) {
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
        //mediaPlayer = player;
    }

    @Override
    public void ActualListPlayer(List<Song> musicPlayer) {
        myMusic = new ArrayList<>();
        myMusic.addAll(musicPlayer);
    }

    public void SongChange(Song song){
        TextView titleSong = findViewById(R.id.titleSong);
        TextView nameArtist = findViewById(R.id.nameArtist);

        titleSong.setText(song.getTitle());
        nameArtist.setText(song.getArtist());

    }

    public void playPause(View view) {

        /*if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btn_play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }else{
            mediaPlayer.start();
            btn_play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }*/

        if(manager.isActivo()){
            manager.Pause();
            btn_play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        }else{
            manager.Play();
            btn_play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        }

    }

    @Override
    public void onProgress(int time) {
        System.out.println("Hola");
        Log.i("TIME", "onProgress: "+time);
    }

    @Override
    public void finish(boolean bol) {

    }

}