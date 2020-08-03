package com.example.adopsi_hewan;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.adopsi_hewan.R;
import com.example.adopsi_hewan.app.AppConfig;
import com.example.adopsi_hewan.app.AppController;
import com.example.adopsi_hewan.menu_utama;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menu_login extends AppCompatActivity  implements Validator.ValidationListener {

    ProgressDialog pDialog;
    Button btn_login;
    TextView btn_lupa;
    Intent intent;


    @NotEmpty
    @BindView(R.id.edit_nis)
    EditText edit_nis;



    @NotEmpty
    @BindView(R.id.edit_pass)
    EditText edit_pass;


    int success;
    ConnectivityManager conMgr;
    private static final String TAG = menu_login.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_nis = "nik_ambil";
   public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
  //  public final static String TAG_KELAS = "kelas";
    String status;


    Boolean session = false;
    String id,nama,nis,sts;
    String tag_json_obj = "json_obj_req";
    Validator validator;
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    //initially it is false
    private boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_login);//  sesi();
        ButterKnife.bind(this); validator = new Validator(this);
        validator.setValidationListener(this);


        conMgr =(ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_lupa = (TextView) findViewById(R.id.txt_lupa);
        edit_nis.requestFocus();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
      //  id = sharedpreferences.getString(TAG_ID, null);
        nis = sharedpreferences.getString(TAG_nis, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);
       sts = sharedpreferences.getString(TAG_STATUS, null);

        if (session) {
            Intent intent = new Intent(menu_login.this, menu_utama.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
           // intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_nis, nis);
            intent.putExtra(TAG_NAMA, nama);
            intent.putExtra(TAG_STATUS, sts);
            finish();
            startActivity(intent);
        }

        btn_login.setOnClickListener(new View.OnClickListener()
        {

            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });

//        btn_register.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                intent = new Intent(menu_login.this, menu_register.class);
//                finish();
//                startActivity(intent);
//            }
//        });
        btn_lupa.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                intent = new Intent(menu_login.this, menu_lupa_password.class);
//                finish();
//                startActivity(intent);
            }
        });
        // db = new SQLiteHandler(getApplicationContext());


    }
    @OnClick(R.id.btn_lihat)
    public void lihat() {
        if (status.equals("true")){
            edit_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            status="flase";
            Toast.makeText(this, "status"+status, Toast.LENGTH_SHORT).show();

        }else if (status.equals("false")){
            edit_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            status="true";
        }

    }

    @OnClick(R.id.txt_register)
    public void txt_register() {
        Intent intent = new Intent(menu_login.this, menu_register.class);

        startActivity(intent);

    }

    private void checkLogin(final String nis, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response_kecamatan: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String nis = jObj.getString(TAG_nis);
                       // String id = jObj.getString(TAG_ID);
                        String nama = jObj.getString(TAG_NAMA);
                        String sts = jObj.getString(TAG_STATUS);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session_dtr
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                       // editor.putString(TAG_ID, id);
                        editor.putString(TAG_nis, nis);
                        editor.putString(TAG_NAMA, nama);
                        editor.putString(TAG_STATUS, sts);

                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(menu_login.this, menu_utama.class);
                       // intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_nis, nis);
                        intent.putExtra(TAG_NAMA, nama);
                        intent.putExtra(TAG_STATUS, sts);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nik", nis);
                params.put("pass", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//
//            startActivity(new Intent(menu_login.this, menu_login_utama.class));
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//    @OnClick(R.id.back)
//    void back() {
//        startActivity(new Intent(menu_login.this, menu_login_utama.class));
//        finish();
//
//    }
    @Override
    public void onValidationSucceeded() {

        String nis = edit_nis.getText().toString();
        String password = edit_pass.getText().toString();

        // mengecek kolom yang kosong
        if (nis.trim().length() > 0 && password.trim().length() > 0) {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                checkLogin(nis, password);
            } else {
                Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
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
