package com.example.mymusic;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class library extends Fragment implements MusicObserver, OnItemSelected<Song>, OnGrupSelected<String>{
    View viewInit;
    List<Song> myMusic;
    List<String> query;
    private ListSong listSong;

    private RecyclerView rv;
    private SongListAdapter songsListAdapter;
    private GrupListAdapter grupListAdapter;


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

        query = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewInit = inflater.inflate(R.layout.fragment_library, container, false);
        setupUI(viewInit);

        Chip chip_Artist=viewInit.findViewById(R.id.chipArtista);
        chip_Artist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                search(view, "Artista");
            }
        });

        Chip chip_Album=viewInit.findViewById(R.id.chipAlbum);
        chip_Album.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                search(view, "Album");
            }
        });
        // Inflate the layout for this fragment
        return viewInit;
    }

    @Override
    public void OnPlayListChange(List<Song> music) {
        myMusic.clear();
        myMusic.addAll(music);
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

    private void setupUICategory(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);

        grupListAdapter = new GrupListAdapter(this);

        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(grupListAdapter);

        fetchDataCategory();
    }

    private void fetchData() {
        songsListAdapter.update(myMusic);
        songsListAdapter.notifyDataSetChanged();
    }

    private void fetchDataCategory() {
        grupListAdapter.update(query);
        grupListAdapter.notifyDataSetChanged();
    }

    public void selected(View view){
        Toast.makeText(getContext(),"Select",Toast.LENGTH_LONG);
    }


    @Override
    public void onSelect(Song item) {
        listSong.setSong(item);
        listSong.setListPlay(myMusic);
    }

    public void search(View view, String cadena){
        query = new ArrayList<>();
        for (int i = 0; i<myMusic.size(); i++){
            if(cadena.equalsIgnoreCase("Artista")){
                if(!query.contains(myMusic.get(i).getArtist())){
                    Song nuevo = new Song(myMusic.get(i).getUri(),myMusic.get(i).getTitle(),myMusic.get(i).getArtist(),myMusic.get(i).getAlbum(),myMusic.get(i).getDuration());
                    query.add(nuevo.getArtist());
                }
            }
            if (cadena.equalsIgnoreCase("Album")){
                if(!query.contains(myMusic.get(i).getAlbum())){
                    Song nuevo = new Song(myMusic.get(i).getUri(),myMusic.get(i).getTitle(),myMusic.get(i).getArtist(),myMusic.get(i).getAlbum(),myMusic.get(i).getDuration());
                    query.add(nuevo.getAlbum());
                }
            }
        }
        setupUICategory(viewInit);

    }

    @Override
    public void onSelectGroup(String item) {
        listSong.setCategory(item);
        Navigation.findNavController(viewInit).navigate(R.id.action_library_to_category);

    }
}