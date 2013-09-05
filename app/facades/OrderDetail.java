package facades;

import java.math.BigDecimal;

import models.product.Product;

public class OrderDetail {

	public Product product;
	public BigDecimal quantity;

	public OrderDetail(Product product, Double quantity) {
		this.product = product;
		this.quantity = BigDecimal.valueOf(quantity);
	}
	
	public Product getProduct() {
		return product;
	}
	
	public BigDecimal getQuantity() {
		return quantity;
	}

}
