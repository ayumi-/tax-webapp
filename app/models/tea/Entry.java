package models.tea;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

@Entity
@MappedSuperclass
public class Entry extends Model {

	private static final long serialVersionUID = -5308925847676242233L;

	@Id
	public Long id;
	}
