package com.example.mymusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import java.sql.SQLOutput;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataManager {

    public static List<Song> getData(Context context){
        List<Song> listSong = new ArrayList<>();

        ContentResolver contentResolver = context.getContentResolver();
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
            int songAlbumID = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);

            do {
                long currentId = songCursor.getLong(songId);
                String currentTitle = songCursor.getString(songTitle);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentArtist = songCursor.getString(songArtist);
                String currentSize = songCursor.getString(songSize);
                String currentMimeType = songCursor.getString(songMimeType);
                String currentDuration = songCursor.getString(songDuration);
                String currentTitleUri = songCursor.getString(songTitleURI);
                long currentAlbumID = songCursor.getLong(songAlbumID);

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
        songCursor.close();
        Comparator<Song> comparador = new Comparator<Song>() {
            @Override
            public int compare(Song song, Song t1) {
                return song.getTitle().compareTo(t1.getTitle());
            }
        };
        Collections.sort(listSong, comparador);

        Uri urialbum = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursorAlbum = contentResolver.query(urialbum,null, null, null);
        Log.i("Cursor", "getData: +"+cursorAlbum);
        if(cursorAlbum !=null && cursorAlbum.moveToFirst()) {
            System.out.println("Entre");
            int album = cursorAlbum.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do{
                System.out.println("Entre2");
                String albumCoverPath = cursorAlbum.getString(album);
                Log.i("ALBUM", "getData: "+albumCoverPath);;
            }while(cursorAlbum.moveToNext());
        }
        cursorAlbum.close();

        return  listSong;

    }
}
