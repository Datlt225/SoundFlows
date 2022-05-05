package com.example.soundflows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Song> songArrayList;

    public SearchingAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_searching, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.tvNameSong.setText(song.getNameSong());
        holder.tvSinger.setText(song.getSinger());
        Picasso.get().load(song.getImgSong()).into(holder.ivSong);
    }

    @Override
    public int getItemCount() {
        if (songArrayList != null)
            return songArrayList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameSong, tvSinger;
        ImageView ivSong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameSong = itemView.findViewById(R.id.tv_searching_name_song);
            tvSinger = itemView.findViewById(R.id.tv_searching_singer);
            ivSong = itemView.findViewById(R.id.iv_searching_song);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, PlaySongActivity.class);
//                    intent.putExtra(UserConstant.KEY_SONG, songArrayList.get(getAdapterPosition()));
//                    context.startActivity(intent);
                }
            });
        }
    }
}
