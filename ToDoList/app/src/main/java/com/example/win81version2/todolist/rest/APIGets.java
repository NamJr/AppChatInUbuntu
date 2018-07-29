package com.example.win81version2.todolist.rest;

import com.example.win81version2.todolist.model.TaskPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIGets {

    @GET("all")
    Call<List<TaskPOJO>> getContacts();

    @GET("{id}")
    Call<String> delete(@Path("id") String id);

}
