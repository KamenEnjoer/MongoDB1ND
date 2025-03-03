package com.example.mongodb1nd;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("/get_names")
    Call<List<String>> getNames();
}