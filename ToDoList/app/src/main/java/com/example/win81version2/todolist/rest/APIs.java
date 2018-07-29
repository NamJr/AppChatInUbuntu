package com.example.win81version2.todolist.rest;

import com.example.win81version2.todolist.model.POJO;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * chua cac apis trong du an
 */
public interface APIs {

    //xay dung api de lay contact
    @GET("37f383ae4d51160d55eb")
    Call<POJO> getContacts();

}
