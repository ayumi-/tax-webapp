package utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

public class ConvertUtils {
	
	public static void copyBeanProperties(
			final Object source, final Object dest){
		final BeanWrapperImpl src = new BeanWrapperImpl(source);
		final BeanWrapperImpl dst = new BeanWrapperImpl(dest);
		PropertyDescriptor[] descriptors = src.getPropertyDescriptors();
		FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
        factoryBean.setConverters(createConverters());
        factoryBean.afterPropertiesSet();
        ConversionService service = factoryBean.getObject();
        
		for (PropertyDescriptor d : descriptors) {
			String name = d.getName();
			TypeDescriptor type = dst.getPropertyTypeDescriptor(name);
			if (type == null) {
				continue;
			}
			if (!dst.isWritableProperty(name)) {
				continue;
			}
			Object value = src.getPropertyValue(name);
			if (value == null || "".equals(value)) {
				dst.setPropertyValue(name, null);
			} else {
				dst.setPropertyValue(name, 
						service.convert(value, type.getType()));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <S, T> Set<Converter<S, T>> createConverters() {
		Set<Converter<S, T>> converters = new HashSet<Converter<S, T>>();
	
		converters.add((Converter<S, T>) new PartyConverter());
		
		return converters;
	}
}
