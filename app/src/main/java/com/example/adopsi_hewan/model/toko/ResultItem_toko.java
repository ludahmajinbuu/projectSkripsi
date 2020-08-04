package com.example.adopsi_hewan.model.toko;

import com.google.gson.annotations.SerializedName;

public class ResultItem_toko {

	@SerializedName("lng")
	private String lng;

	@SerializedName("jarak")
	private String jarak;

	@SerializedName("id_toko")
	private String idToko;

	@SerializedName("jam_oprsional")
	private String jamOprsional;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("lat")
	private String lat;

	@SerializedName("nama_toko")
	private String namaToko;

	@SerializedName("alamat")
	private String alamat;

	public String getLng(){
		return lng;
	}

	public String getJarak(){
		return jarak;
	}

	public String getIdToko(){
		return idToko;
	}

	public String getJamOprsional(){
		return jamOprsional;
	}

	public String getGambar(){
		return gambar;
	}

	public String getLat(){
		return lat;
	}

	public String getNamaToko(){
		return namaToko;
	}

	public String getAlamat(){
		return alamat;
	}
}