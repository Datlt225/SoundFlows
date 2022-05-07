package com.example.soundflows.Services;

public class APIService {
    private static String base_url = "http://192.168.10.117:5000/";

    public static Dataservice getService() {
        return APIRetrofitClient.getClient(base_url).create(Dataservice.class);
    }
}
