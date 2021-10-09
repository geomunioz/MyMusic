package com.example.mymusic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SongViewModel extends ViewModel {
    private MutableLiveData<Song> itemSong;
    private MutableLiveData<List<Song>> listSong;

    public MutableLiveData<Song> getSong(){

        return itemSong;
    }

    public MutableLiveData<List<Song>> getListSong(){
        if(listSong==null){
            System.out.println("Hola dentro de ViewModel");
            return new MutableLiveData<List<Song>>();
        }
        System.out.println("Hola ya hay datos");
        return listSong;
    }


}
