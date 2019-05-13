package com.example.newsapiapp.network;

import com.example.newsapiapp.model.Response;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsApi {

    @GET("top-headlines")
    Single<Response> topHeadlines(@QueryMap Map<String, String> options);

    @GET("everything")
    Single<Response> everything(@QueryMap Map<String, String> options);
}
