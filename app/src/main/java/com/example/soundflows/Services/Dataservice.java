package com.example.soundflows.Services;

import com.example.soundflows.Model.Album;
import com.example.soundflows.Model.Banner;
import com.example.soundflows.Model.LoginResponse;
import com.example.soundflows.Model.RegisterResponse;
import com.example.soundflows.Model.SearchingSong;
import com.example.soundflows.Model.Song;
import com.example.soundflows.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dataservice {

    @GET("banner")
    Call<List<Banner>> GetDataBanner();

    @GET("album")
    Call<List<Album>> GetDataAlbum();

    @GET("song")
    Call<List<Song>> GetDataSong();

    @POST("playlist_banner")
    Call<List<Song>> GetPlayListBanner(@Body Banner ID_ads);

    @POST("playlist_album")
    Call<List<Song>> GetPlayListAlbum(@Body Album ID_album);

    @POST("searching")
    Call<List<Song>> GetDataSearching(@Body SearchingSong word);

    @POST("login")
    Call<LoginResponse> Login(@Body Users data);

    @POST("register")
    Call<RegisterResponse> Register(@Body Users user);

    @POST("change_info")
    Call<RegisterResponse> ChangInformation(@Body Users users);
}
