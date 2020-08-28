package com.example.adopsi_hewan;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.model.profil.Response_profil;
import com.example.adopsi_hewan.model.profil.ResultItem_profil;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class detail_hewan extends AppCompatActivity {


    public static final String session_status = "session_status";
    private List<ResultItem_profil> data = new ArrayList<ResultItem_profil>();
    Boolean session = false;
    EditText alasan;
    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status, nik, nama;
    String nik_pemilik_hewan;
    Dialog dialog;
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

    @BindView(R.id.btn_tidak)
    Button btn_tidak;

    String id, status_kirim, id_alasan, id_adopsi;

    String tampung, status_hewan, status_user;
    @BindView(R.id.txt_alasan)
    TextView txtAlasan;
    @BindView(R.id.btn_panggil)
    Button btnPanggil;
    String phone ;
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
        status_hewan = extras.getString("status");
        id_alasan = extras.getString("id_alasan");
        id_adopsi = extras.getString("id_adopsi");
        txtAlasan.setText("Alasan Adopsi " + extras.getString("alasan"));
      nik_pemilik_hewan= extras.getString("nik");


        tampung = extras.getString("foto");
        id = extras.getString("id");
        Log.i("isi_id", "onCreate: " + id + " " + id_alasan + " " + id_adopsi);
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        nik = sharedpreferences.getString(TAG_nis, null);
        status_user = sharedpreferences.getString(TAG_STATUS, null);
        Glide.with(this)
                .load("http://192.168.43.14/adopsi/gambar/" + tampung)
                .centerCrop()
                .into(imgHewandetail);

        if (status_hewan.equals("2")) {
            btn_adop.setText("SETUJUI");
            btnPanggil.setVisibility(View.GONE);
        } else if (status_hewan.equals("1")) {
            txtAlasan.setVisibility(View.GONE);
            btnPanggil.setVisibility(View.GONE);
            btn_tidak.setVisibility(View.GONE);
            btn_adop.setText("ADOPSI");
        } else if (status_hewan.equals("0")) {
            btnPanggil.setVisibility(View.GONE);
            txtAlasan.setVisibility(View.GONE);
            btn_tidak.setVisibility(View.GONE);
            btn_adop.setText("SETUJUI");
        } else {
            txtAlasan.setVisibility(View.GONE);
            btn_tidak.setVisibility(View.GONE);
            btn_adop.setVisibility(View.GONE);
        }
        GET_profil();

    }

    @OnClick(R.id.btn_tidak)
    public void btn_tidak() {

        tidak_Setuju();


    }

    @OnClick(R.id.btn_adop)
    public void order() {
        if (status_hewan.equals("1")) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_tambah_materi);
            dialog.setCancelable(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            dialog.getWindow().setAttributes(lp);
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.getWindow().setDimAmount(0.5f);


            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


            ImageView close = (ImageView) dialog.findViewById(R.id.btn_close);
            TextView judul = (TextView) dialog.findViewById(R.id.txt_judul);
            alasan = (EditText) dialog.findViewById(R.id.edit_alasan);

            judul.setText("Tambah Materi");

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_simpan);

            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the popup window
                    alasan();
                }
            });


            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the popup window
                    dialog.dismiss();
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            setuju();
        }


        //
        //  Toast.makeText(this, "permohonan adopsi berhasil dikirim", Toast.LENGTH_SHORT).show();

    }

    void alasan() {
        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<BaseResponse> update2 = api2.alasan(
                nik,
                id,
                alasan.getText().toString());


        update2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("Retro", "Response_tes");
                //Toast.makeText(detail_hewan.this, "kode"+response.body().getKode(), Toast.LENGTH_SHORT).show();
                if (response.body().getKode().equals("0")) {
                    // Toast.makeText(detail_hewan.this, "permohonan adopsi terkirim", Toast.LENGTH_SHORT).show();
                    updatepass();

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
                Toast.makeText(detail_hewan.this, "gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void tidak_Setuju() {
        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<BaseResponse> update2 = api2.tidak_setuju(
                id,
                id_adopsi,
                id_alasan);


        update2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("Retro", "Response_tes");
                //Toast.makeText(detail_hewan.this, "kode"+response.body().getKode(), Toast.LENGTH_SHORT).show();
                if (response.body().getKode().equals("0")) {
                    Toast.makeText(detail_hewan.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    btn_adop.setVisibility(View.GONE);
                    btn_tidak.setVisibility(View.GONE);
                    // updatepass();

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
                Toast.makeText(detail_hewan.this, "gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setuju() {


        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<BaseResponse> update2 = api2.setuju(id);


        update2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("Retro", "Response_tes");
                //Toast.makeText(detail_hewan.this, "kode"+response.body().getKode(), Toast.LENGTH_SHORT).show();
                if (response.body().getKode().equals("0")) {
                    btn_adop.setVisibility(View.GONE);

//                    Intent intent = new Intent(detail_hewan.this, hewan_baru.class);
//
//                    startActivity(intent);
                    Toast.makeText(detail_hewan.this, "permohonan adopsi terkirim", Toast.LENGTH_LONG).show();


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
                Toast.makeText(detail_hewan.this, "gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void updatepass() {


        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<BaseResponse> update2 = null;

        if (status_user.equals("admin")) {
            update2 = api2.Update_hewan(
                    id,
                    id,
                    nik,
                    "1");
        } else {
            update2 = api2.Update_hewan(
                    id,
                    id,
                    nik,
                    "2");
        }


        update2.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("Retro", "Response_tes");
                //Toast.makeText(detail_hewan.this, "kode"+response.body().getKode(), Toast.LENGTH_SHORT).show();
                if (response.body().getKode().equals("0")) {
                    dialog.dismiss();
                    btn_adop.setVisibility(View.GONE);

//                    Intent intent = new Intent(detail_hewan.this, hewan_baru.class);
//
//                    startActivity(intent);
                    Toast.makeText(detail_hewan.this, "permohonan adopsi terkirim", Toast.LENGTH_LONG).show();


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
                Toast.makeText(detail_hewan.this, "gagal update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onResume() {


        super.onResume();
        // cek_berita();
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        status = sharedpreferences.getString(TAG_STATUS, null);
        nik = sharedpreferences.getString(TAG_nis, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);

        if (status.equals("admin")) {

            btn_tidak.setVisibility(View.GONE);

            status_kirim = "1";
        }

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_panggil)
    public void onViewClicked() {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void GET_profil() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.14/adopsi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        Call<Response_profil> call = service.profil(nik_pemilik_hewan);

        call.enqueue(new Callback<Response_profil>() {
            @Override
            public void onResponse(Call<Response_profil> call, Response<Response_profil> response) {

                try {
                    data = response.body().getResult();

                    if (data.size()==0){

                    }else {

                        for (int i = 0; i < data.size(); i++) {

                            phone=data.get(i).getAlamat();
                        }
                    }



                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Response_profil> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }
}
