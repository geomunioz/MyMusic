package com.example.mymusic;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSong {
    private static ListSong instance = null;
    private Song song;
    private List<Song> musica;
    private List<Song> historial;
    private List<Song> listPlay;
    private List<MusicObserver> suscribers;
    private String category;
    // Modificado private MediaPlayer mplayer;
    private MediaPlayerManager manager;
    //private Uri uri;
    //private Context context;

    private ListSong() {
        System.out.println("Singleton invoked");
        this.musica = new ArrayList<>();
        this.listPlay = new ArrayList<>();
        this.historial = new ArrayList<>();
        this.song = new Song(0, "", "", "", 0);
        this.suscribers = new ArrayList<>();
        this.category = "";
        this.manager = new MediaPlayerManager();
        // this.mplayer = new MediaPlayer();
        // mplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new ListSong();
        }
    }

    public static ListSong getInstance() {
        if (instance == null) createInstance();
        return instance;
    }

    public void printList() {
        for(int i=0; i<musica.size();i++){
            System.out.println("Nombre: "+musica.get(i).getTitle());
        }
    }

    public void init(MusicObserver suscriber){
        if(!suscribers.contains(suscriber)){
            suscribers.add(suscriber);
        }
    }

    public void dispose(MusicObserver suscriber){
        if(suscribers.contains(suscriber)){
            suscribers.remove(suscriber);
        }
    }

    /*public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }*/

    public void setMusica(List<Song> miMusica){
        this.musica = new ArrayList<>();
        this.musica.addAll(miMusica);
        this.song = miMusica.get(0);
        this.listPlay = new ArrayList<>();
        this.listPlay.addAll(miMusica);
        //prepareSong();
        manager.prepareSong(song);
        manager.newPLay();
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).OnPlayListChange(miMusica);
            suscribers.get(i).ActualSongChange(miMusica.get(0));
            //suscribers.get(i).ActualMediaPlayer(mplayer);
            suscribers.get(i).ActualListPlayer(listPlay);
        }
    }

    public List<Song> getMusica() {
        return musica;
    }

    public List<Song> getHistorial(){return historial;}

    public void setSong(Song actual){
        this.song = actual;

        List<Song> copia = new ArrayList<>();
        for (int i = 0 ; i<historial.size(); i++){
            if(historial.get(i).getTitle().equalsIgnoreCase(actual.getTitle())){
                historial.remove(i);
            }
        }
        copia.add(actual);
        copia.addAll(historial);
        historial.clear();
        historial.addAll(copia);
        //prepareSong();
        manager.prepareSong(song);
        manager.newPLay();
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).ActualSongChange(actual);
            suscribers.get(i).HistorialListChange(historial);
            //suscribers.get(i).ActualMediaPlayer(mplayer);
        }
    }

    public Song getSong(){
        return song;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String nombre){
        category = nombre;
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).ActualCategory(nombre);
        }
    }

    /*public void prepareSong(){

        uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,song.getUri());

        if(mplayer.isPlaying()){
            mplayer.stop();
            mplayer.release();
            try {
                mplayer = new MediaPlayer();
                mplayer.setDataSource(context,uri);
                mplayer.prepare();
                mplayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                mplayer = new MediaPlayer();
                mplayer.setDataSource(context,uri);
                mplayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }*/

    /*public void setMplayer(MediaPlayer player){
        mplayer = player;
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).ActualMediaPlayer(mplayer);
        }
    }*/

    /*public MediaPlayer getMplayer(){
        return mplayer;
    }*/

    public void setListPlay(List<Song> list){
        listPlay = new ArrayList<>();
        listPlay.addAll(list);
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).ActualListPlayer(listPlay);
        }
    }

    public List<Song> getListPlay(){
        return listPlay;
    }

    public MediaPlayerManager getManager(){
        return manager;
    }
}
