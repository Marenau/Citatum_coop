package com.corylab.citatum.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForismaticApi {
    @GET("api/1.0/")
    Call<ForismaticQuote> getQuote(
            @Query("method") String method,
            @Query("format") String format,
            @Query("key") Integer key,
            @Query("lang") String lang
    );
}