package models.contract;

import java.util.Date;

import models.party.Party;
import utils.ConvertUtils;

public class Contract {
	
	private long contractNumber;
	private Party party;
	private Date agreementDate;
	private Date effectiveDate;
	private Date expiredDate;
	private ContractType contractType;
	private RoundingMethod priceRoundingMethod;
	private RoundingMethod consumptionTaxRoundingMethod;
	private CalculationBase consumptionTaxCalculationBase;
	
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
}
