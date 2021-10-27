package com.example.soundflows.Services;

public class APIService {
    private static String base_url = "https://soundflowsmusic.000webhostapp.com/Server/";

    public static Dataservice getService() {
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
