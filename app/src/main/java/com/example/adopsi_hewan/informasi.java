package com.example.adopsi_hewan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.adopsi_hewan.adapter.adapter_tampil_berita;
import com.example.adopsi_hewan.model.berita.Response_berita;
import com.example.adopsi_hewan.model.berita.ResultItem_berita;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class informasi extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swifeRefresh)
    SwipeRefreshLayout swifeRefresh;

    private List<ResultItem_berita> data = new ArrayList<>();
    adapter_tampil_berita adapter;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mManager,manager;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    ProgressDialog Pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);
        ButterKnife.bind(this);




        init_get_laporan_baru_saya();



        swifeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init_get_laporan_baru_saya();
            }
        });
    }
    public void get_laporan_selesai() {

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<Response_berita> call  =  api.get_berita();

        call.enqueue(new Callback<Response_berita>() {
            @Override
            public void onResponse(Call<Response_berita> call, Response<Response_berita> response) {

                try {
                    Pd.hide();
                    ///  Log.d("RETRO", "RESPONSE : " + response.body().get());
                    data = response.body().getResult();
                    adapter = new adapter_tampil_berita(informasi.this,data,"status");
                    mRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    swifeRefresh.setRefreshing(false);
                    Log.i("idade", "onResponse: "+data);

                    if (data.size()==0){

                    }
                    else {

                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error"+e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_berita> call, Throwable t) {
                t.printStackTrace();


            }
        });
    }

    public void init_get_laporan_baru_saya() {

        if (checkInternet()){
            Pd = new ProgressDialog(this);
            mRecycler = (RecyclerView) findViewById(R.id.rv);
            mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            manager = new LinearLayoutManager(this);
            mRecycler.setLayoutManager(mManager);
            mRecycler.setHasFixedSize(true);
            get_laporan_selesai();
            Pd.setMessage("Memproses data ...");
            Pd.setCancelable(false);
        }
        else {

            Toast.makeText(this, "Anda membutuhkan koneksi internet untuk mengambil gambar", Toast.LENGTH_SHORT).show();
        }

//            pd.show();

//        get_laporan_baru_saya();

    }


    public boolean checkInternet(){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;

        }
        else {
            connectStatus = false;
            // Display message in dialog box if you have internet connection
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Internet Connection Available");
            alertDialogBuilder.setMessage("Yes, you are online, internet connection available");
          //  Toast.makeText(hewan_baru.this, "Internet Connection Available", Toast.LENGTH_LONG).show();
            alertDialogBuilder.setPositiveButton("Refresh Data", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    init_get_laporan_baru_saya();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return connectStatus;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(informasi.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public  void  onResume() {

        super.onResume();



    }
}
