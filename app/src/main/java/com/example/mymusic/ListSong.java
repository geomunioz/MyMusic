package com.example.mymusic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ListSong {
    private static ListSong instance = null;
    private Song song;
    private List<Song> musica;
    private List<MusicObserver> suscribers;

    private ListSong() {
        System.out.println("Singleton invoked");
        this.musica = new ArrayList<>();
        this.song = new Song(0, "", "", "", 0);
        this.suscribers = new ArrayList<>();
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

    public void setMusica(List<Song> miMusica){
        this.musica = new ArrayList<>();
        this.musica.addAll(miMusica);

        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).OnPlayListChange(miMusica);
            suscribers.get(i).ActualSongChange(miMusica.get(0));
        }
    }

    public List<Song> getMusica() {
        return musica;
    }

    public void setSong(Song actual){
        for (int i = 0; i<suscribers.size(); i++){
            suscribers.get(i).ActualSongChange(actual);
        }
    }

    public Song getSong(){
        return song;
    }
}
