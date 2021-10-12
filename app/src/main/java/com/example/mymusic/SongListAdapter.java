package com.example.mymusic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{

    private List<Song> songs;
    private OnItemSelected<Song> onItemSelected;

    public SongListAdapter(OnItemSelected<Song> onItemSelected) {
        this.songs = new ArrayList<>();
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongListAdapter.ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder view, int position) {
        Song song = songs.get(position);
        view.tvItemSongTitle .setText(song.getTitle());
        view.tvItemSongAuthor.setText(song.getArtist());
        view.itemView.setOnClickListener(v -> onItemSelected.onSelect(song));
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void update(List<Song> data) {
        this.songs.clear();
        this.songs.addAll(data);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemSongTitle, tvItemSongAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setupUI();
        }

        private void setupUI() {
            tvItemSongTitle = itemView.findViewById(R.id.txtViewNameSong);
            tvItemSongAuthor = itemView.findViewById(R.id.txtViewNameArtist);
        }
    }
}
