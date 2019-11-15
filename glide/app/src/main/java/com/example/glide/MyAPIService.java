package com.example.glide;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyAPIService {
    // https://www.flickr.com/services/rest/
    // ?method=flickr.photos.search&api_key=f5c47104535c1a739703c68cc1680aec&text=test&format=json&nojsoncallback=1&api_sig=c9151c817e9c27a0e376191b690652f5
    @GET("method=flickr.photos.search&api_key=7caa7d56fbcba0bf969ef339d62aa4ea&text=test&format=json&nojsoncallback=1&api_sig=02bf1b1eb2b06c734fa61573e7341df1")
    // 設置一個GET連線，路徑為albums/1
    Call<Flickr> getFlickr();

    @GET("services/rest/?method=flickr.photos.search&api_key=168f07b2197a2fa14efcee247ede4843&tags=apple&format=json&nojsoncallback=1")
    Call<Example> getNew();

    @GET("services/rest/?method=flickr.photos.search&api_key=168f07b2197a2fa14efcee247ede4843&text=test&format=json&nojsoncallback=1")
        // 設置一個GET連線，路徑為albums/1
    Call<Example> getExample();

    @GET("services/rest/?method=flickr.photos.search&api_key=168f07b2197a2fa14efcee247ede4843&format=json&nojsoncallback=1")
    Call<Example> getSearch(@Query("tags") String tags);

}
