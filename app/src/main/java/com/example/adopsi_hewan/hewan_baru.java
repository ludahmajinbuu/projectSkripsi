package com.example.adopsi_hewan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

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

import com.example.adopsi_hewan.adapter.adapter_tampil_data;
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan;
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewn;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

public class hewan_baru extends AppCompatActivity {

    private List<ResultItem_hewn> data = new ArrayList<>();
    adapter_tampil_data adapter;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mManager,manager;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    ProgressDialog Pd;
    public static final String session_status = "session_status";
    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean session = false;

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    String id,nik,nama;

    String status,staus_hewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hewan_baru);

        ButterKnife.bind(this);
        Pd = new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        mRecycler = (RecyclerView) findViewById(R.id.rv);
        Log.i("id_pemohon"+id, "onCreate: ");

        Toast.makeText(this, "ID"+id, Toast.LENGTH_SHORT).show();
        //card_tidak_ada_data.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swifeRefresh);

        Intent i=this.getIntent();
        staus_hewan=i.getExtras().getString("status");
        Toast.makeText(this, ""+staus_hewan, Toast.LENGTH_SHORT).show();



        init_get_laporan_baru_saya();



        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                init_get_laporan_baru_saya();
            }
        });
    }

    public void get_laporan_selesai() {

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();



        Call<Response_hewan> call = null;

        if (staus_hewan.equals("1")){
            call =  api.Get_data_hewan();
        }else if (staus_hewan.equals("2")){
            call =  api.get_data_hewan_verifikasi_pemilik_hewan();
        }else if (staus_hewan.equals("3")){
            call =  api.get_data_hewan_suskes_adopsi();
        }else {
            call =  api.get_data_hewan_verifikasi_admin();
        }


        call.enqueue(new Callback<Response_hewan>() {
            @Override
            public void onResponse(Call<Response_hewan> call, Response<Response_hewan> response) {

                try {
                    Pd.hide();
                    ///  Log.d("RETRO", "RESPONSE : " + response.body().get());
                    data = response.body().getResult();
                    adapter = new adapter_tampil_data(hewan_baru.this,data,status);
                    mRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
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
            public void onFailure(Call<Response_hewan> call, Throwable t) {
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
            Toast.makeText(hewan_baru.this, "Internet Connection Available", Toast.LENGTH_LONG).show();
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

            startActivity(new Intent(hewan_baru.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public  void  onResume() {

        super.onResume();
        // cek_berita();
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        status = sharedpreferences.getString(TAG_STATUS, null);
        nik = sharedpreferences.getString(TAG_nis, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);



    }

}
