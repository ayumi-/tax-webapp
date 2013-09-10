package models.product;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.tea.Unit;

import play.db.ebean.Model;

@Entity
public class Product extends Model {
	
	private static final long serialVersionUID = 5255996690326230096L;

	@Id
	public Long id;
	
	public String name;
	
	@Column(scale = 1)
	public BigDecimal unitPrice;
	
	public Unit unit;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "item_id")
	public Item item;
	
	public Product(Item item, String name, BigDecimal unitPrice, Unit unit) {
		this.item = item;
		this.name = name;
		this.unitPrice = unitPrice;
		this.unit = unit;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	
	public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);

	public static List<Product> findAll() {
		return find.all();
	}

	public static Product findById(Long id) {
		return find.byId(id);
	}

	public Unit getUnit() {
		return unit;
	}
}
