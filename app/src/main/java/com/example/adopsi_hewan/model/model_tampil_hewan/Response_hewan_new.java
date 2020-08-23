package com.example.adopsi_hewan.model.model_tampil_hewan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_hewan_new {

	@SerializedName("result")
	private List<ResultItem_hewan_new> result;

	@SerializedName("kode")
	private int kode;

	@SerializedName("search_count")
	private int searchCount;

	public void setResult(List<ResultItem_hewan_new> result){
		this.result = result;
	}

	public List<ResultItem_hewan_new> getResult(){
		return result;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}

	public void setSearchCount(int searchCount){
		this.searchCount = searchCount;
	}

	public int getSearchCount(){
		return searchCount;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"result = '" + result + '\'' + 
			",kode = '" + kode + '\'' + 
			",search_count = '" + searchCount + '\'' + 
			"}";
		}
}