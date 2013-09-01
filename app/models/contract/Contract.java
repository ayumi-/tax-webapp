package models.contract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.party.Party;
import utils.ConvertUtils;

public class Contract {
	
	public long contractNumber;
	public Party party;
	public Date agreementDate;
	public Date effectiveDate;
	public Date expiredDate;
	public ContractType contractType;
	public RoundingMethod priceRoundingMethod;
	public RoundingMethod consumptionTaxRoundingMethod;
	public CalculationBase consumptionTaxCalculationBase;
	
	public Contract(Party party, Date agreementDate, ContractType contractType,
			RoundingMethod priceRoundingMethod,
			RoundingMethod consumptionTaxRoundingMethod,
			CalculationBase consumptionTaxCalculationBase) {
		this.party = party;
		this.agreementDate = agreementDate;
		this.contractType = contractType;
		this.priceRoundingMethod = priceRoundingMethod;
		this.consumptionTaxRoundingMethod = consumptionTaxRoundingMethod;
		this.consumptionTaxCalculationBase = consumptionTaxCalculationBase;
	}
	
	public Contract() {
		// オブジェクトをコピーする際に使用する
	}

	public long getContractNumber() {
		return contractNumber;
	}
	
	public Party getParty() {
		return party;
	}
	
	public Date getAgreementDate() {
		return agreementDate;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	
	public Date getExpiredDate() {
		return expiredDate;
	}
	
	public ContractType getContractType() {
		return contractType;
	}
	
	public RoundingMethod getPriceRoundingMethod() {
		return priceRoundingMethod;
	}
	
	public RoundingMethod getConsumptionTaxRoundingMethod() {
		return consumptionTaxRoundingMethod;
	}
	
	public CalculationBase getConsumptionTaxCalculationBase() {
		return consumptionTaxCalculationBase;
	}
	
	public static Contract save(Contract src) {
		entities.Contract dst = new entities.Contract();
		ConvertUtils.copyBeanProperties(src, dst);
		dst.save();
		ConvertUtils.copyBeanProperties(dst, src);
		return src;
	}

	public static List<Contract> getList(String sortBy, String order) {
		List<entities.Contract> list = entities.Contract.findBy(sortBy, order);
		
		ArrayList<Contract> result = new ArrayList<Contract>();
		for (entities.Contract src : list) {
			Contract dst = new Contract();
			result.add(dst);
			ConvertUtils.copyBeanProperties(src, dst);
		}
		return result;
	}
}
