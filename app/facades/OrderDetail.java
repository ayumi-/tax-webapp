package facades;

import java.math.BigDecimal;

import models.product.Product;

public class OrderDetail {

	public Product product;
	public BigDecimal quantity;

	public OrderDetail(Product product, BigDecimal quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}

}
