package com.example.soundflows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Model.Song;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.RowFavoriteSongBinding;
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavoriteSongAdapter extends RecyclerView.Adapter<FavoriteSongAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Song> songArrayList;
    Users mUsers;

    public FavoriteSongAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mUsers = new Gson().fromJson(AppPrefsUtils.getString(UserConstant.KEY_USER_DATA), Users.class);

        return new ViewHolder(RowFavoriteSongBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.binding(song);
    }

    @Override
    public int getItemCount() {
        if (songArrayList.size() > 0)
            return songArrayList.size();

        return 0;
    }

    /**
     * This method updates the data list and notify the changes
     * @param favoriteSongResponse
     */
    public void setCateAdapter(ArrayList<Song> favoriteSongResponse) {
        songArrayList = favoriteSongResponse;
        notifyDataSetChanged();
    }

    /**
     * return date list
     * @return
     */
    public ArrayList<Song> getFavoriteSongAdapter() {
        return songArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RowFavoriteSongBinding binding;

        public ViewHolder(@NonNull RowFavoriteSongBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void binding(Song song) {
            String nameSong = song.getNameSong();
            String singer = song.getSinger();

            binding.tvNameFavoriteSong.setText(nameSong);
            binding.tvSingerFavoriteSong.setText(singer);
            binding.tvIndexFavoriteSong.setText(getAdapterPosition() + 1 + "");
        }
    }
}
