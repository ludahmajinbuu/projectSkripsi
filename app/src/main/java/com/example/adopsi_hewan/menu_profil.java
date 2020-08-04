package com.example.adopsi_hewan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.adopsi_hewan.model.profil.Response_profil;
import com.example.adopsi_hewan.model.profil.ResultItem_profil;
import com.example.adopsi_hewan.server.ApiRequest;

import java.util.ArrayList;
import java.util.List;

public class menu_profil extends AppCompatActivity {

    public static final String session_status = "session_status";

    Boolean session = false;

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status,nik,nama;

    SharedPreferences sharedpreferences;

    private List<ResultItem_profil> data = new ArrayList<ResultItem_profil>();

    @BindView(R.id.txtNIKPro)
    TextView txtNIKPro;

    @BindView(R.id.txtNmaPro)
    TextView txtNmaPro;

    @BindView(R.id.txtHPPro)
    TextView txtHPPro;

    @BindView(R.id.txtEmaPro)
    TextView txtEmaPro;

    @BindView(R.id.txtKelPro)
    TextView txtKelPro;

    @BindView(R.id.txtAlaPro)
    TextView txtAlaPro;

    @BindView(R.id.txtStsPro)
    TextView txtStsPro;

    @BindView(R.id.txtKrjaPro)
    TextView txtKrjaPro;

    @BindView(R.id.imgProPhto)
    ImageView imgProPhto;



    String id,status_kirim;

    String tampung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profil);

        ButterKnife.bind(this);


        GET_profil();
        Glide.with(this)
                .load("http://192.168.43.14/adopsi/potopro/" + tampung)
                .centerCrop()
                .into(imgProPhto);

    }

    public  void  onResume() {


        super.onResume();
        // cek_berita();
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        status = sharedpreferences.getString(TAG_STATUS, null);
        nik = sharedpreferences.getString(TAG_nis, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);
        GET_profil();


    }


    @OnClick(R.id.txtLogout)
    public void pindah() {
        SweetAlertDialog pDialog = new SweetAlertDialog(menu_profil.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText("Apakah anda yakin ingin keluar ?");
        pDialog.setCancelable(false);
        pDialog.setConfirmText("Ya");
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                //  String nik = txt_nik.getText().toString();

                //    matikan_notif();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(menu_login.session_status, false);
                editor.putString(TAG_nis, null);
                editor.putString(TAG_NAMA, null);
                editor.commit();

                Intent intent = new Intent(menu_profil.this, menu_login.class);
                finish();
                startActivity(intent);
                // TODO Auto-generated method stub
                // update login session ke FALSE dan mengosongkan nilai id dan username
            }
        });
        pDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();


    }

    public void GET_profil() {

//        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
//        // Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
//
//
//
//        Call<Response_profil> call =  api.profil("123");
//
//
//
//        call.enqueue(new Callback<Response_profil>() {
//            @Override
//            public void onResponse(Call<Response_profil> call, Response<Response_profil> response) {
//
//                try {
//
//
//                    data = response.body().getResult();
//
//
//
//                    if (data.size()==0){
//
//                    }
//                    else {
//
//                    }
//                } catch (Exception e) {
//                    Log.e("onResponse", "There is an error"+e);
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Response_profil> call, Throwable t) {
//                t.printStackTrace();
//
//
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.14/adopsi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        Call<Response_profil> call = service.profil(nik);

        call.enqueue(new Callback<Response_profil>() {
            @Override
            public void onResponse(Call<Response_profil> call, Response<Response_profil> response) {

                try {
                    data = response.body().getResult();

                    if (data.size()==0){

                    }else {




//                        txt_nama.setVisibility(View.VISIBLE);
//                        txt_email.setVisibility(View.VISIBLE);
//                        txt_opd.setVisibility(View.VISIBLE);
//                        txt_username.setVisibility(View.VISIBLE);

                        for (int i = 0; i < data.size(); i++) {
                            Toast.makeText(menu_profil.this, ""+data.get(i).getAlamat(), Toast.LENGTH_SHORT).show();
                            txtAlaPro.setText(data.get(i).getAlamat());
                            txtEmaPro.setText(data.get(i).getEmail());
                            txtHPPro.setText(data.get(i).getNohp());
                            txtNmaPro.setText(data.get(i).getNama());
                            txtNIKPro.setText(data.get(i).getNik());
                            txtStsPro.setText(data.get(i).getStatusKwn());
                            txtKrjaPro.setText(data.get(i).getPekerjaan());
                            txtKelPro.setText(data.get(i).getJk());



//                            txt_nama.setText("" + data.get(i).getNama());
//                            txt_email.setText(""+data.get(i).getEmail());
//                            foto=data.get(i).getFoto();
//                            txt_opd.setText(""+data.get(i).getNamaOpd());
//                            txt_username.setText(""+data.get(i).getUsername());
                            Glide.with(menu_profil.this)
                                    .load("http://192.168.43.14/adopsi/gambar/"+data.get(i).getFoto())
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        //    progres_foto.setVisibility(View.GONE);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                          //  progres_foto.setVisibility(View.GONE);
                                            return false;
                                        }
                                    })
                                    .circleCrop()
                                    .error(R.drawable.error_circle)
                                    .into(imgProPhto);
                        }
                    }



                    //  txt_alamat.setText("Kecamatann "+kec+" Kelurahan "+kel+" "+" Alamat "+alamat+" Rt "+rt);


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
