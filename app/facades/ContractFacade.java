package facades;

import java.text.ParseException;
import java.util.Date;

import utils.DateUtils;

import models.contract.CalculationBase;
import models.contract.Contract;
import models.contract.ContractType;
import models.contract.RoundingMethod;
import models.party.Party;

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

	public Contract save() {
		return Contract.save(contract);
	}

}
