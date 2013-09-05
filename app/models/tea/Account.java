package models.tea;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

@Entity
@MappedSuperclass
public class Account extends Model {

	private static final long serialVersionUID = 3381106830515054148L;

	@Id
	public Long id;
	
	public String unit;
}
