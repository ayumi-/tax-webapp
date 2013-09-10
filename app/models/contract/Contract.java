package models.contract;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.party.Party;
import play.db.ebean.Model;

@Entity
public class Contract extends Model {

	private static final long serialVersionUID = 7240739265118318434L;
	
	@Id
	public Long contractNumber;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "party_id")
	public Party party;

	public Date agreementDate;
	
	public Date effectiveDate;
	
	public Date expiredDate;
	
	public String contractType;
	
	public String priceRoundingMethod;
	
	public String consumptionTaxRoundingMethod;
	
	public String consumptionTaxCalculationBase;
	
	public Contract(Party party, Date agreementDate, ContractType contractType,
			RoundingMethod priceRoundingMethod,
			RoundingMethod consumptionTaxRoundingMethod,
			CalculationBase consumptionTaxCalculationBase) {
		this.party = party;
		this.agreementDate = agreementDate;
		this.contractType = contractType.name();
		this.priceRoundingMethod = priceRoundingMethod.name();
		this.consumptionTaxRoundingMethod = consumptionTaxRoundingMethod.name();
		this.consumptionTaxCalculationBase = consumptionTaxCalculationBase.name();
	}
	
	public Long getContractNumber() {
		return contractNumber;
	}
	
	public RoundingMethod getPriceRoundingMethod() {
		return RoundingMethod.valueOf(priceRoundingMethod);
	}
	
	public CalculationBase getConsumptionTaxCalculationBase() {
		return CalculationBase.valueOf(consumptionTaxCalculationBase);
	}
	
	public static Finder<Long, Contract> find = new Finder<Long, Contract>(Long.class, Contract.class);

	public static List<Contract> findBy(String sortBy, String order) {
		return find.orderBy(sortBy + " " + order).fetch("party").findList();
	}

	public static Contract findById(long contractNumber) {
		return find.byId(contractNumber);
	}
}
