package com.example.soundflows.Services;

import com.example.soundflows.Model.Banner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Dataservice {

    @GET("songBanner.php")
    Call<List<Banner>> GetDataBanner();
}
