package com.example.glide;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager mInstance = new RetrofitManager();

    private MyAPIService myAPIService;
  

    private RetrofitManager() {
//   //https://www.flickr.com/services/rest/?method=flickr.photos.search&
//    // api_key=f5c47104535c1a739703c68cc1680aec&text=test&format=json&nojsoncallback=1&api_sig=c9151c817e9c27a0e376191b690652f5
        // 設置baseUrl即要連的網站，addConverterFactory用Gson作為資料處理Converter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.flickr.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myAPIService = retrofit.create(MyAPIService.class);
    }

    public static RetrofitManager getInstance() {
        return mInstance;
    }

    public MyAPIService getAPI() {
        return myAPIService;
    }
}
