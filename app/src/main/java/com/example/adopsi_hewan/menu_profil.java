package com.example.adopsi_hewan;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class menu_profil extends AppCompatActivity {

    public static final String session_status = "session_status";

    Boolean session = false;

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status,nik,nama;

    SharedPreferences sharedpreferences;



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



        Glide.with(this)
                .load("http://192.168.56.1/adopsi/potopro/" + tampung)
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
}
