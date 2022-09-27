package com.producteca.model;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		
		String bearer = "eec57d23e9dbd7bac1a6d919d182a2872c88c2ac"; //secret
		String apiKey = "c41OCCFJDn1VynGN81oA465FTOndUoQF5imTTjHa";
		String url = "https://api-external.producteca.com";
		
		Producteca producteca = new Producteca(bearer, apiKey, url);
		
		Product product = new Product();
		product.setSku("1");
//		product.setId("167576362"); 
		product.setName("Vino Malbecc");
		
		List<Stock> stocks = new LinkedList<Stock>();
		Stock stock = new Stock();
		stock.setAvailableQuantity(100);
		stock.setQuantity(150);
		stock.setWarehouse("Default");
		stocks.add(stock);
		
		List<Price> prices = new LinkedList<Price>();
		Price retailPrice = new Price();
		retailPrice.setAmount(new BigDecimal(850));
		retailPrice.setCurrency("Local");
		retailPrice.setPriceList("Default");
		prices.add(retailPrice);
		
		Price price = new Price();
		price.setAmount(new BigDecimal(750));
		price.setCurrency("Local");
		price.setPriceList("Mayorista");
		prices.add(price);
		
		product.setStocks(stocks);
		product.setPrices(prices);
		
		System.out.println(product.update(producteca, product));
		//System.out.println(product.create(producteca, product));
		
		//System.out.println(product.getProduct(producteca, "167576362"));	
		
		//heinkis
		//Date currentDate = new Date();
		
		LinkedList<SaleOrder> orders = SaleOrder.getSaleOrders(producteca, "2022-08-28");
		for(SaleOrder order : orders) {
			System.out.println("Orden: " + order.getId() + " - Fecha: " +  order.getDate() + " - Total: " + order.getAmount());
			System.out.println("Lineas:");
			for(Line line : order.getLines()) {
				System.out.println(line.getProduct().getName());
				System.out.println(line.getQuantity());
				System.out.println(line.getPrice());
				System.out.println("==========================");
			}
			System.out.println();
		}
		if(orders.size() == 0) {
			System.out.println("==========================");
			System.out.println("No se encontraron pedidos");
			System.out.println("==========================");


		}
		
	}
}
