package com.example.adopsi_hewan;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class menu_detail_informasi extends AppCompatActivity {

    @BindView(R.id.txt_isi)
    TextView txtIsi;
    @BindView(R.id.txt_tgl)
    TextView txtTgl;
    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.txt_judul)
    TextView txtJudul;
    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_detail_informasi);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        txtIsi.setText(extras.getString("isi"));
        txtTgl.setText(extras.getString("tgl"));
        txtJudul.setText(extras.getString("judul"));



        Glide.with(this)
                .load("http://192.168.43.109/adopsi/gambar/" + extras.getString("foto"))
                .centerCrop()
                .into(bgImg);

    }
}