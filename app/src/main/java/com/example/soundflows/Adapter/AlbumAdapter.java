package com.example.soundflows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaylistActivity;
import com.example.soundflows.Model.Album;
import com.example.soundflows.R;
import com.example.soundflows.constant.UserConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_album, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.tvAlbum.setText(album.getNameAlbum());
        Picasso.get().load(album.getImgAlbum()).into(holder.ivAlbum);
    }

    @Override
    public int getItemCount() {
        if (albumArrayList != null)
            return albumArrayList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAlbum;
        TextView tvAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAlbum = itemView.findViewById(R.id.iv_album);
            tvAlbum = itemView.findViewById(R.id.tv_name_album);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaylistActivity.class);

                    intent.putExtra(UserConstant.KEY_PUT_ALBUM_PLAYLIST,
                            albumArrayList.get(getAdapterPosition()));

                    Toast.makeText(context, UserConstant.KEY_PUT_ALBUM_PLAYLIST, Toast.LENGTH_SHORT).show();

                    context.startActivity(intent);
                }
            });
        }
    }
}
