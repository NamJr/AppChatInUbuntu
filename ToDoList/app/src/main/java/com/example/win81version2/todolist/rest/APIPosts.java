package com.example.win81version2.todolist.rest;

import com.example.win81version2.todolist.model.TaskPOJO;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * chua cac apis trong du an
 */
public interface APIPosts {

    @FormUrlEncoded
    @POST("add")
    Call<TaskPOJO> insertNewTask(
            @Field("name") String name,
            @Field("done") Boolean done
    );

    @FormUrlEncoded
    @POST("{id}")
    Call<TaskPOJO> updateTaskName(@Path("id") String id,
                              @Field("name") String name,
                              @Field("done") Boolean done

    );

    @FormUrlEncoded
    @POST("{id}")
    Call<TaskPOJO> updateTaskDone(@Path("id") String id,
                                  @Field("name") String name,
                                  @Field("done") Boolean done

    );

}
