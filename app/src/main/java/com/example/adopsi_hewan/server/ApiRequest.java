package com.example.adopsi_hewan.server;


import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.model.DataModel_register;
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiRequest {


    @POST("Register_nis.php")
    Call<DataModel_register> get_register_nis(@Query("nis") String nik,
                                              @Query("password") String password); // view berdasarkan ID

    @GET("data_materi.php")
    Call<Response_hewan> Get_data_hewan();

    @FormUrlEncoded
    @POST("updatehewan.php")
    Call<BaseResponse> Update_hewan(
            @Field("id") String id,
            @Field("status") String status);

    @GET("data_materi_admin.php")
    Call<Response_hewan> Get_data_hewan_admin(
        @Field("status") String status);

}


