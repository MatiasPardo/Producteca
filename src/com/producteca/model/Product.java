package com.producteca.model;

import java.util.LinkedList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Product {
	
	private String id;
	private String sku;
	private String name;
	private String barcode;
	private String category;
	
	private List<Stock> stock;
	private List<Price> prices;
	
	public String getProduct(Producteca producteca, String productId) throws Exception {
		Request request = new Request.Builder()
				  .url(producteca.getUrl() + "/products/" + productId)
				  .method("GET", null)
				  .addHeader("x-api-key", producteca.getApiKey())
				  .addHeader("Authorization", "Bearer " + producteca.getBearer())
				  .addHeader("Cookie", "express:sess=eyJwYXNzcG9ydCI6e319; express:sess.sig=6u4cyBgFwn9GiR5JKZzuFwXZz8k")
				  .build();
		
		String response = producteca.sendRequest(request);
		
		return response;
	}
	
	private String productToJson(Product product) {
		JsonObject productJson = new JsonObject();
		productJson.addProperty("sku", product.getSku());
		productJson.addProperty("name", product.getName());
		
		JsonArray pricesJson = new JsonArray();
		for(Price price : product.getPrices()) {
			JsonObject priceJson = new JsonObject();
			priceJson.addProperty("amount", price.getAmount());
			priceJson.addProperty("currency", price.getCurrency());
			priceJson.addProperty("priceList", price.getPriceList());
			pricesJson.add(priceJson);
		}
		productJson.add("prices", pricesJson);
		
		JsonArray stocksJson = new JsonArray();
		for(Stock stock : product.getStocks()) {
			JsonObject stockJson = new JsonObject();
			stockJson.addProperty("quantity", stock.getQuantity());
			stockJson.addProperty("availableQuantity",stock.getAvailableQuantity());
			stockJson.addProperty("warehouse", stock.getWarehouse());
			stocksJson.add(stockJson);
		}
		productJson.add("stocks", stocksJson);
		
		return productJson.toString();
	}
	
	public String create(Producteca producteca, Product product) throws Exception {		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, this.productToJson(product));
		Request request = new Request.Builder()
				  .url(producteca.getUrl() + "/products/synchronize")
				  .method("POST", body)
				  .addHeader("x-api-key", producteca.getApiKey())
				  .addHeader("createifitdoesntexist", "true")
				  .addHeader("Authorization", "Bearer " + producteca.getBearer())
				  .addHeader("Content-Type", "application/json")
				  .build();
		
		String response = producteca.sendRequest(request);				
		if(response.contains(product.sku))
			return new Gson().fromJson(response, Product.class).getId();
		else
			throw new Exception("Error al crear producto con SKU: " + product.getSku() + "\n" + response);
	}
	
	public Boolean update(Producteca producteca, Product product) throws Exception {		
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, this.productToJson(product));
		Request request = new Request.Builder()
				  .url(producteca.getUrl() + "/products/synchronize")
				  .method("POST", body)
				  .addHeader("x-api-key", producteca.getApiKey())
				  .addHeader("Authorization", "Bearer " + producteca.getBearer())
				  .addHeader("Content-Type", "application/json")
				  .build();
		
		String response = producteca.sendRequest(request);
		JsonArray jsonAux = new Gson().fromJson(response, JsonArray.class);
		Boolean responseOk = Boolean.FALSE;
		JsonObject jsonResponse = jsonAux.iterator().next().getAsJsonObject().get("product").getAsJsonObject();
		if(jsonResponse.get("updated") != null && !jsonResponse.get("updated").isJsonNull())
			responseOk = jsonResponse.get("updated").getAsJsonPrimitive().getAsBoolean();
		return responseOk;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Stock> getStocks() {
		if(stock == null)
			return new LinkedList<Stock>();
		else
			return stock;
	}

	public void setStocks(List<Stock> stock) {
		this.stock = stock;
	}

	public List<Price> getPrices() {
		if(prices == null)
			return new LinkedList<Price>();
		else
			return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}


}
