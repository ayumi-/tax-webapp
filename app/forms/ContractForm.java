package forms;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;

public class ContractForm {
	public String party_id;
	@Required
	public String party_name;
	@Required
	@Pattern(value = "\\d{4}-\\d{2}-\\d{2}")
	public String agreement_date;
	public String contract_type;
	public String price_rounding_method;
	public String consumption_tax_rounding_method;
	public String consumption_tax_calculation_base;
}
