package com.producteca.model;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
		String responseString = "";
		
		response = client.newCall(request).execute();
		responseString = response.body().string();
		if(!response.isSuccessful()) {
			throw new Exception("Error " + response.code() + " API Producteca: " + responseString);
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
