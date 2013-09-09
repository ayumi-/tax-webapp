package forms;

import java.util.List;




public class OrderForm {
	public String effective_date;
	public String transaction_type;
	public String is_untaxed;
	public String trading_subject;
	public List<String> detail_product_id;
	public List<String> detail_quantity;
}
