package com.example.adopsi_hewan.model.model_tampil_hewan;


import com.google.gson.annotations.SerializedName;


public class ResultItem_hewn {

	@SerializedName("jk")
	private String jk;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("umur")
	private String umur;

	@SerializedName("fotoHewan")
	private String fotoHewan;

	@SerializedName("nik")
	private String nik;

	@SerializedName("nama")
	private String nama;

	@SerializedName("berat")
	private String berat;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("tgl")
	private String tgl;

	@SerializedName("id")
	private String id;

	@SerializedName("vaksin")
	private String vaksin;

	@SerializedName("steril")
	private String steril;

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

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
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

	public void setSteril(String steril){
		this.steril = steril;
	}

	public String getSteril(){
		return steril;
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
			"ResultItem_hewn{" +
			"jk = '" + jk + '\'' + 
			",keterangan = '" + keterangan + '\'' + 
			",umur = '" + umur + '\'' + 
			",fotoHewan = '" + fotoHewan + '\'' + 
			",nik = '" + nik + '\'' + 
			",nama = '" + nama + '\'' + 
			",berat = '" + berat + '\'' + 
			",jenis = '" + jenis + '\'' + 
			",tgl = '" + tgl + '\'' + 
			",id = '" + id + '\'' + 
			",vaksin = '" + vaksin + '\'' + 
			",steril = '" + steril + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}