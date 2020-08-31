package com.example.adopsi_hewan;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.model.ResponsModel;
import com.example.adopsi_hewan.model.profil.Response_profil;
import com.example.adopsi_hewan.model.profil.ResultItem_profil;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Pelayanan_service;
import com.example.adopsi_hewan.server.Retroserver;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobsandgeeks.saripaar.Validator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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

public class menu_profil extends AppCompatActivity {

    public static final String session_status = "session_status";

    Boolean session = false;

    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    public static final String my_shared_preferences = "my_shared_preferences";
    String status, nik, nama;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    SharedPreferences sharedpreferences;
    @BindView(R.id.btn_edit)
    TextView btnEdit;
    @BindView(R.id.btn_edit_no)
    ImageView btnEditNo;
    @BindView(R.id.btn_eidt_email)
    ImageView btnEidtEmail;
    public final int SELECT_FILE = 1;
    private List<ResultItem_profil> data = new ArrayList<ResultItem_profil>();
    private static String imageStoragePath;
    Bitmap bitmap, decoded;
    String token;
    String ud;
    ProgressDialog pd;
    Validator validator;
    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;

    private String path;
    SweetAlertDialog pd_new;
    String image = null;

    @BindView(R.id.txtNIKPro)
    TextView txtNIKPro;
    BottomSheetDialog dialog;
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


    String id, status_kirim;

    String tampung;

    EditText no_hp,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profil);

        ButterKnife.bind(this);


        GET_profil();
        Glide.with(this)
                .load("http://192.168.43.109/adopsi/potopro/" + tampung)
                .centerCrop()
                .into(imgProPhto);

    }

    public void onResume() {


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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.109/adopsi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest service = retrofit.create(ApiRequest.class);

        Call<Response_profil> call = service.profil(nik);

        call.enqueue(new Callback<Response_profil>() {
            @Override
            public void onResponse(Call<Response_profil> call, Response<Response_profil> response) {

                try {
                    data = response.body().getResult();

                    if (data.size() == 0) {

                    } else {


                        for (int i = 0; i < data.size(); i++) {
                            Toast.makeText(menu_profil.this, "" + data.get(i).getAlamat(), Toast.LENGTH_SHORT).show();
                            txtAlaPro.setText(data.get(i).getAlamat());
                            txtEmaPro.setText(data.get(i).getEmail());
                            txtHPPro.setText(data.get(i).getNohp());
                            txtNmaPro.setText(data.get(i).getNama());
                            txtNIKPro.setText(data.get(i).getNik());
                            txtStsPro.setText(data.get(i).getStatusKwn());
                            txtKrjaPro.setText(data.get(i).getPekerjaan());
                            txtKelPro.setText(data.get(i).getJk());


                            Glide.with(menu_profil.this)
                                    .load("http://192.168.43.109/adopsi/gambar/" + data.get(i).getFoto())
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
                                    .error(R.drawable.dog)
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(menu_profil.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btn_edit)
    public void onViewClicked() {
        final CharSequence[] items = {"Galeri",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(menu_profil.this);
        builder.setTitle("Add Foto!");
        builder.setIcon(R.drawable.ic_account_box_black_48dp);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Galeri")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @SuppressLint("MissingSuperCall")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                try {


                    BitmapFactory.Options bounds = new BitmapFactory.Options();
                    bounds.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(imageStoragePath, bounds);

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    Bitmap bm = BitmapFactory.decodeFile(imageStoragePath, opts);
                    ExifInterface exif = new ExifInterface(imageStoragePath);
                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

                    int rotationAngle = 0;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

                    Matrix matrix = new Matrix();
                    matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);


                    //  Bitmap bitmap = CameraUtils.optimizeBitmap(BITMAP_SAMPLE_SIZE, imageStoragePath);
                    // bitmap = MediaStore.Images.Media.getBitmap(Tanggapan_admin.this.getContentResolver(), data.getData());
                    setToImageView(getResizedBitmap(rotatedBitmap, max_resolution_image));
                    imgProPhto.setImageBitmap(rotatedBitmap);
                    edit_foto();

                    //  CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }
        }

        if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
            try {


                // mengambil gambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(menu_profil.this.getContentResolver(), data.getData());
                setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                getStringImage(decoded);
                edit_foto();
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                foto(tempUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Log.i(TAG, "getStringImage: "+encodedImage);
        return encodedImage;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imgProPhto.setImageBitmap(decoded);

    }

    void foto(Uri uri) {
        Glide.with(menu_profil.this)
                .load(uri)
                .apply(new RequestOptions()
                        .fitCenter()
                        .circleCrop()
                        .error(R.drawable.ic_account_box_black_48dp))
                .into(imgProPhto);
    }

    void edit_foto() {
        image = getStringImage(decoded);
        Pelayanan_service api = Retroserver.getClient().create(Pelayanan_service.class);
        Call<BaseResponse> sendbio = api.edit_foto(image, nik);


        sendbio.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                String value = response.body().getvalue();
                String message = response.body().getMessage();
                //   progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(menu_profil.this, "Berhasil Edit Foto", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(menu_profil.this, "Gagal", Toast.LENGTH_SHORT).show();
                    //  Toast.makeText(menu_register_detail_warga_jambi.this, message, Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                // progress.dismiss();
                // Toast.makeText(menu_register_detail_warga_jambi.this, "Jaringan Error!" + t, Toast.LENGTH_SHORT);
            }
        });
    }

    @OnClick({R.id.btn_edit_no, R.id.btn_eidt_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_edit_no:
                dialog = new BottomSheetDialog(this);

                dialog.setTitle("Login");
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_no);
                dialog.setCancelable(true);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);
                ButterKnife.bind(this);


                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                no_hp = (EditText) dialog.findViewById(R.id.edit_no);
                no_hp.setText(txtHPPro.getText());


                no_hp.requestFocus();
                Button btn_edit = (Button) dialog.findViewById(R.id.btn_edit_pw);


                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (no_hp.getText().toString().trim().equals("")){
                            Toast.makeText(menu_profil.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            up_no();
                        }




                    }
                });

                dialog.show();



                break;
            case R.id.btn_eidt_email:

                dialog = new BottomSheetDialog(this);

                dialog.setTitle("Login");
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_email);
                dialog.setCancelable(true);

                lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                dialog.getWindow().setAttributes(lp);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.getWindow().setDimAmount(0.5f);
                ButterKnife.bind(this);


                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


                no_hp = (EditText) dialog.findViewById(R.id.edit_no);
                no_hp.setText(txtEmaPro.getText());


                no_hp.requestFocus();
                btn_edit = (Button) dialog.findViewById(R.id.btn_edit_pw);


                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (no_hp.getText().toString().trim().equals("")){
                            Toast.makeText(menu_profil.this, "Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }else {
                            up_email();
                        }




                    }
                });

                dialog.show();
                break;
        }
    }
    void up_no() {

        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsModel> update2 = api2.up_no(
                nik,
                no_hp.getText().toString().trim());

        update2.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Log.d("Retro", "Response_tes");
                Toast.makeText(menu_profil.this, "kode" + response.body().getKode(), Toast.LENGTH_SHORT);
                Log.i("kodeeee", "onResponse: "+response.body().getKode());
                if (response.body().getKode().equals("0")) {
                    Log.i("kodeeee", "onResponse: "+response.body().getKode());
                    // berhail();
                    Toast.makeText(menu_profil.this, "Berhasil Ubah No Handphone", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }
                if (response.body().getKode().equals("1")) {
                    Toast.makeText(menu_profil.this, "Password Lama Salah", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(menu_profil_pejabat_pejabat.this, response.body().getPesan(), Toast.LENGTH_SHORT);
                    // edit_gagal("Passwod Lama Salah");
                }

            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                // Pd.hide();
                Log.i("Retro", "OnFailure" + t);
                //Toast.makeText(menu_profil_pejabat.this, "gagal update", Toast.LENGTH_SHORT);
            }
        });
    }

    void up_email() {

        ApiRequest api2 = Retroserver.getClient().create(ApiRequest.class);
        Call<ResponsModel> update2 = api2.edit_email(
                nik,
                no_hp.getText().toString().trim());

        update2.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                Log.d("Retro", "Response_tes");
                Toast.makeText(menu_profil.this, "kode" + response.body().getKode(), Toast.LENGTH_SHORT);
                Log.i("kodeeee", "onResponse: "+response.body().getKode());
                if (response.body().getKode().equals("0")) {
                    Log.i("kodeeee", "onResponse: "+response.body().getKode());
                    // berhail();
                    Toast.makeText(menu_profil.this, "Berhasil Ubah Email", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();

                }
                if (response.body().getKode().equals("1")) {
                    Toast.makeText(menu_profil.this, "Password Lama Salah", Toast.LENGTH_SHORT).show();
                    // Toast.makeText(menu_profil_pejabat_pejabat.this, response.body().getPesan(), Toast.LENGTH_SHORT);
                    // edit_gagal("Passwod Lama Salah");
                }

            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                // Pd.hide();
                Log.i("Retro", "OnFailure" + t);
                //Toast.makeText(menu_profil_pejabat.this, "gagal update", Toast.LENGTH_SHORT);
            }
        });
    }

}
