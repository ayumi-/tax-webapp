package utils;

import models.party.Party;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;

public class ModelPartyConverter implements Converter<entities.Party, Party> {

	@Override
	public Party convert(entities.Party src) {
		Party dst = new Party();
		BeanUtils.copyProperties(src, dst);
		return dst;
	}

}