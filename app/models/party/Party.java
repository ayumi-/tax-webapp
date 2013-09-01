package models.party;

import org.springframework.beans.BeanUtils;



public class Party {
	
	public Long id;
	public String name;
	
	public Party() {
	}
	
	public Party(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public static Party find(long id) {
		entities.Party find = entities.Party.find.byId(id);
		Party p = new Party();
		BeanUtils.copyProperties(find, p);
		return p;
	}

	public static Party save(Party src) {
		entities.Party dst = new entities.Party();
		BeanUtils.copyProperties(src, dst);
		dst.save();
		BeanUtils.copyProperties(dst, src);
		return src;
	}
}
