package com.example.osmaini.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static final String base_url="http://codemantul.my.id/GIS/androidapi/";
    private static Retrofit retro = null;

    public static Retrofit getClient () {
        if (retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
    public static ApiRequest getRequestService() {
        return getClient().create(ApiRequest.class);
    }
}
