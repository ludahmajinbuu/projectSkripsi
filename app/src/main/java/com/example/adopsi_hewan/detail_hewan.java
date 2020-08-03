package com.example.adopsi_hewan;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;


public class detail_hewan extends AppCompatActivity {


    public static final String session_status = "session_status";

    Boolean session = false;

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status,nik,nama;

    SharedPreferences sharedpreferences;

    @BindView(R.id.txtnmaDtlHwan)
    TextView txtnmaDtlHwan;

    @BindView(R.id.txtUmurHwanDtl)
    TextView txtUmurHwanDtl;

    @BindView(R.id.txtJnsHwanDtl)
    TextView txtJnsHwanDtl;

    @BindView(R.id.txtJKHewanDtl)
    TextView txtJKHewanDtl;

    @BindView(R.id.txtBrtHwanDtl)
    TextView txtBrtHwanDtl;

    @BindView(R.id.txtStrlHwanDtl)
    TextView txtStrlHwanDtl;

    @BindView(R.id.txtVksinHwanDtl)
    TextView txtVksinHwanDtl;

    @BindView(R.id.txtKtrHwanDtl)
    TextView txtKtrHwanDtl;

    @BindView(R.id.imgHewandetail)
    ImageView imgHewandetail;

    @BindView(R.id.btn_adop)
    Button btn_adop;

    @BindView(R.id.btn_order)
    Button btn_order;

    String id,status_kirim;

    String tampung,status_hewan,status_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hewan);

        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        txtnmaDtlHwan.setText(extras.getString("nama"));
        txtBrtHwanDtl.setText(extras.getString("berat"));
        txtUmurHwanDtl.setText(extras.getString("umur"));
        txtJKHewanDtl.setText(extras.getString("kelamin"));
        txtJnsHwanDtl.setText(extras.getString("jenis"));
        txtStrlHwanDtl.setText(extras.getString("steril"));
        txtVksinHwanDtl.setText(extras.getString("vaksin"));
        txtKtrHwanDtl.setText(extras.getString("keterangan"));
        status_hewan=extras.getString("status");

        tampung = extras.getString("foto");
        id = extras.getString("id");
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        nik = sharedpreferences.getString(TAG_nis, null);
        status_user = sharedpreferences.getString(TAG_STATUS, null);
        Glide.with(this)
                .load("http://192.168.56.1/adopsi/gambar/" + tampung)
                .centerCrop()
                .into(imgHewandetail);

        if (status_hewan.equals("2")){
            btn_adop.setText("SETUJUI");
        }else if (status_hewan.equals("1")){
            btn_adop.setText("ADOPSI");
        }else if (status_hewan.equals("0")){
            btn_adop.setText("SETUJUI");
        }else {
            btn_adop.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.btn_adop)
    public void order() {
        updatepass();
      //  Toast.makeText(this, "permohonan adopsi berhasil dikirim", Toast.LENGTH_SHORT).show();

    }

    void updatepass() {


        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<BaseResponse> update2 = null;

        if (status_user.equals("admin")){
            update2=api2.Update_hewan(
                    id,
                    id,
                    nik,
                    "1");
        }else {
            update2=api2.Update_hewan(
                    id,
                    id,
                    nik,
                    "2");
        }





        update2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("Retro", "Response_tes");
                Toast.makeText(detail_hewan.this, "kode"+response.body().getKode(), Toast.LENGTH_SHORT).show();
                if (response.body().getKode().equals("0")) {
                    Toast.makeText(detail_hewan.this, "permohonan adopsi terkirim", Toast.LENGTH_SHORT).show();


                }
                if (response.body().getKode().equals("1")) {

                    // Toast.makeText(menu_profil.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(detail_hewan.this, "gagal kirim permohonan", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                // Pd.hide();
                Log.d("Retro", "OnFailure");
                Toast.makeText(detail_hewan.this,"gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  void  onResume() {


        super.onResume();
        // cek_berita();
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        status = sharedpreferences.getString(TAG_STATUS, null);
        nik = sharedpreferences.getString(TAG_nis, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);

 if (status.equals("admin")){

    btn_order.setVisibility(View.GONE);

    status_kirim="1";
}

        ButterKnife.bind(this);
    }
}
