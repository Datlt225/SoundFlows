package com.example.soundflows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soundflows.Activity.PlaylistActivity;
import com.example.soundflows.Model.Album;
import com.example.soundflows.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    Context context;
    ArrayList<Album> mangAlbum;

    public AlbumAdapter(Context context, ArrayList<Album> mangAlbum) {
        this.context = context;
        this.mangAlbum = mangAlbum;
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
        Album album = mangAlbum.get(position);
        holder.txtTenAlbum.setText(album.getNameAlbum());
        Picasso.get().load(album.getImgAlbum()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mangAlbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView txtTenAlbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAlbum);
            txtTenAlbum = itemView.findViewById(R.id.textviewAlbumName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlaylistActivity.class);
                    intent.putExtra("album",mangAlbum.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
