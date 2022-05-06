package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder>{

    Context context;
    ArrayList<Song>  arraySong;

    public PlayListAdapter(Context context, ArrayList<Song> arraySong) {
        this.context = context;
        this.arraySong = arraySong;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_playlist, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = arraySong.get(position);
        holder.txtSinger.setText(song.getSinger());
        holder.txtNameSong.setText(song.getNameSong());
        holder.txtIndex.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        if (arraySong != null)
            return arraySong.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtIndex, txtNameSong, txtSinger;
        ImageView imgLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSinger = itemView.findViewById(R.id.textviewSingerName);
            txtIndex = itemView.findViewById(R.id.textviewListIndex);
            txtNameSong = itemView.findViewById(R.id.textviewNameSong);
            imgLike = itemView.findViewById(R.id.imgviewLikedPlaylist);

//            imgLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imgLike.setImageResource(R.drawable.iconloved);
//                    Dataservice dataservice= APIService.getService();
//                    Call<String> callback = dataservice.UpdateLiked("1",
//                            arraySong.get(getAdapterPosition()).getIDSong());
//                    callback.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            String result = response.body();
//                            if (result.equals("succes")) {
//                                Toast.makeText(context, "Da thich", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(context, "Loi~", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<String> call, Throwable t) {
//
//                        }
//                    });
//
//                    imgLike.setEnabled(false);
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaySongActivity.class);
                    intent.putExtra("song", arraySong.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
