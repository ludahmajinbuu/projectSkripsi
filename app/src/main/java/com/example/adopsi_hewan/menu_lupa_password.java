package com.example.adopsi_hewan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adopsi_hewan.model.ResponsModel;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Retroserver;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class menu_lupa_password extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    @BindView(R.id.edit_tahun)
    EditText editTahun;

    @NotEmpty
    @BindView(R.id.edit_bln)
    EditText editBln;

    @NotEmpty
    @BindView(R.id.edit_tgl)
    EditText editTgl;

    @NotEmpty
    @ConfirmPassword(message = "Konfiramsi Password Tidak Sama")
    @BindView(R.id.edit_konfir)
    EditText editKonfir;

    @NotEmpty
    @BindView(R.id.edit_nis)
    EditText editNis;

    @NotEmpty
    @Password(min = 6,message = "Minimal Password 6 Karakter")
    @BindView(R.id.edit_pass)
    EditText editPass;
    @BindView(R.id.btn_register)
    Button btnRegister;
    Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_lupa_password);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
    }
    void check_validasi_reset_pass() {

        String nik = editNis.getText().toString().trim();
        String tgl= editTgl.getText().toString().trim();
        String bulan= editBln.getText().toString().trim();
        String tahun= editTahun.getText().toString().trim();
        String password= editPass.getText().toString().trim();
        if (bulan.length()<2){
            bulan= "0"+bulan;
        }
        if (tgl.length()<2){
            tgl= "0"+tgl;
        }
        String tgl_lahir = tahun+"-"+bulan+"-"+tgl;

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsModel> sendbio = api.lupa_password(nik,password,tgl_lahir);
        sendbio.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {

                String kode = response.body().getKode();

                if (kode.equals("1")){

                    Toast.makeText(menu_lupa_password.this, "Berhasil Edit Password", Toast.LENGTH_SHORT).show();
                }
                else if (kode.equals("0")){
                    Toast.makeText(menu_lupa_password.this, "NIK ATAU TANGGAL LAHIR SALAH", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                t.printStackTrace();
                Log.i("cek", "onFailure: "+t);
                if (t instanceof IOException) {

                    // pDialog.dismiss();
                    //Toast.makeText(ErrorHandlingActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                    Toast.makeText(menu_lupa_password.this, "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();

                }
                else {
                    // pDialog.dismiss();
                    //  Toast.makeText(ErrorHandlingActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                    Toast.makeText(menu_lupa_password.this, "Jaringan Anda Bermasalah", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @OnClick(R.id.btn_register)
    void spin5() {
        validator.validate();

    }

    @Override
    public void onValidationSucceeded() {
        check_validasi_reset_pass();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }

    }
}