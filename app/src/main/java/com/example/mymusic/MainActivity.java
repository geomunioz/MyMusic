package com.example.mymusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MusicObserver{
    BottomNavigationView bottomNavigation;
    MaterialCardView playerMin;
    player player = new player();
    private ListSong listSong;

    private List<Song> myMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();
        playerMin = (MaterialCardView) findViewById(R.id.playerMin);
        myMusic = new ArrayList<>();

        listSong = ListSong.getInstance();
        listSong.init(this);
        listSong.setMusica(DataManager.getData(this));

        myMusic.addAll(listSong.getMusica());

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
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(view.getId()==R.id.playerMin){
            ft.add (R.id.constraintInit, player);
            ft.commit();
            bottomNavigation.setVisibility(View.GONE);
            playerMin.setVisibility(View.GONE);
        }

        if(view.getId()==R.id.btn_Min){
            ft.remove(player);
            ft.commit();
            bottomNavigation.setVisibility(View.VISIBLE);
            playerMin.setVisibility(View.VISIBLE);
        }
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

    public void SongChange(Song song){
        TextView titleSong = (TextView) findViewById(R.id.titleSong);
        TextView nameArtist = (TextView) findViewById(R.id.nameArtist);

        titleSong.setText(song.getTitle());
        nameArtist.setText(song.getArtist());
    }
}