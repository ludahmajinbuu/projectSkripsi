package com.example.adopsi_hewan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.example.adopsi_hewan.model.BaseResponse;
import com.example.adopsi_hewan.server.ApiRequest;
import com.example.adopsi_hewan.server.Pelayanan_service;
import com.example.adopsi_hewan.server.Retroserver;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class tambahHewa1 extends AppCompatActivity implements Validator.ValidationListener {
    private List<String> results = new ArrayList<String>();
    private List<String> results2 = new ArrayList<String>();
    private List<String> results3 = new ArrayList<String>();
    private String jsonResponse;

    public ArrayList<String> items =new ArrayList<>();
    String id_kecamatan;

    String image = null;
    private static final String TAG = tambahHewa1.class.getSimpleName();
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_STAUS = "status";

    String tag_json_obj = "json_obj_req";
    String id,nama,status,tgl_daftar;
    String token="123";

    @NotEmpty
    @BindView(R.id.txtnmaTbhHwan)
    EditText txtnmaTbhHwan;

    @NotEmpty
    @BindView(R.id.txtTbhJnsHwan)
    EditText txtTbhJnsHwan;

    @NotEmpty
    @BindView(R.id.txtTbhJKHewan)
    EditText txtTbhJKHewan;


    @NotEmpty
    @BindView(R.id.txtTbhBrtHwan)
    EditText txtTbhBrtHwan;

    @NotEmpty
    @BindView(R.id.txtTbhUmurHwan)
    EditText txtTbhUmurHwan;

    @NotEmpty
    @BindView(R.id.txtTbhVksinHwan)
    EditText txtTbhVksinHwan;

    @NotEmpty
    @BindView(R.id.txtTbhStrlHwan)
    EditText txtTbhStrlHwan;



    @BindView(R.id.btn_TbhHewn)
    Button lanjut;

    @NotEmpty
    @BindView(R.id.txtTbhKtrHwan)
    EditText txtTbhKtrHwan;


    @BindView(R.id.imgHewanTbh)
    ImageView imgHewanTbh;

    @NotEmpty(message = "Tanggal Lahir Tidak Boleh Kosong")


    public static final String session_status = "session_status";

    Boolean session = false;



    public final static String TAG_nis = "nik_ambil";
    public final static String TAG_STATUS = "status";
    public final static String TAG_NAMA = "nama";
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    Bitmap bitmap, decoded;

    int bitmap_size = 100; // image quality 1 - 100;
    int max_resolution_image = 1000;

    private static final int PHOTO_REQUEST_CODE = 102;
    private String path;

    public final int SELECT_FILE = 1;

    final PermissionManager permissionManager = new PermissionManager();

    Calendar myCalendar;
    String nik;
    DatePickerDialog.OnDateSetListener date;
//    @BindView(R.id.textView12)
//    TextView textView12;

    String status_user;

    ProgressDialog pd;
    Validator validator;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tambah_hewan);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);

        nik = sharedpreferences.getString(TAG_nis, null);

        ButterKnife.bind(this);
        pd = new ProgressDialog(tambahHewa1.this);


        validator = new Validator(this);
        validator.setValidationListener(this);

        myCalendar = Calendar.getInstance();


    }


    @OnClick(R.id.imgHewanTbh)
    public void btnTmbhL() {
//  if (CameraUtils.checkPermissions(getApplicationContext())) {
        imgHewanTbh.setImageResource(0);
        final CharSequence[] items = {"Camera", "Galeri",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(tambahHewa1.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.drawable.dog);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    if (permissionManager.userHasPermission(tambahHewa1.this)) {
                        takePicture();
                    } else {
                        permissionManager.requestPermission(tambahHewa1.this);
                    }
                } else if (items[item].equals("Galeri")) {
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
        //  }
        //  else {
        //    requestCameraPermission(MEDIA_TYPE_IMAGE);
        //   }


    }



    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Uri photoURI = null;
            try {
                File photoFile = createImageFileWith();
                path = photoFile.getAbsolutePath();
                photoURI = FileProvider.getUriForFile(tambahHewa1.this,
                        getString(R.string.file_provider_authority),
                        photoFile);

            } catch (IOException ex) {
                Log.e("TakePicture", ex.getMessage());
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                takePictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(takePictureIntent, PHOTO_REQUEST_CODE);
        }
    }

    public File createImageFileWith() throws IOException {
        final String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "JPEG_" + timestamp;
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "pics");
        storageDir.mkdirs();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }



    @OnClick(R.id.button)
    public void daftr() {

        validator.validate();

    }




//new



    void insert_laporan() {
        if (decoded == null) {
            Toast.makeText(this, "Anda belum mengambil Foto", Toast.LENGTH_SHORT).show();





        }else {
            image = getStringImage(decoded);
            // String id_user = SP_user.instance().get_SP_id_user("id_user");
            Pelayanan_service api = Retroserver.getClient().create(Pelayanan_service.class);
            Call<BaseResponse> sendbio = api.insert_data_anda_bukan_warga(
                    txtnmaTbhHwan.getText().toString(),
                    nik,
                    txtTbhJnsHwan.getText().toString(),
                    txtTbhJKHewan.getText().toString(),
                    txtTbhBrtHwan.getText().toString(),
                    txtTbhUmurHwan.getText().toString(),
                    txtTbhVksinHwan.getText().toString(),

                    txtTbhStrlHwan.getText().toString(),
                    txtTbhKtrHwan.getText().toString(),
                    image

                    );


            // get selected radio button from radioGroup


            sendbio.enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    String value = response.body().getvalue();
                    String message = response.body().getMessage();
                    //    Toast.makeText(tambahHewa1.this, "Jaringan Error!"+message, Toast.LENGTH_SHORT).show();

                    //  progress.dismiss();
                    if (value.equals("1")) {
                        Toast.makeText(tambahHewa1.this, message, Toast.LENGTH_SHORT).show();
                        dialog_register_berhsail();
                    } else {
                        Toast.makeText(tambahHewa1.this, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    // progress.dismiss();
                    Toast.makeText(tambahHewa1.this, "Jaringan Error!" + t, Toast.LENGTH_SHORT).show();
                }


            });
        }
    }

    void dialog_berhasil_kirim() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 2000);

    }
    void dialog_register_berhsail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(tambahHewa1.this);
        builder.setCancelable(false);
        builder.setMessage("Register Berhasil ...");
        builder.setPositiveButton("Silahkan Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(tambahHewa1.this, menu_login.class);
                startActivity(intent);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public  void  onResume() {

        super.onResume();
      
    }


    @Override
    public void onValidationSucceeded() {
        insert_laporan();
       // update_no_hp();
        //ade
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

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


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            startActivity(new Intent(tambahHewa1.this, menu_utama.class));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = false;
                opt.inPreferredConfig = Bitmap.Config.RGB_565;

//


                BitmapFactory.decodeFile(path, opt);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                Bitmap bm = BitmapFactory.decodeFile(path, opts);
                ExifInterface exif = new ExifInterface(path);
                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;

                int rotationAngle = 0;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

                Matrix matrix = new Matrix();
                matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, opt.outWidth, opt.outHeight, matrix, true);

                setToImageView(getResizedBitmap(rotatedBitmap, max_resolution_image));
                imgHewanTbh.setImageBitmap(rotatedBitmap);
                getStringImage(decoded);
                Log.i(TAG, "decode: "+decoded);
                //    CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
                Log.i(TAG, "onActivityResult: ");


            }
            catch (Exception e){
                // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }



        }

//
        if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
            try {


                // mengambil gambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(tambahHewa1.this.getContentResolver(), data.getData());
                setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                getStringImage(decoded);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
//
//        if (requestCode == 90) {
//            if (resultCode == RESULT_OK) {
//                try {
//
//
//                    BitmapFactory.Options bounds = new BitmapFactory.Options();
//                    bounds.inJustDecodeBounds = true;
//                    BitmapFactory.decodeFile(imageStoragePath, bounds);
//
//                    BitmapFactory.Options opts = new BitmapFactory.Options();
//                    Bitmap bm = BitmapFactory.decodeFile(imageStoragePath, opts);
//                    ExifInterface exif = new ExifInterface(imageStoragePath);
//                    String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
//                    int orientation = orientString != null ? Integer.parseInt(orientString) :  ExifInterface.ORIENTATION_NORMAL;
//
//                    int rotationAngle = 0;
//                    if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
//                    if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
//                    if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
//
//                    Matrix matrix = new Matrix();
//                    matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
//                    Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
//
//                    setToImageView(getResizedBitmap(rotatedBitmap, max_resolution_image));
//                    img_foto_ktp.setImageBitmap(rotatedBitmap);
//                    getStringImage(decoded);
//                    Log.i(TAG, "onActivityResult: ");
//                    CameraUtils.refreshGallery(getApplicationContext(), imageStoragePath);
//
//                }
//                catch (Exception e){
//                    // Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//
//
//            } else if (resultCode == RESULT_CANCELED) {
//
//            }
//            else {
//
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        imgHewanTbh.setImageBitmap(decoded);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        Log.i("ghj", "getStringImage: "+encodedImage);
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

}
