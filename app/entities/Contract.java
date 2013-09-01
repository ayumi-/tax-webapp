package entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

}