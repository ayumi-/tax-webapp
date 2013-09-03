package models.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

@Entity
public class Product extends Model {
	
	private static final long serialVersionUID = 5255996690326230096L;

	@Id
	public Long id;
	
	public String name;
	
	public Integer unitPrice;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "item_id")
	public Item item;
	
	public Product(Item item, String name, int unitPrice) {
		this.item = item;
		this.name = name;
		this.unitPrice = unitPrice;
	}

	public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);

	public static List<Product> findAll() {
		return find.all();
	}

}
