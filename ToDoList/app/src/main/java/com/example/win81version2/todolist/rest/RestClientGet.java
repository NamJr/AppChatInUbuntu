package com.example.win81version2.todolist.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClientGet {

    public static final String BASE_API = "http://150.95.111.30:4444/api/";
    public static Retrofit retrofit;

    public static APIGets getRestClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIGets.class);
    }
}
