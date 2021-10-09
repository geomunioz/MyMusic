package com.example.mymusic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    MaterialCardView playerMin;
    player player = new player();

    private SongViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpNavigation();

        playerMin = (MaterialCardView) findViewById(R.id.playerMin);

        //Creacion de ViewModel
        model = new ViewModelProvider(this).get(SongViewModel.class);
        loadMusic();
    }

    public void loadMusic(){
        List<Song> listSong = new ArrayList<>();

        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if(songCursor != null && songCursor.moveToFirst()) {
            int songId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songSize = songCursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int songMimeType = songCursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int songTitleURI = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentArtist = songCursor.getString(songArtist);
                String currentSize = songCursor.getString(songSize);
                String currentMimeType = songCursor.getString(songMimeType);
                String currentDuration = songCursor.getString(songDuration);
                String currentTitleUri = songCursor.getString(songTitleURI);

                if(currentMimeType.equalsIgnoreCase("audio/mpeg")) {
                    int duration = Integer.parseInt(currentDuration);

                    Song nueva = new Song(currentId, currentTitle, currentArtist, currentAlbum, duration);
                    listSong.add(nueva);
                    System.out.println("Lista: "+listSong.size());

                    System.out.println("id: " + currentId);
                    System.out.println("Title: " + currentTitle);
                    System.out.println("Album: " + currentAlbum);
                    System.out.println("Artist: " + currentArtist);
                    System.out.println("Size: " + currentSize);
                    System.out.println("AlbumArtist: " + currentMimeType);
                    System.out.println("Duration: " + duration);
                    System.out.println("TitleUri: " + currentTitleUri);
                }
            } while(songCursor.moveToNext());
        }
        model.getListSong().postValue(listSong);

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

}