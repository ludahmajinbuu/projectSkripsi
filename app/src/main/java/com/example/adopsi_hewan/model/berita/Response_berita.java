package com.example.adopsi_hewan.model.berita;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_berita {

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("status")
	private boolean status;

	@SerializedName("desc")
	private String desc;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			",desc = '" + desc + '\'' + 
			"}";
		}
}