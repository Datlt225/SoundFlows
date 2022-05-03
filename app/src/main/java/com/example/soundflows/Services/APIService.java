package com.example.soundflows.Services;

public class APIService {
    private static String base_url = "http://127.0.0.1:5000/";

    public static Dataservice getService() {
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
