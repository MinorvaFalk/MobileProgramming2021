package com.example.week11_33031;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class configRetro {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static Retrofit retrofit = null;
    public static Retrofit getCliet(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
