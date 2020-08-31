package com.example.adopsi_hewan.server;


import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.model.DataModel_register;
import com.example.adopsi_hewan.model.ResponsModel;
import com.example.adopsi_hewan.model.berita.Response_berita;
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan;
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan_new;
import com.example.adopsi_hewan.model.profil.Response_profil;
import com.example.adopsi_hewan.model.toko.Response_toko;

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
    Call<Response_hewan_new> Get_data_hewan();

    @GET("get_data_hewan_suskes_adopsi.php")
    Call<Response_hewan_new> get_data_hewan_suskes_adopsi();

    @GET("get_data_hewan_verifikasi_admin.php")
    Call<Response_hewan_new> get_data_hewan_verifikasi_admin();

    @GET("berita.php")
    Call<Response_berita> get_berita();

    @GET("toko.php")
    Call<Response_toko> toko(@Query("lat") String lat,
                             @Query("lng") String lng);


    @FormUrlEncoded
    @POST("cari_hewan.php")
    Call<Response_hewan_new> cari_hewan(
            @Field("search") String search);

    @FormUrlEncoded
    @POST("update_pass.php")
    Call<ResponsModel> lupa_password(
            @Field("nik") String nik,
            @Field("password") String password,
            @Field("tgl_lahir") String tgl_lahir);

    @FormUrlEncoded
    @POST("up_no.php")
    Call<ResponsModel> up_no(
            @Field("nik") String nik,
            @Field("nohp") String nohp);

    @FormUrlEncoded
    @POST("edit_email.php")
    Call<ResponsModel> edit_email(
            @Field("nik") String nik,
            @Field("nohp") String nohp);

    @FormUrlEncoded
    @POST("hapus_hewan.php")
    Call<ResponsModel> hapus_hewan(
            @Field("id") String id);

    @GET("profil.php")
    Call<Response_profil> profil(@Query("nik") String nik);


    @GET("get_data_hewan_verifikasi_pemilik_hewan.php")
    Call<Response_hewan_new> get_data_hewan_verifikasi_pemilik_hewan(@Query("nik") String nik);

    @FormUrlEncoded
    @POST("updatehewan.php")
    Call<BaseResponse> Update_hewan(
            @Field("id") String id,
            @Field("idHewan") String idHewan,
            @Field("idUser") String idUser,
            @Field("status") String status);



    @FormUrlEncoded
    @POST("setuju.php")
    Call<BaseResponse> setuju(
            @Field("idHewan") String idHewan);

    @FormUrlEncoded
    @POST("alasan.php")
    Call<BaseResponse> alasan(
            @Field("id_pemohon") String id_pemohon,
            @Field("id_hewan") String id_hewan,
            @Field("alasan") String alasan);


    @FormUrlEncoded
    @POST("tidak_setuju.php")
    Call<BaseResponse> tidak_setuju(
            @Field("idHewan") String idHewan,
            @Field("idAdopsi") String idAdopsi,
            @Field("id_alasan") String id_alasan);


    ;

    @GET("data_materi_admin.php")
    Call<Response_hewan> Get_data_hewan_admin(
        @Field("status") String status);

}


