package com.example.adopsi_hewan;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class edit_profil extends AppCompatActivity {

    @BindView(R.id.txtnikEdt)
    EditText txtnikEdt;

    @BindView(R.id.txtnmaEdt)
    EditText txtnmaEdt;

    @BindView(R.id.txthpEdt)
    EditText txthpEdt;

    @BindView(R.id.txtemaEdt)
    EditText txtemaEdt;

    @BindView(R.id.txtklmnEdt)
    EditText txtklmnEdt;

    @BindView(R.id.txtalamEdt)
    EditText txtalamEdt;

    @BindView(R.id.txtsttsEdt)
    EditText txtsttsEdt;

    @BindView(R.id.txtkrjaEdt)
    EditText txtkrjaEdt;


    @BindView(R.id.imguserEdt)
    ImageView imguserEdt;

    @BindView(R.id.btnuserEdt)
    Button btnuserEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
    }
}
