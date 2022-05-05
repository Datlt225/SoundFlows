package com.example.soundflows.Services;

import com.example.soundflows.Model.Album;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.Model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {

    @GET("banner")
    Call<List<Banner>> GetDataBanner();

    @GET("album")
    Call<List<Album>> GetDataAlbum();

    @GET("song")
    Call<List<Song>> GetDataSong();

    @POST("playlist_banner")
    Call<List<Song>> GetPlayListBanner(@Body String ID_ads);

    @POST("playlist_album")
    Call<List<Song>> GetPlayListAlbum(@Body String ID_album);

    @POST("searching")
    Call<List<Song>> GetDataSearching(@Body String word);
}
