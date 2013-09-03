package facades;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


import models.contract.CalculationBase;
import models.contract.Contract;
import models.contract.ContractType;
import models.contract.RoundingMethod;
import models.party.Party;
import utils.DateUtils;

public class ContractFacade {

	private Contract contract;

	public ContractFacade(
			Party p, 
			String agreementDateStr,
			String contractTypeStr,
			String priceRoundingMethodStr,
			String consumptionTaxRoundingMethodStr,
			String consumptionTaxCalculationBaseStr) {
		
    	Date agreementDate = null;
    	try {
    		agreementDate = DateUtils.parseDate(agreementDateStr, "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO エラーメッセージ
		}
    	
    	ContractType contractType = ContractType.valueOf(contractTypeStr);
    	RoundingMethod priceRoundingMethod = RoundingMethod.valueOf(priceRoundingMethodStr);
    	RoundingMethod consumptionTaxRoundingMethod = RoundingMethod.valueOf(consumptionTaxRoundingMethodStr);
    	CalculationBase consumptionTaxCalculationBase = CalculationBase.valueOf(consumptionTaxCalculationBaseStr);

    	this.contract = new Contract(p, agreementDate, contractType, priceRoundingMethod, consumptionTaxRoundingMethod, consumptionTaxCalculationBase);
	}

	public void save() {
		contract.save();
	}

	public static List<Contract> getList(String sortBy, String order) {
		return Contract.findBy(sortBy, order);
	}

}
