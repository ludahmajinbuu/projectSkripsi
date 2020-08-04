package com.example.adopsi_hewan.model.profil;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_profil {

	@SerializedName("result")
	private List<ResultItem_profil> result;

	@SerializedName("kode")
	private int kode;

	@SerializedName("search_count")
	private int searchCount;

	public List<ResultItem_profil> getResult(){
		return result;
	}

	public int getKode(){
		return kode;
	}

	public int getSearchCount(){
		return searchCount;
	}

	@Override
	public String toString(){
		return
				"Response_hewan{" +
						"result = '" + result + '\'' +
						",kode = '" + kode + '\'' +
						",search_count = '" + searchCount + '\'' +
						"}";
	}
}