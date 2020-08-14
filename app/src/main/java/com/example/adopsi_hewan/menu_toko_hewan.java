package com.example.adopsi_hewan;

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

import com.example.adopsi_hewan.adapter.adapter_tampil_toko;
import com.example.adopsi_hewan.model.toko.Response_toko;
import com.example.adopsi_hewan.model.toko.ResultItem_toko;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_toko_hewan extends AppCompatActivity {
    private List<ResultItem_toko> data = new ArrayList<>();
    adapter_tampil_toko adapter;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mManager,manager;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    ProgressDialog Pd;
    public static final String session_status = "session_status";
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_toko_hewan);
        ButterKnife.bind(this);
        Pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.rv);



        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);






        init_get_laporan_baru_saya();



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_laporan_selesai();
            }
        });
    }
    public void get_laporan_selesai() {

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();



        Call<Response_toko> call =  api.toko("-1.618345","103.628127");

        call.enqueue(new Callback<Response_toko>() {
            @Override
            public void onResponse(Call<Response_toko> call, Response<Response_toko> response) {

                try {
                    Pd.hide();
                    ///  Log.d("RETRO", "RESPONSE : " + response.body().get());
                    data = response.body().getResult();
                    adapter = new adapter_tampil_toko(menu_toko_hewan.this,data,"status");
                    mRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.i("idade", "onResponse: "+data);

                    if (data.size()==0){
                        Toast.makeText(menu_toko_hewan.this, "Data tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                    else {

                    }
                } catch (Exception e) {
                    Log.e("onResponse", "There is an error"+e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_toko> call, Throwable t) {
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
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;

        }
        else {
            connectStatus = false;
            // Display message in dialog box if you have internet connection
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Internet Connection Available");
            alertDialogBuilder.setMessage("Yes, you are online, internet connection available");

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

            startActivity(new Intent(menu_toko_hewan.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}