package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaySongActivity;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchingAdapter extends RecyclerView.Adapter<SearchingAdapter.ViewHolder>{
    Context context;
    ArrayList<Song> songArrayList = new ArrayList<>();

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
        holder.txtNameSong.setText(song.getNameSong());
        holder.txtSinger.setText(song.getSinger());
        Picasso.get().load(song.getImgSong()).into(holder.imgSong);
//        Picasso.get().load(song.getLiked()).into(holder.imgLiked);
    }

    @Override
    public int getItemCount() {
        return songArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameSong, txtSinger;
        ImageView imgSong, imgLiked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameSong = itemView.findViewById(R.id.textviewSearchingNameSong);
            txtSinger = itemView.findViewById(R.id.textviewSearchingSinger);
            imgSong = itemView.findViewById(R.id.imgviewSearching);
            imgLiked = itemView.findViewById(R.id.imgviewSearchingLiked);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaySongActivity.class);
                    intent.putExtra("song", songArrayList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

//            imgLiked.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imgLiked.setImageResource(R.drawable.iconloved);
//                    Dataservice dataservice = APIService.getService();
//                    Call<String> callback = dataservice.UpdateLiked("1",
//                            songArrayList.get(getAdapterPosition()).getIDSong());
//                    callback.enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            String result = response.body();
//                            if(result.equals("succes")){
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
//                    imgLiked.setEnabled(false);
//                }
//            });
        }
    }
}
