package com.example.adopsi_hewan.model.model_tampil_hewan;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Response_hewan {

	@SerializedName("result")
	private List<ResultItem_hewn> result;

	@SerializedName("status")
	private boolean status;

	@SerializedName("desc")
	private String desc;

	public void setResult(List<ResultItem_hewn> result){
		this.result = result;
	}

	public List<ResultItem_hewn> getResult(){
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
			"Response_hewan{" +
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			",desc = '" + desc + '\'' + 
			"}";
		}
}