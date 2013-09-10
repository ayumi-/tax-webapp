package forms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class OrderForm {
	@Required
	@Pattern(value = "\\d{4}-\\d{2}-\\d{2}")
	public String effective_date;
	public String transaction_type;
	public String is_untaxed;
	public String trading_subject;
	public List<String> detail_product_id;
	public List<String> detail_quantity;

	// TODO リファクタリング
	public Map<String,List<ValidationError>> validate() {
		HashMap<String,List<ValidationError>> map = new HashMap<String, List<ValidationError>>();
		
		int i = 0;
		boolean isExist = false;
		String message = "This field is required";
		for (String pid : detail_product_id) {
			String q = detail_quantity.get(i);
			if (!pid.isEmpty() || !q.isEmpty()) {
				isExist = true;
			}
			if (pid.isEmpty() && !q.isEmpty()) {
				String key = "detail_product_id[" + i + "]";
				map.put(key, Arrays.asList(new ValidationError(key, message)));
			}
			if (!pid.isEmpty() && q.isEmpty()) {
				String key = "detail_quantity[" + i + "]";
				map.put(key, Arrays.asList(new ValidationError(key, message)));
			}
			i++;
		}
		if (!isExist) {
			map.put("detail_product_id[0]", Arrays.asList(new ValidationError("detail_product_id[0]", message)));
			map.put("detail_quantity[0]", Arrays.asList(new ValidationError("detail_quantity[0]", message)));
		}
		if (map.size() > 0) {
			return map;
		}
		return null;
	}
}
