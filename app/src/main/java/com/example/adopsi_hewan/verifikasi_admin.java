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
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan_new;
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewan_new;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

public class verifikasi_admin extends AppCompatActivity {

    private List<ResultItem_hewan_new> data = new ArrayList<>();
    adapter_tampil_data adapter;


    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mManager,manager;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    String id,nik,nama;
    ProgressDialog Pd;
    public static final String session_status = "session_status";
    SwipeRefreshLayout mSwipeRefreshLayout;
    Boolean session = false;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_admin);
        ButterKnife.bind(this);
        Pd = new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        mRecycler = (RecyclerView) findViewById(R.id.rv);
        Log.i("id_pemohon"+id, "onCreate: ");

        Toast.makeText(this, "ID"+id, Toast.LENGTH_SHORT).show();
        //card_tidak_ada_data.setVisibility(View.GONE);
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
        Call<Response_hewan_new> call = api.Get_data_hewan();


        call.enqueue(new Callback<Response_hewan_new>() {
            @Override
            public void onResponse(Call<Response_hewan_new> call, Response<Response_hewan_new> response) {

                try {
                    Pd.hide();
                    ///  Log.d("RETRO", "RESPONSE : " + response.body().get());
                    data = response.body().getResult();
                    adapter = new adapter_tampil_data(verifikasi_admin.this,data,status);
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
            public void onFailure(Call<Response_hewan_new> call, Throwable t) {
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



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(verifikasi_admin.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    class chekinternet {
        public  String getConnectivityStatusString(Context context) {
            String status = null;
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    status = "Wifi enabled";
                    return status;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    status = "Mobile data enabled";
                    return status;
                }
            } else {
                status = "No internet is available";


                return status;
            }
            return status;
        }

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
            alertDialogBuilder.setTitle("Internet Connection Not Available");
            alertDialogBuilder.setMessage("You are not online, internet connection not available");
            Toast.makeText(verifikasi_admin.this, "Internet Connection Available", Toast.LENGTH_LONG).show();
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
