package entities;

import javax.persistence.*;

import play.db.ebean.Model;
import play.data.validation.*;

@Entity
public class Party extends Model {
	
	private static final long serialVersionUID = -4098480397681255405L;

	@Id
	public Long id;
	
	@Constraints.Required
	public String name;
	
	public static Finder<Long, Party> find = new Finder<Long, Party>(
		Long.class, Party.class	
	);

}
