package models.pricing;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.tea.Entry;

@Entity
public class PricingEntry extends Entry {
	
	private static final long serialVersionUID = 471382520085873896L;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "transaction_number")
	public PricingTransaction transaction;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "account_id")
	public PricingAccount account;

	@Column(precision = 32, scale = 1)
	public BigDecimal price;
	
	public String currency = "円";

	public PricingEntry(PricingTransaction transaction, PricingAccount account, BigDecimal price) {
		this.transaction = transaction;
		this.account = account;
		this.price = price;
	}
	
	public PricingAccount getAccount() {
		return account;
	}

	public PricingTransaction getTransaction() {
		return transaction;
	}
	
	public PricingEntry clone() {
		PricingEntry copy = (PricingEntry) this._ebean_createCopy();
		copy.id = null;
		return copy;
	}

	public void toRed() {
		price = price.abs().multiply(BigDecimal.valueOf(-1));
	}
	
	public static Finder<Long, PricingEntry> find = 
			new Finder<Long, PricingEntry>(Long.class, PricingEntry.class);
	
	public static List<PricingEntry> findByTransaction(PricingTransaction transaction) {
		return find.where().eq("transaction_number", transaction.transactionNumber).findList();
	}

}
