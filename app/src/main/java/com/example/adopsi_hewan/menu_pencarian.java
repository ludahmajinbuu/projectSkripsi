package com.example.adopsi_hewan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.adopsi_hewan.adapter.adapter_tampil_data;
import com.example.adopsi_hewan.model.model_tampil_hewan.Response_hewan_new;
import com.example.adopsi_hewan.model.model_tampil_hewan.ResultItem_hewan_new;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_pencarian extends AppCompatActivity {

    @BindView(R.id.cari_data)
    SearchView cariData;
    ProgressDialog pd;
    @BindView(R.id.rv)
    RecyclerView rv;
    private List<ResultItem_hewan_new> data = new ArrayList<>();
    adapter_tampil_data adapter;

    private RecyclerView.LayoutManager mManager, manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_pencarian);
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        cariData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                cariData.clearFocus();

                unit_cari();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                unit_cari();


                return false;
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(menu_pencarian.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void cari() {
        pd.setMessage("Mecari data ...");
        pd.setCancelable(true);
        pd.show();
        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();



        Call<Response_hewan_new> call =  api.cari_hewan(String.valueOf(cariData.getQuery()));



        call.enqueue(new Callback<Response_hewan_new>() {
            @Override
            public void onResponse(Call<Response_hewan_new> call, Response<Response_hewan_new> response) {

                try {
                    pd.hide();
                    ///  Log.d("RETRO", "RESPONSE : " + response.body().get());
                    data = response.body().getResult();
                    adapter = new adapter_tampil_data(menu_pencarian.this,data,"status");
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //mSwipeRefreshLayout.setRefreshing(false);
                    Log.i("idade", "onResponse: "+data);

                    if (data.size()==0){
                        Toast.makeText(menu_pencarian.this, "Data tidak Ada", Toast.LENGTH_SHORT).show();

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

    public void unit_cari() {


        rv = (RecyclerView) findViewById(R.id.rv);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(mManager);
        rv.setHasFixedSize(true);
        cari();

//            pd.show();


    }
}