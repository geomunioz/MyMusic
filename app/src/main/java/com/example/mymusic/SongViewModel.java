package com.example.mymusic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SongViewModel extends ViewModel {
    private MutableLiveData<String> itemSong;
    private MutableLiveData<List<Song>> listSong = new MutableLiveData<List<Song>>();;

    public MutableLiveData<String> getItemSong(){
        if (itemSong == null) {
            itemSong = new MutableLiveData<String>();
        }
        return itemSong;
    }

    public MutableLiveData<List<Song>> getListSong(){
        if(listSong==null){
            System.out.println("Hola dentro de ViewModel");
            listSong =  new MutableLiveData<List<Song>>();
        }
        System.out.println("Hola ya hay datos: ");
        return listSong;
    }

}
