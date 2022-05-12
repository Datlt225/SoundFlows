package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.ForegroundService.NotificationService;
import com.example.soundflows.Model.LikeSong;
import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.Song;
import com.example.soundflows.Model.Users;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.utils.AppPrefsUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder>{

    Context context;
    ArrayList<Song> songArrayList;
    Dataservice dataservice= APIService.getService();
    Users mUsers;
    boolean isLiked = false;

    public SongAdapter(Context context, ArrayList<Song> songArrayList) {
        this.context = context;
        this.songArrayList = songArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);



        View view = inflater.inflate(R.layout.row_song, parent, false);
        mUsers = new Gson().fromJson(AppPrefsUtils.getString(UserConstant.KEY_USER_DATA), Users.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = songArrayList.get(position);
        holder.txtSinger.setText(song.getSinger());
        holder.txtNameSong.setText(song.getNameSong());
        Picasso.get().load(song.getImgSong()).into(holder.imgSong);

    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameSong, txtSinger;
        ImageView imgSong, imgLiked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameSong = itemView.findViewById(R.id.textviewSongName);
            txtSinger = itemView.findViewById(R.id.textviewSinger);
            imgSong = itemView.findViewById(R.id.imageviewSong);
            imgLiked = itemView.findViewById(R.id.imageLove);

            itemView.setOnClickListener(v -> {
                clickStartService();
                clickPlaySong();
            });

            imgLiked.setOnClickListener(v -> {
                LikeSong likeSong = new LikeSong();
                likeSong.setEmail(mUsers.getEmail());
                likeSong.setIdSong(songArrayList.get(getAdapterPosition()).getIDSong());

                Call<RegisterResponse> callback = dataservice.UpdateLiked(likeSong);
                callback.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, UserConstant.LIKE, Toast.LENGTH_SHORT).show();
                            imgLiked.setImageResource(R.drawable.iconloved);
                            isLiked = true;
                        } else {
                            dataservice.UnLikeSong(likeSong).enqueue(new Callback<RegisterResponse>() {
                                @Override
                                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(context, UserConstant.UNLIKE, Toast.LENGTH_SHORT).show();
                                        imgLiked.setImageResource(R.drawable.iconlove);
                                        isLiked = false;
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Log.e("Like", t.getMessage());
                    }
                });
            });
        }
        private void clickPlaySong() {
            Intent intent = new Intent(context, PlaySongActivity.class);
            intent.putExtra(UserConstant.KEY_SONG, songArrayList.get(getAdapterPosition()));

            context.startActivity(intent);
        }

        private void clickStartService() {
            Intent intent = new Intent(context, NotificationService.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(UserConstant.OBJECT_SONG, songArrayList.get(getAdapterPosition()));
            intent.putExtras(bundle);

            context.startService(intent);
        }
    }
}
