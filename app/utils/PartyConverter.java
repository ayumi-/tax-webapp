package utils;

import models.party.Party;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

public class PartyConverter implements Converter<Party, entities.Party> {

	@Override
	public entities.Party convert(Party src) {
		entities.Party dst = new entities.Party();
		BeanUtils.copyProperties(src, dst);
		return dst;
	}

}
