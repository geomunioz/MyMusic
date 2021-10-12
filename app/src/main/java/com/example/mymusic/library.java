package com.example.mymusic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class library extends Fragment implements MusicObserver, OnItemSelected<Song>{
    View view;
    List<Song> myMusic;
    private ListSong listSong;

    private RecyclerView rv;
    private SongListAdapter songsListAdapter;


    public library() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listSong = ListSong.getInstance();
        listSong.init(this);

        myMusic = new ArrayList<>();
        myMusic.addAll(listSong.getMusica());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library, container, false);
        setupUI(view);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        myMusic.clear();
        myMusic.addAll(music);
        Log.i("Musica", "OnPlayListChange: "+myMusic.size());
        setupUI(view);
    }

    @Override
    public void ActualSongChange(Song song) {

    }

    private void setupUI(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        songsListAdapter = new SongListAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(songsListAdapter);

        fetchData();
    }

    private void fetchData() {
        songsListAdapter.update(myMusic);
        songsListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSelect(Song item) {
        listSong.setSong(item);
    }
}