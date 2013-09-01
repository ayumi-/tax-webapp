package utils;

import java.util.ArrayList;
import java.util.List;

public class EnumUtils {
	
	public static <E extends Enum<E>> List<String> getList(Class<E> myEnum) {
		ArrayList<String> list = new ArrayList<String>();
		for (E type : myEnum.getEnumConstants()) {
			list.add(type.name().toString());
		}
		return list;
	}

}
