package com.example.adopsi_hewan.server;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retroserver {
    private  static  final String base_url = "http://192.168.43.109/adopsi/";
      public static  final String base_url_image_before = "http://192.168.43.109/adopsi/images/before/";
    public static  final String base_url_image_after = "http://192.168.43.109/adopsi/images/after/";
    public static  final String base_url_image_user = "http://192.168.43.109/adopsi/images/user/";
    public static  final String url_register = "http://192.168.43.109/adopsi/register.php";
    public static  final String url_kirim_laporan_user = "http://192.168.43.109/adopsi/kirim_laporan.php";
    public static  final String url_login = "http://192.168.43.109/adopsi/Login.php";

    private static Retrofit retrofit;
    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    }


}
