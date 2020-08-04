 package com.example.adopsi_hewan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;

 public class menu_utama extends AppCompatActivity {

     public static final String session_status = "session_status";

     Boolean session = false;



    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status,nik,nama;

    SharedPreferences sharedpreferences;

    @BindView(R.id.txtNIKTampil)
    TextView txtNIKTampil;

     @BindView(R.id.txtnamaTmpil)
     TextView txtnamaTmpil;

     @BindView(R.id.btnTmbhL)
     RelativeLayout btnTmbhL;


     @BindView(R.id.btn_veri)
     LinearLayout btn_veri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }


     @OnClick(R.id.Layer_verifi)
     public void pindah() {
         Intent i=new Intent(menu_utama.this, hewan_baru.class);
         i.putExtra("status","0");
         startActivity(i);


     }


     @OnClick(R.id.btnCariL)
     public void btnCariL() {
         Intent intent = new Intent(menu_utama.this, menu_pencarian.class);

         startActivity(intent);

     }

     @OnClick(R.id.imgMShop)
     public void imgMShop() {
         Intent intent = new Intent(menu_utama.this, menu_toko_hewan.class);

         startActivity(intent);

     }

     @OnClick(R.id.btnTmbhL)
         public void txt_register() {
             Intent intent = new Intent(menu_utama.this, tambahHewa1.class);

             startActivity(intent);

     }

     @OnClick(R.id.btnBruL)
     public void pindah3() {
         Intent i=new Intent(menu_utama.this, hewan_baru.class);
         i.putExtra("status","1");
         startActivity(i);

     }

     @OnClick(R.id.btn_hewan_adopsi)
     public void btn_hewan_adopsi() {
         Intent i=new Intent(menu_utama.this, hewan_baru.class);
         i.putExtra("status","2");
         startActivity(i);

     }
     @OnClick(R.id.btn_sukses)
     public void btn_sukses() {
         Intent i=new Intent(menu_utama.this, hewan_baru.class);
         i.putExtra("status","3");
         startActivity(i);

     }


     @OnClick(R.id.btnprofil)
     public void pindah4() {
         Intent intent = new Intent(menu_utama.this, menu_profil.class);

         startActivity(intent);

     }



     public  void  onResume() {

         super.onResume();
         // cek_berita();
         sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
         session = sharedpreferences.getBoolean(session_status, false);
         status = sharedpreferences.getString(TAG_STATUS, null);
         nik = sharedpreferences.getString(TAG_nis, null);
         nama = sharedpreferences.getString(TAG_NAMA, null);

         txtNIKTampil.setText("nik : "+nik);
         txtnamaTmpil.setText(nama);

         ButterKnife.bind(this);

         if (status.equals("user")){
             btn_veri.setVisibility(View.GONE);
         }else {
             btn_veri.setVisibility(View.VISIBLE);
         }
     }
}
