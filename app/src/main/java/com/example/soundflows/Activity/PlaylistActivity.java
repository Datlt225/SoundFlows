package com.example.soundflows.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soundflows.Adapter.PlayListAdapter;
import com.example.soundflows.Model.Album;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIRetrofitClient;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
import com.example.soundflows.databinding.ActivityPlaylistBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistActivity extends AppCompatActivity {
    private ActivityPlaylistBinding binding;
    private Banner banner;
    private Album album;
    private ArrayList<Song> songArrayList;
    private PlayListAdapter playListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DataIntent();
        init();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (banner != null && !banner.getNameSong().equals("")) {
            setValueInView(banner.getNameSong(), banner.getImgSong());
            GetDataBanner(banner.getIDAds());
        }

        if (album != null && !album.getNameAlbum().equals("")) {
            setValueInView(album.getNameAlbum(), album.getImgAlbum());
            GetDataAlbum(album.getIDAlbum());
        }
    }

    private void init() {
        setSupportActionBar(binding.toolbarPlaylist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.faButton.setEnabled(false);

        binding.toolbarPlaylist.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
    }

    private void GetDataAlbum(String idAlbum) {
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> callback = dataservice.GetPlayListAlbum(idAlbum);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                playListAdapter = new PlayListAdapter(PlaylistActivity.this, songArrayList);

                binding.rvPlaylist.setLayoutManager(
                        new LinearLayoutManager(PlaylistActivity.this));

                binding.rvPlaylist.setAdapter(playListAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void GetDataBanner(String idAds) {
        Dataservice dataservice = APIService.getService();
        Call<List<Song>> callback = dataservice.GetPlayListBanner(idAds);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                songArrayList = (ArrayList<Song>) response.body();
                playListAdapter = new PlayListAdapter(PlaylistActivity.this, songArrayList);

                binding.rvPlaylist.setLayoutManager(
                        new LinearLayoutManager(PlaylistActivity.this));

                binding.rvPlaylist.setAdapter(playListAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    private void eventClick() {
        binding.faButton.setEnabled(true);
        binding.faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        PlaylistActivity.this, PlaySongActivity.class);

                intent.putExtra(UserConstant.KEY_ARRAY_SONGS, songArrayList);
                startActivity(intent);
            }
        });
    }

    private void setValueInView(String nameSong, String imgSong) {

        binding.collapsingToolbar.setTitle(nameSong);

        try {
            URL url = new URL(imgSong);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            binding.collapsingToolbar.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.get().load(imgSong).into(binding.ivPlaylistList);
    }

    private void DataIntent() {
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(UserConstant.KEY_PUT_BANNER_PLAYLIST)) {
                banner = (Banner) intent.getSerializableExtra(UserConstant.KEY_PUT_BANNER_PLAYLIST);
            }

            if (intent.hasExtra(UserConstant.KEY_PUT_ALBUM_PLAYLIST)) {
                album = (Album) intent.getSerializableExtra(UserConstant.KEY_PUT_ALBUM_PLAYLIST);
            }
        }
    }
}