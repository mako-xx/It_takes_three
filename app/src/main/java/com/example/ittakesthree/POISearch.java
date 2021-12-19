package com.example.ittakesthree;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface POISearch {

    @GET("text")
    Call<ResponseBody> poi(@Query("key") String key, @Query("keywords") String keywords,
                           @Query("type") String type);
}
