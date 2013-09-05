package models.pricing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import models.tea.Transaction;
import models.trading.TradingEntry;
import models.trading.TradingTransaction;
import facades.ConsumptionTaxFacade;

@Entity
public class PricingTransaction extends Transaction {

	private static final long serialVersionUID = -594558767537017995L;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "trading_transaction_number", referencedColumnName = "transaction_number")
	public TradingTransaction tradingTransaction;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "trading_entry_basis_id")
	public TradingEntry tradingEntryBasis;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "trading_transaction_basis_number", referencedColumnName = "transaction_number")
	public TradingTransaction tradingTransactionBasis;
	
	@Transient
	public BigDecimal totalPrice = BigDecimal.ZERO;
	
	public PricingTransaction(Date effectiveDate, TradingTransaction tradingTransaction) {
		this.effectiveDate = effectiveDate;
		this.tradingTransaction = tradingTransaction;
		this.tradingTransactionBasis = tradingTransaction;
	}

	public PricingTransaction(Date effectiveDate, TradingEntry tradingEntry) {
		this.effectiveDate = effectiveDate;
		this.tradingTransaction = tradingEntry.getTransaction();
		this.tradingEntryBasis = tradingEntry;
	}

	public BigDecimal calculateConsumptionTax() {
		ConsumptionTaxFacade facade = new ConsumptionTaxFacade(tradingTransaction, totalPrice);
		
		return facade.getConsumptionTaxAmountRounded();
	}

	public void addTotalPrice(BigDecimal price) {
		totalPrice = totalPrice.add(price);
	}
	
	public List<PricingEntry> getEntries() {
		return PricingEntry.findByTransaction(this);
	}
	
	public static Finder<Long, PricingTransaction> find = 
			new Finder<Long, PricingTransaction>(Long.class, PricingTransaction.class);

	public static List<PricingTransaction> findByTradingTransaction(
			TradingTransaction tradingTransaction) {
		return find.where().eq("trading_transaction_number", tradingTransaction.transactionNumber).findList();
	}
}
