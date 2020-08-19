package com.example.adopsi_hewan.app;

public class AppConfig {
	//USER
	// Server user login url
	public static String URL_LOGIN_USER = "http://192.168.43.14/adopsi/login.php";
	public static String JUMLAH = "http://192.168.43.14/adopsi/data_rusak.php?pemohon_id=";

	// Server user register url
	public static String URL_REGISTER_USER = "http://192.168.43.14/adopsi/register_user.php";

    //Lupa password
    public static final String LUPA_PASSWORD = "http://192.168.43.14/adopsi/update_password.php";
	public static final String URL_MATIKAN_NOTIF =  "http://192.168.43.14/adopsi/matikan.php";
	public static final String LAPOR_BENCANA = "http://192.168.43.14/adopsi/lapor_bencana.php";
	public static final String BERITA = "http://192.168.43.14/adopsi/tampil_berita.php";
	public static final String JML_BERITA = "http://192.168.43.14/adopsi/jml_berita.php";
	public static final String ABSEN = "http://192.168.43.14/adopsi/absen.php?nis=";
	public static final String BACA =  "http://192.168.43.14/adopsi/baca.php";
    public static final String QUIZ =  "http://192.168.43.14/adopsi/quiz.php";
	public static final String NILAI = "http://192.168.43.14/adopsi/tampil_nilai.php?nis=";
	public static final String KEC = "http://192.168.43.14/adopsi/kecamatan.php?id=";
	public static final String KEL = "http://192.168.43.14/adopsi/kelurahan.php?id=";
	public static final String DES = "http://192.168.43.14/adopsi/des.php";


	public static final String PROFIL = "http://192.168.43.14/adopsi/profil.php?nis=";
	public static final String FOTO = "http://192.168.43.14/adopsi/foto1.php";
	public static final String EMAIL = "http://192.168.43.14/adopsi/email.php";
	public static final String EDIT_PROFIL = "http://192.168.43.14/adopsi/edit_profil.php";

	public static final String TAG_SUCCESS = "success";
	public static final String TAG_MESSAGE = "message";

	public final static String TAG_EMAIL = "email";
	public final static String TAG_ID = "id";
	public final static String TAG_PASSWORD = "password";

	//This would be the name of our shared preferences
	public static final String SHARED_PREF_NAME = "myloginapp";

	//We will use this to store the boolean in sharedpreference to track user is loggedin or not
	public static final String LOGGEDIN_SHARED_PREF = "loggedin";








	public static String STR_PUSH = "pushNotification";
	public static final String URL_REGISTER_DEVICE = "http://192.168.43.14/adopsi/notif/RegisterDevice.php";
	public static final String URL_SEND_SINGLE_PUSH = "http://192.168.43.14/adopsi/notif/sendSinglePush.php";
	public static final String URL_SEND_MULTIPLE_PUSH = "http://192.168.43.14/adopsi/notif/sendMultiplePush.php";
	public static final String URL_FETCH_DEVICES = "http://192.168.43.14/adopsi/notif/GetRegisteredDevices.php";
}
