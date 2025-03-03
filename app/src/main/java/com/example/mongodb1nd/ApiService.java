package com.example.mongodb1nd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/get_names")
    Call<List<SomeItem>> getItems();
}