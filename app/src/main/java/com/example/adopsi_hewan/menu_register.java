package com.example.adopsi_hewan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adopsi_hewan.R;
import com.example.adopsi_hewan.menu_login;
import com.example.adopsi_hewan.model.DataModel_register;
import com.example.adopsi_hewan.server.ApiRequest;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;



import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class menu_register extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    @BindView(R.id.edit_nis)
    EditText edit_nis;

    @NotEmpty
    @Password(min = 6,message = "Minimal Password 6 Karakter")
    @BindView(R.id.edit_pass)
    EditText edit_pass;

    @NotEmpty
    @ConfirmPassword(message = "Konfiramsi Password Tidak Sama")
    @BindView(R.id.edit_konfir)
    EditText edit_konfir;

    Validator validator;
    String url = "http://192.168.43.14/adopsi/";
    ProgressDialog pd;
    String klik="";
    @BindView(R.id.btn_register)
    Button btn_register;
    private static final String TAG = menu_register.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_register);
        ButterKnife.bind(this);
        pd = new ProgressDialog(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        //   kecamatan();

//

        edit_nis.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.btn_register)
    public void klik_dafarkan() {
        validator.validate();
    }
    public void Register_nik() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiRequest service = retrofit.create(ApiRequest.class);
        Call<DataModel_register> call = service.get_register_nis( edit_nis.getText().toString().trim(),edit_pass.getText().toString().trim());
        call.enqueue(new Callback<DataModel_register>() {
            @Override
            public void onResponse(Call<DataModel_register> call, final retrofit2.Response<DataModel_register> response) {
                try {

                    if (response.body().getNik().equals("0")){
                        pd.setTitle("Verifikasi Data");
                        pd.setMessage("Mohon tunggu sedang memproses...");
                        pd.show();
                        pd.setCancelable(false);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                dialog_register();
                                pd.dismiss();


                            }
                        }, 2000);
                    }
                    else if (response.body().getNik().equals("2")){
                        pd.setTitle("Verifikasi Data");
                        pd.setMessage("Mohon tunggu sedang memproses...");
                        pd.show();
                        pd.setCancelable(false);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                pd.dismiss();
                                dialog_register_duplikat();

                            }
                        }, 2000);
                    }
                    else {
                        pd.setTitle("Verifikasi Data");
                        pd.setMessage("Mohon tunggu sedang memproses...");
                        pd.show();
                        pd.setCancelable(false);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                pd.dismiss();
                                Intent intent = new Intent(menu_register.this, menu_login.class);

                                startActivity(intent);
                            }
                        }, 4000);
                    }

                } catch (Exception e) {
                    Toast.makeText(menu_register.this, "Terjadi kesalahan !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataModel_register> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(Register.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();

            }
        });
    }





    public void dialog_register(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("NIk tidak terdaftar");
        builder.setMessage("NIK anda belum terdaftar DI DATABASE??");


        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(getApplicationContext(), menu_register.class);

                        startActivity(intent);
                    }
                });



        AlertDialog alert = builder.create();
        alert.show();
    }


    public void dialog_register_duplikat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("NIK anda sudah terdaftar silahkan login...");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(menu_register.this, menu_login.class);
        startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(menu_register.this, menu_login.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onValidationSucceeded() {

        Register_nik();


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
