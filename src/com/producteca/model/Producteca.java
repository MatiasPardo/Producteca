package com.producteca.model;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Producteca {
	private String bearer;
	private String apiKey;
	private String url;
	
	public Producteca(String bearer, String apiKey, String url) {
		this.bearer = bearer;
		this.apiKey = apiKey;
		this.url = url;
	}
	
	public String sendRequest(Request request) throws Exception {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Response response = null;
		ResponseBody body = null;
		String responseString = "";
		Call newCall = null;
		try{
			newCall = client.newCall(request);
			response = newCall.execute();
			body = response.body();
			responseString = body.string();
			if(!response.isSuccessful()) {
				throw new Exception("Error " + response.code() + " API Producteca: " + responseString);
			}
		}finally{
			if(response != null)
				response.close();
			
			if(body != null)
				response.close();
		}
		
		
		return responseString;
	}
	
	public String getBearer() {
		return bearer;
	}
	
	public void setBearer(String bearer) {
		this.bearer = bearer;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
