package br.com.sossp.sosspapp.config;

import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigurationRetrofit {

    @Getter private Retrofit retrofit;
    public static final String API_BASE_URL = "http://10.0.2.2:8080/api/";

    public void buildRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
