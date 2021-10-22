package com.example.mymusic;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class category extends Fragment implements MusicObserver, OnItemSelected<Song>{
    View viewInit;
    MaterialToolbar toolbar;

    List<Song> myMusic;
    List<Song> query;
    String category;
    private ListSong listSong;

    private RecyclerView rv;
    private SongListAdapter songsListAdapter;

    public category() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listSong = ListSong.getInstance();
        listSong.init(this);

        myMusic = new ArrayList<>();
        myMusic.addAll(listSong.getMusica());

        query = new ArrayList<>();

        category = listSong.getCategory();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewInit = inflater.inflate(R.layout.fragment_category, container, false);
        toolbar = viewInit.findViewById(R.id.topAppBar);
        toolbar.setTitle(category);
        search(viewInit, category);
        setupUI(viewInit);
        return viewInit;
    }

    private void setupUI(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        songsListAdapter = new SongListAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(songsListAdapter);

        fetchData();
    }

    private void fetchData() {
        listSong.setListPlay(query);
        songsListAdapter.update(query);
        songsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnPlayListChange(List<Song> music) {

    }

    @Override
    public void ActualSongChange(Song song) {

    }

    @Override
    public void HistorialListChange(List<Song> historial) {

    }

    @Override
    public void ActualCategory(String category) {
        toolbar.setTitle("category");
        toolbar.setSubtitle("Adios");
        Log.i("Category", "OnPlayListChange: "+category);
    }

    @Override
    public void ActualMediaPlayer(MediaPlayer player) {

    }

    @Override
    public void ActualListPlayer(List<Song> musicPlayer) {

    }

    @Override
    public void onSelect(Song item) {
        listSong.setSong(item);
        listSong.setListPlay(query);
    }

    public void search(View view, String cadena){
        query = new ArrayList<>();
        for (int i = 0; i<myMusic.size(); i++){
            if(myMusic.get(i).getArtist().contains(cadena) || myMusic.get(i).getAlbum().contains(cadena)){
                Song nuevo = new Song(myMusic.get(i).getUri(),myMusic.get(i).getTitle(),myMusic.get(i).getArtist(),myMusic.get(i).getAlbum(),myMusic.get(i).getDuration());
                query.add(nuevo);
            }
        }
        setupUI(viewInit);

    }
}