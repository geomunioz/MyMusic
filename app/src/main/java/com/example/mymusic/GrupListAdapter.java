package com.example.mymusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GrupListAdapter extends RecyclerView.Adapter<GrupListAdapter.ViewHolder>{
    private List<String> grup;
    private OnGrupSelected<String> onGrupSelected;

    public GrupListAdapter(OnGrupSelected<String> onGrupSelected) {
        this.grup = new ArrayList<>();
        this.onGrupSelected = onGrupSelected;
    }

    @NonNull
    @Override
    public GrupListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grup, parent, false);
        return new GrupListAdapter.ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupListAdapter.ViewHolder view, int position) {
        String song = grup.get(position);
        view.tvItemTitle .setText(song);
        view.itemView.setOnClickListener(v -> onGrupSelected.onSelectGroup(song));
    }

    @Override
    public int getItemCount() {
        return grup.size();
    }

    public void update(List<String> data) {
        this.grup.clear();
        this.grup.addAll(data);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setupUICategory();
        }

        private void setupUICategory() {
            tvItemTitle = itemView.findViewById(R.id.txtViewTitle);
        }
    }
}
