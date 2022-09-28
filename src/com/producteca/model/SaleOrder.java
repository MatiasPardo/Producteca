package com.producteca.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.Request;

public class SaleOrder {
	
	private String id;
	private Date date;
	private BigDecimal amount;
	private String status;
	private String paymentStatus;
	private LinkedList<Line> lines;
	private List<Payment> payments; 
	private ShippingAddress shippingAddress;
	private BigDecimal shippingCost;
	private String orderId;
	private String contactPerson;
	private String contactName;
	private Date updatedAt;
	
	
	public static LinkedList<SaleOrder> getSaleOrders(Producteca producteca, String date) throws Exception {
		//Date format: yyyy-MM-dd
		Request request = new Request.Builder()
				  .url(producteca.getUrl() + "/search/salesorders?$filter= ((paymentStatus eq 'Approved') and"
						  + "(updatedAt gt " + date + "T00:00:00z" + "))")
				  .method("GET", null)
				  .addHeader("x-api-key", producteca.getApiKey())
				  .addHeader("Authorization", "Bearer " + producteca.getBearer())
				  .addHeader("Content-Type", "application/json")
				  .build();
		
		String response = producteca.sendRequest(request);
		LinkedList<SaleOrder> orders = new LinkedList<SaleOrder>();
		JsonElement jsonElement = new Gson().fromJson(response, JsonElement.class);
		JsonArray jsonArray = new JsonArray();
		try{
			if(jsonElement.isJsonObject()){
				jsonArray = jsonElement.getAsJsonObject().get("results").getAsJsonArray();
			}
			if(jsonArray != null && !jsonArray.isJsonNull()){
				for(JsonElement element : jsonArray){
					SaleOrder order = new Gson().fromJson(element, SaleOrder.class);
					orders.add(order);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public BigDecimal getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
