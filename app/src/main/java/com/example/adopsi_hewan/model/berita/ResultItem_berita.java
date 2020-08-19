package com.example.adopsi_hewan.model.berita;

import com.google.gson.annotations.SerializedName;

public class ResultItem_berita {

	@SerializedName("foto_info")
	private String fotoInfo;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("judul")
	private String judul;

	@SerializedName("isi")
	private String isi;

	@SerializedName("id_informasi")
	private String idInformasi;

	public void setFotoInfo(String fotoInfo){
		this.fotoInfo = fotoInfo;
	}

	public String getFotoInfo(){
		return fotoInfo;
	}

	public void setTgl(String tgl){
		this.tgl = tgl;
	}

	public String getTgl(){
		return tgl;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	public void setIdInformasi(String idInformasi){
		this.idInformasi = idInformasi;
	}

	public String getIdInformasi(){
		return idInformasi;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"foto_info = '" + fotoInfo + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",judul = '" + judul + '\'' + 
			",isi = '" + isi + '\'' + 
			",id_informasi = '" + idInformasi + '\'' + 
			"}";
		}
}