package models.trading;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import models.contract.Contract;
import models.pricing.PricingTransaction;
import models.tea.Transaction;

@Entity
public class TradingTransaction extends Transaction {

	private static final long serialVersionUID = -7882871673033722853L;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
	@JoinColumn(name = "contract_number")
	public Contract contract;
	
	public String transactionType;
	
	public String tradingSubject;
	
	public Boolean isUntaxed;
	
	@Transient
	public BigDecimal totalPrice;
	
	public TradingTransaction(Contract contract, Date effectiveDate, TransactionType transactionType,
			TradingSubjectType tradingSubject, boolean isUntaxed) {
		this.contract = contract;
		this.effectiveDate = effectiveDate;
		this.transactionType = transactionType.name();
		this.tradingSubject = tradingSubject.name();
		this.isUntaxed = isUntaxed;
	}
	
	public Contract getContract() {
		return contract;
	}
	
	public List<TradingEntry> getEntries() {
		return TradingEntry.findByTransaction(this);
	}
	
	public List<PricingTransaction> getPricingTransaction() {
		return PricingTransaction.findByTradingTransaction(this);
	}
	
	public TradingTransaction clone() {
		TradingTransaction copy = (TradingTransaction) this._ebean_createCopy();
		copy.transactionNumber = null;
		copy.contract = this.contract;
		return copy;
	}
	
	public static Finder<Long, TradingTransaction> find = 
			new Finder<Long, TradingTransaction>(Long.class, TradingTransaction.class);
	
	public static List<TradingTransaction> findByContract(Contract contract) {
		return find.where().eq("contract_number", contract.contractNumber).findList();
	}

	public static TradingTransaction finndById(Long transactionNumber) {
		return find.byId(transactionNumber);
	}
}
