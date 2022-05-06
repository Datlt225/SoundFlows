package com.example.soundflows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Model.Song;
import com.example.soundflows.R;

import java.util.ArrayList;

public class PlaylistInPlayAdapter extends RecyclerView.Adapter<PlaylistInPlayAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Song> songArrayList;

    public PlaylistInPlayAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_playlist_in_play, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.tvNameSong.setText(song.getNameSong());
        holder.tvSinger.setText(song.getSinger());
        holder.tvIndex.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        if (songArrayList != null)
            return songArrayList.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndex, tvNameSong, tvSinger;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndex = itemView.findViewById(R.id.textviewPlaylistIndex);
            tvNameSong = itemView.findViewById(R.id.textviewPlaylistNameSong);
            tvSinger = itemView.findViewById(R.id.textviewPlaylistSinger);
        }
    }
}
