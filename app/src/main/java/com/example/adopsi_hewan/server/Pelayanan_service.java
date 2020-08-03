package com.example.adopsi_hewan.server;

import com.example.adopsi_hewan.model.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Pelayanan_service {
    //data jambi
    @FormUrlEncoded
    @POST("simpan_data_user.php")
    Call<BaseResponse> insert_data_anda(
            @Field("nik") String nik,
            @Field("password") String password,
            @Field("no_hp") String no_hp,
            @Field("token") String token);

    //data bukan warga jambi
    @FormUrlEncoded
    @POST("simpan_data_hewan.php")
    Call<BaseResponse> insert_data_anda_bukan_warga(
            @Field("nama") String nama,
            @Field("nik") String nik,
            @Field("jenis") String jenis,

            @Field("kelamin") String kelamin,
            @Field("berat") String berat,
            @Field("umur") String umur,
            @Field("vaksin") String vaksin,
            @Field("steril") String steril,
            @Field("keterangan") String keterangan,
            @Field("fotoHewan") String foto);






    @FormUrlEncoded
    @POST("login.php")
    Call<BaseResponse> loginRequest(@Field("nik") String nik,
                                    @Field("password") String password);


    @FormUrlEncoded
    @POST("token_send.php")
    Call<BaseResponse> Send_notif_staff(
            @Field("id_masalah") String id_masalah,
            @Field("judul") String judul
    );

}