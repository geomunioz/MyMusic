package com.example.mymusic;

import static java.lang.Thread.sleep;

import android.content.ContentUris;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPlayerManager {
    private Context context;
    private Uri uri;
    private Song song;
    private MediaPlayer media;
    private Runnable nuevo;
    private List<MediaListener> suscribers;

    public MediaPlayerManager() {
        this.suscribers = new ArrayList<>();
        this.media = new MediaPlayer();
        this.media.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    public void newPLay(){
        /*nuevo = new Runnable() {
            @Override
            public void run() {

                while(media.getCurrentPosition()!=media.getDuration()){
                    if(media.isPlaying()){
                        try {
                            sleep(1000);
                            for (int i = 0; i<suscribers.size(); i++){
                                suscribers.get(i).onProgress(media.getCurrentPosition());
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };*/
    }

    public void prepareSong( Song cancion){
        song = cancion;
        uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,song.getUri());

        if(media.isPlaying()){
            media.stop();
            media.release();
            try {
                media = new MediaPlayer();
                media.setDataSource(context,uri);
                media.prepare();
                media.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                media = new MediaPlayer();
                media.setDataSource(context,uri);
                media.prepare();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public void addListener(MediaListener suscriber){
        if(!suscribers.contains(suscriber)){
            suscribers.add(suscriber);
        }
    }

    public void dispose(MusicObserver suscriber){
            suscribers.remove(suscriber);
    }

    public void Play(){
        media.start();
    }

    public void Pause(){
        media.stop();
    }

    public boolean isActivo(){
        return media.isPlaying();
    }

    public void Repeat(){
        if(media.isLooping()){
            media.setLooping(false);
        }else{
            media.setLooping(true);
        }
    }

    public void setMedia(MediaPlayer mediaPlayer){
        media = mediaPlayer;
    }

    public MediaPlayer getMedia(){
        return media;
    }

    public void setContext(Context contexto){
        context = contexto;
    }

}
