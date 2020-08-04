package com.example.adopsi_hewan.model.profil;

import com.google.gson.annotations.SerializedName;

public class ResultItem_profil {

	@SerializedName("nik")
	private String nik;

	@SerializedName("jk")
	private String jk;

	@SerializedName("nama")
	private String nama;

	@SerializedName("tempat_lahir")
	private String tempatLahir;

	@SerializedName("pekerjaan")
	private String pekerjaan;

	@SerializedName("foto")
	private String foto;

	@SerializedName("status_kwn")
	private String statusKwn;

	@SerializedName("nohp")
	private String nohp;

	@SerializedName("id")
	private String id;

	@SerializedName("tanggal_lahir")
	private String tanggalLahir;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public String getNik(){
		return nik;
	}

	public String getJk(){
		return jk;
	}

	public String getNama(){
		return nama;
	}

	public String getTempatLahir(){
		return tempatLahir;
	}

	public String getPekerjaan(){
		return pekerjaan;
	}

	public String getFoto(){
		return foto;
	}

	public String getStatusKwn(){
		return statusKwn;
	}

	public String getNohp(){
		return nohp;
	}

	public String getId(){
		return id;
	}

	public String getTanggalLahir(){
		return tanggalLahir;
	}

	public String getEmail(){
		return email;
	}

	public String getAlamat(){
		return alamat;
	}
}