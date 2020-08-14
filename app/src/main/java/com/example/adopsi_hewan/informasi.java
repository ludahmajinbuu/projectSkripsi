package com.example.adopsi_hewan;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.OnClick;

public class informasi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi);
    }

    @OnClick(R.id.Layer_info)
    public void pindah_info1() {
        Intent intent = new Intent(informasi.this, info1.class);

        startActivity(intent);

    }
}
