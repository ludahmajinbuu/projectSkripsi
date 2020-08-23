package com.example.adopsi_hewan.model.model_tampil_hewan;

import com.google.gson.annotations.SerializedName;

public class ResultItem_hewan_new {

	@SerializedName("jk")
	private String jk;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("umur")
	private String umur;

	@SerializedName("fotoHewan")
	private String fotoHewan;

	@SerializedName("alasan")
	private String alasan;

	@SerializedName("idUser")
	private String idUser;

	@SerializedName("nik")
	private String nik;

	@SerializedName("id_alasan")
	private String idAlasan;

	@SerializedName("id_hewan")
	private String idHewan1;

	@SerializedName("nama")
	private String nama;

	@SerializedName("idHewan")
	private String idHewan;

	@SerializedName("berat")
	private String berat;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("id")
	private String id;

	@SerializedName("vaksin")
	private String vaksin;

	@SerializedName("id_pemohon")
	private String idPemohon;

	@SerializedName("steril")
	private String steril;

	@SerializedName("idAdopsi")
	private String idAdopsi;

	@SerializedName("status")
	private String status;

	public void setJk(String jk){
		this.jk = jk;
	}

	public String getJk(){
		return jk;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setUmur(String umur){
		this.umur = umur;
	}

	public String getUmur(){
		return umur;
	}

	public void setFotoHewan(String fotoHewan){
		this.fotoHewan = fotoHewan;
	}

	public String getFotoHewan(){
		return fotoHewan;
	}

	public void setAlasan(String alasan){
		this.alasan = alasan;
	}

	public String getAlasan(){
		return alasan;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setIdAlasan(String idAlasan){
		this.idAlasan = idAlasan;
	}

	public String getIdAlasan(){
		return idAlasan;
	}

	public void setIdHewan1(String idHewan){
		this.idHewan = idHewan;
	}

	public String getIdHewan1(){
		return idHewan;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdHewan(String idHewan){
		this.idHewan = idHewan;
	}

	public String getIdHewan(){
		return idHewan;
	}

	public void setBerat(String berat){
		this.berat = berat;
	}

	public String getBerat(){
		return berat;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setTgl(String tgl){
		this.tgl = tgl;
	}

	public String getTgl(){
		return tgl;
	}

	public void setTanggal(String tanggal){
		this.tanggal = tanggal;
	}

	public String getTanggal(){
		return tanggal;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setVaksin(String vaksin){
		this.vaksin = vaksin;
	}

	public String getVaksin(){
		return vaksin;
	}

	public void setIdPemohon(String idPemohon){
		this.idPemohon = idPemohon;
	}

	public String getIdPemohon(){
		return idPemohon;
	}

	public void setSteril(String steril){
		this.steril = steril;
	}

	public String getSteril(){
		return steril;
	}

	public void setIdAdopsi(String idAdopsi){
		this.idAdopsi = idAdopsi;
	}

	public String getIdAdopsi(){
		return idAdopsi;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResultItem{" + 
			"jk = '" + jk + '\'' + 
			",keterangan = '" + keterangan + '\'' + 
			",umur = '" + umur + '\'' + 
			",fotoHewan = '" + fotoHewan + '\'' + 
			",alasan = '" + alasan + '\'' + 
			",idUser = '" + idUser + '\'' + 
			",nik = '" + nik + '\'' + 
			",id_alasan = '" + idAlasan + '\'' + 
			",id_hewan = '" + idHewan + '\'' + 
			",nama = '" + nama + '\'' + 
			",idHewan = '" + idHewan1 + '\'' +
			",berat = '" + berat + '\'' + 
			",jenis = '" + jenis + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",tanggal = '" + tanggal + '\'' + 
			",id = '" + id + '\'' + 
			",vaksin = '" + vaksin + '\'' + 
			",id_pemohon = '" + idPemohon + '\'' + 
			",steril = '" + steril + '\'' + 
			",idAdopsi = '" + idAdopsi + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}