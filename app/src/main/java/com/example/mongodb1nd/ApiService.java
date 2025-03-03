package com.example.mongodb1nd;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/get_items")
    Call<List<SomeItem>> getItems();

    @DELETE("/delete_item/{id}")
    Call<Void> deleteItem(@Path("id") String id);

    @PUT("/update_item/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> updateItem(@Path("id") String id, @Body HashMap<String, String> body);
}
