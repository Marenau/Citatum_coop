package com.corylab.citatum.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Интерфейс для взаимодействия с Forismatic API.
 */
public interface ForismaticApi {

    /**
     * Получить цитату с помощью Forismatic API.
     *
     * @param method Метод запроса.
     * @param format Формат ответа.
     * @param key    Ключ.
     * @param lang   Язык цитаты.
     * @return Call с объектом ForismaticQuote в качестве результата.
     */
    @GET("api/1.0/")
    Call<ForismaticQuote> getQuote(
            @Query("method") String method,
            @Query("format") String format,
            @Query("key") Integer key,
            @Query("lang") String lang
    );
}