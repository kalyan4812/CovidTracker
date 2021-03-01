package com.example.covidtracker;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MyInterface {
    @GET("data.json")
     Call<Response> getStatewise();
}
