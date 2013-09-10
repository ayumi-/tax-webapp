package models.trading;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.tea.Entry;
import models.tea.Unit;

@Entity
public class TradingEntry extends Entry {

	private static final long serialVersionUID = 932816239193536058L;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_number")
	public TradingTransaction transaction;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_id")
	public TradingAccount account;

	@Column(scale = 1)
	public BigDecimal quantity;
	
	public String unit;
	
	public TradingEntry(TradingTransaction transaction, TradingAccount account,
			BigDecimal quantity, Unit unit) {
		this.transaction = transaction;
		this.account = account;
		this.quantity = quantity;
		this.unit = unit.name();
	}
	
	public TradingTransaction getTransaction () {
		return transaction;
	}

	public TradingAccount getAccount() {
		return account;
	}
	
	public TradingEntry clone() {
		TradingEntry copy = (TradingEntry) this._ebean_createCopy();
		copy.id = null;
		return copy;
	}
	
	public void toRed() {
		quantity = quantity.abs().multiply(BigDecimal.valueOf(-1));
	}
	
	public static Finder<Long, TradingEntry> find = 
			new Finder<Long, TradingEntry>(Long.class, TradingEntry.class);
	
	public static List<TradingEntry> findByTransaction(TradingTransaction transaction) {
		return find.where().eq("transaction_number", transaction.transactionNumber).findList();
	}


}
