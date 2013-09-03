package models.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Item extends Model {

	private static final long serialVersionUID = 3603283784659818626L;
	
	@Id
	public Long id;
	
	@Column(unique=true)
	public String name;
	
	public Boolean isUntaxed;
	
	public Item(String name, boolean isUntaxed) {
		this.name = name;
		this.isUntaxed = isUntaxed;
	}
	
	public static Finder<Long, Item> find = new Finder<Long, Item>(Long.class, Item.class);
	
	public static Item findById(Long id) {
		return find.byId(id);
	}

}
