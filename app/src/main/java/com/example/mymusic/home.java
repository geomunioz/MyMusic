package com.example.mymusic;

import android.media.MediaPlayer;
import android.media.metrics.LogSessionId;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment implements MusicObserver,OnItemSelected<Song>{
    View view;
    List<Song> myMusic;
    ListSong listSong;

    RecyclerView rv;
    SongListAdapter songsListAdapter;

    public home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listSong = ListSong.getInstance();
        listSong.init(this);

        myMusic = new ArrayList<>();
        myMusic.addAll(listSong.getHistorial());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        setupUI(view);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        /*myMusic.clear();
        myMusic.addAll(music);
        Log.i("Musica", "OnPlayListChange: "+myMusic.size());
        setupUI(view);*/
    }

    @Override
    public void ActualSongChange(Song song) {

    }

    @Override
    public void HistorialListChange(List<Song> historial) {
        myMusic.clear();
        myMusic.addAll(historial);
        setupUI(view);
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
        rv = view.findViewById(R.id.rv);

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
        listSong.setListPlay(myMusic);
    }

}