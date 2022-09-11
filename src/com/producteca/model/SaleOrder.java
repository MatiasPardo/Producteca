package com.producteca.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.Request;

public class SaleOrder {
	
	private String id;
	private String date;
	private Integer amount;
	private String status;
	private String paymentStatus;
	private LinkedList<Line> lines;
	
	public static LinkedList<SaleOrder> getSaleOrders(Producteca producteca, String date) throws Exception {
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-ddThh-mm-ssz");
		
		Request request = new Request.Builder()
				  .url(producteca.getUrl() + "/search/salesorders?$filter= ((paymentStatus eq 'Approved') and"
						  + "(date gt " + date + "))")
						  //+ "(date gt " + dateFormat.format(date) + "))")
				  .method("GET", null)
				  .addHeader("x-api-key", producteca.getApiKey())
				  .addHeader("Authorization", "Bearer " + producteca.getBearer())
				  .addHeader("Content-Type", "application/json")
				  .build();
		
		String response = producteca.sendRequest(request);
		LinkedList<SaleOrder> orders = new LinkedList<SaleOrder>();
		JsonObject jsonObject = new Gson().fromJson(response, JsonObject.class);
		JsonArray jsonOrders = jsonObject.get("results").getAsJsonArray();
		
		for(JsonElement element : jsonOrders){
			SaleOrder order = new Gson().fromJson(element, SaleOrder.class);
			orders.add(order);
		}	

		return orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LinkedList<Line> getLines() {
		return lines;
	}

	public void setLines(LinkedList<Line> lines) {
		this.lines = lines;
	}

}
