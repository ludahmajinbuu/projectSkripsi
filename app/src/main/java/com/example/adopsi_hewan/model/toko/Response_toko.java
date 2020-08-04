package com.example.adopsi_hewan.model.toko;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_toko {

	@SerializedName("result")
	private List<ResultItem_toko> result;

	@SerializedName("kode")
	private int kode;

	@SerializedName("search_count")
	private int searchCount;

	public List<ResultItem_toko> getResult(){
		return result;
	}

	public int getKode(){
		return kode;
	}

	public int getSearchCount(){
		return searchCount;
	}
}