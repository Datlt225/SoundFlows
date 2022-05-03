package com.example.soundflows.Services;

import com.example.soundflows.Model.Album;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.Model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {

    @GET("banner")
    Call<List<Banner>> GetDataBanner();

    @GET("album")
    Call<List<Album>> GetDataAlbum();

    @GET("song")
    Call<List<Song>> GetDataSong();
}
