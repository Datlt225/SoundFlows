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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.soundflows.Adapter.PlayListAdapter;
import com.example.soundflows.Model.Album;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.Model.Song;
import com.example.soundflows.R;
import com.example.soundflows.Services.APIService;
import com.example.soundflows.Services.Dataservice;
import com.example.soundflows.constant.UserConstant;
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

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewPlaylist;
    FloatingActionButton floatingActionButton;
    Banner banner;
    Album album;
    ImageView imageViewPlaylist;
    ArrayList<Song> arraySong;
    PlayListAdapter playlistAdapter;

    Dataservice dataservice = APIService.getService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        DataIntent();
        mapping();
        init();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (banner != null && !banner.getNameSong().equals("")){
            setValueInView(banner.getNameSong(), banner.getImgSong());
            GetDataBanner(banner.getIDAds());
        }

        if (album != null && !album.getNameAlbum().equals("")) {
            setValueInView(album.getNameAlbum(), album.getImgAlbum());
            GetDataAlbum(album.getIDAlbum());
        }
    }

    /**
     * Lấy dữ liệu album về
     * @param idAlbum
     */
    private void GetDataAlbum(String idAlbum) {
        Album album = new Album();
        album.setIDAlbum(idAlbum);
        Call<List<Song>> callback = dataservice.GetPlayListAlbum(album);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                arraySong = (ArrayList<Song>) response.body();
                playlistAdapter = new PlayListAdapter(PlaylistActivity.this, arraySong);
                recyclerViewPlaylist.setLayoutManager(new
                        LinearLayoutManager(PlaylistActivity.this));
                recyclerViewPlaylist.setAdapter(playlistAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    /**
     * Lấy dữ liệu của phần quản cáo về
     * @param idAds
     */
    private void GetDataBanner(String idAds) {
        Banner banner = new Banner();
        banner.setIDAds(idAds);
        Call<List<Song>> callback = dataservice.GetPlayListBanner(banner);
        callback.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                arraySong = (ArrayList<Song>) response.body();
                playlistAdapter = new PlayListAdapter(PlaylistActivity.this, arraySong);
                recyclerViewPlaylist.setLayoutManager(new
                        LinearLayoutManager(PlaylistActivity.this));
                recyclerViewPlaylist.setAdapter(playlistAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {

            }
        });
    }

    /**
     *  gán phương thức lên phần collapsing
     * @param name
     * @param img
     */
    private void setValueInView(String name, String img) {
        collapsingToolbarLayout.setTitle(name);
        try {
            URL url = new URL(img);

            // dùng bitmap để gán dữ liệu lên
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Picasso.get().load(img).into(imageViewPlaylist);
    }

    /**
     * set tool bar cho màn hình playlist
     */
    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void mapping() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbarPlaylist);
        recyclerViewPlaylist = findViewById(R.id.recyclerviewPlaylist);
        floatingActionButton = findViewById(R.id.floatingactionbtn);
        imageViewPlaylist = findViewById(R.id.imgPlaylist);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if ( intent != null) {
            if (intent.hasExtra(UserConstant.KEY_PUT_BANNER_PLAYLIST)) {
                banner = (Banner) intent.getSerializableExtra(UserConstant.KEY_PUT_BANNER_PLAYLIST);
                Toast.makeText(this, banner.getContent(), Toast.LENGTH_SHORT).show();
            }

            if (intent.hasExtra(UserConstant.KEY_PUT_ALBUM_PLAYLIST)) {
                album = (Album) intent.getSerializableExtra(UserConstant.KEY_PUT_ALBUM_PLAYLIST);
                Toast.makeText(this, album.getNameAlbum(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void eventClick() {
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this,
                        PlaySongActivity.class);
                intent.putExtra(UserConstant.KEY_ARRAY_SONGS, arraySong);
                startActivity(intent);
            }
        });
    }
}