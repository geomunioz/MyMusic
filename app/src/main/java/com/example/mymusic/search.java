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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class search extends Fragment implements MusicObserver, OnItemSelected<Song>{
    View viewInit;
    List<Song> myMusic;
    List<Song> query;
    private ListSong listSong;

    private RecyclerView rv;
    private SongListAdapter songsListAdapter;

    public search() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewInit = inflater.inflate(R.layout.fragment_search, container, false);
        setupUI(viewInit);

        MaterialButton btn_search = viewInit.findViewById(R.id.search);
        EditText input = viewInit.findViewById(R.id.editText);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtInput = input.getText().toString();
                search(viewInit, txtInput);
            }
        });

        // Inflate the layout for this fragment
        return viewInit;
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        myMusic.clear();
        myMusic.addAll(music);
        Log.i("Musica", "OnPlayListChange: "+myMusic.size());
        setupUI(viewInit);
    }

    @Override
    public void ActualSongChange(Song song) {

    }

    @Override
    public void HistorialListChange(List<Song> historial) {

    }

    @Override
    public void ActualCategory(String category) {

    }

    @Override
    public void ActualMediaPlayer(MediaPlayer player) {

    }

    @Override
    public void ActualListPlayer(List<Song> musicPlayer) {

    }

    private void setupUI(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        songsListAdapter = new SongListAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(songsListAdapter);

        fetchData();
    }

    private void fetchData() {
        songsListAdapter.update(query);
        songsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelect(Song item) {
        listSong.setSong(item);
        listSong.setListPlay(myMusic);
    }

    public void search(View view, String cadena){
        query = new ArrayList<>();
        for (int i = 0; i<myMusic.size(); i++){
            if(myMusic.get(i).getTitle().contains(cadena)){
                Song nuevo = new Song(myMusic.get(i).getUri(),myMusic.get(i).getTitle(),myMusic.get(i).getArtist(),myMusic.get(i).getAlbum(),myMusic.get(i).getDuration());
                query.add(nuevo);
            }
        }
        setupUI(viewInit);

    }
}