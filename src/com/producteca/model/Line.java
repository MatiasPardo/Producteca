package com.producteca.model;

import java.math.BigDecimal;

public class Line {
	private Product product;
	private Variation variation;
	private Integer quantity;
	private BigDecimal price;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Variation getVariation() {
		return variation;
	}
	public void setVariation(Variation variation) {
		this.variation = variation;
	}
}
